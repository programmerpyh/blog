package com.ankhnotes.service;

import com.ankhnotes.domain.Article;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.dto.UpdateArticleDto;
import com.ankhnotes.dto.WriteBlogDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2024-02-23 22:00:23
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
    ResponseResult articleList(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult getArticleDetail(Long id);

    ResponseResult adminGetArticleInfo(Long id);

    ResponseResult updateViewCount(String articleId);

    ResponseResult writeArticle(WriteBlogDto writeBlogDto);

    ResponseResult updateArticle(UpdateArticleDto articleDto);

}
