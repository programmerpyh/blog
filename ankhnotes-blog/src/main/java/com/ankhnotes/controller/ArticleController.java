package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.Article;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    //注入公共模块的ArticleService接口
    @Autowired
    private ArticleService articleService;

    @mySystemLog(logDescription = "测试接口")
    @GetMapping("/list")
    public List<Article> test(){
        //查询数据库对应表的所有数据
        return articleService.list();
    }

    /**
     * 获得浏览量最高, 最热门的十篇文章
     * @return 最热门的十篇文章列表
     */
    @mySystemLog(logDescription = "获得浏览量最高, 最热门的十篇文章")
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    /**
     * 获得分页的文章列表, 显示在主页面
     * @param pageNum 分页页数
     * @param pageSize 页面大小
     * @param categoryId 分类id 为null即查询所有文章
     * @return 分页后的文章列表
     */
    @mySystemLog(logDescription = "获得分页的文章列表, 显示在主页面")
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @mySystemLog(logDescription = "根据文章id获取文章")
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @mySystemLog(logDescription = "更新文章浏览量到redis")
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") String articleId){
        return articleService.updateViewCount(articleId);
    }
}
