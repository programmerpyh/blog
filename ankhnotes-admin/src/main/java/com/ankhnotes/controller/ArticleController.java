package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.Article;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.dto.UpdateArticleDto;
import com.ankhnotes.dto.WriteBlogDto;
import com.ankhnotes.service.ArticleService;
import com.ankhnotes.service.TagService;
import com.ankhnotes.utils.RedisCache;
import com.ankhnotes.utils.SystemUtils;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 后台写博客页面的前端中, "是否置顶"和"是否开启评论"两个选项弄反了
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    RedisCache redisCache;

    @mySystemLog(logDescription = "写博客页面新增博客")
    @PostMapping
    public ResponseResult writeBlog(@RequestBody WriteBlogDto writeBlogDto){
        return articleService.writeArticle(writeBlogDto);
    }

    @mySystemLog(logDescription = "后台查询所有文章(包括草稿)")
    @GetMapping("/list")
    public ResponseResult getArticlePage(Integer pageNum, Integer pageSize, String title, String summary){
        return articleService.articleList(pageNum, pageSize, title, summary);
    }

    @mySystemLog(logDescription = "后台根据文章id查询对应的文章")
    @GetMapping("/{id}")
    public ResponseResult getArticleInfo(@PathVariable("id") Long id){
        return articleService.adminGetArticleInfo(id);
    }

    @mySystemLog(logDescription = "后台修改文章")
    @PutMapping
    public ResponseResult updateArticle(@RequestBody UpdateArticleDto articleDto){
        return articleService.updateArticle(articleDto);
    }

    @mySystemLog(logDescription = "后台逻辑删除文章")
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") String stringIds){
        List<Long> ids = SystemUtils.stringTransferToListLong(stringIds);

        //将redis中的文章的浏览量同步到mysql中并删除
        for(Long id : ids){
            if(redisCache.isCacheMapHasHkey("article:viewCount", id.toString())){
                Integer articleViewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
                LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
                wrapper.set(Article::getViewCount, articleViewCount).eq(Article::getId, id);
                articleService.update(wrapper);
                redisCache.delCacheMapValue("article:viewCount", id.toString());
            }
        }

        boolean isDeleteSuccessful = articleService.removeByIds(ids);
        if(!isDeleteSuccessful)
            throw new RuntimeException("逻辑删除文章失败");
        return ResponseResult.okResult();
    }

}
