package com.ankhnotes.runner;

import com.ankhnotes.domain.Article;
import com.ankhnotes.service.ArticleService;
import com.ankhnotes.utils.RedisCache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目启动时的预处理操作CommandLineRunner
 * 项目关闭时的预处理操作DisposableBean
 */
@Component
public class BlogRunner implements CommandLineRunner, DisposableBean {

    @Autowired
    ArticleService articleService;

    @Autowired
    RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        saveAllArticleViewCount();
    }

    @Override
    public void destroy() throws Exception {
        saveAllArticleViewCount();
    }

    //将所有文章的浏览量作为(文章id, 文章浏览量)键值对存入redis, 用于之后更新浏览量, 帮助mysql缓存更新操作
    private void saveAllArticleViewCount(){
        List<Article> articleList = articleService.list();
        Map<String, Integer> map = new HashMap<>();//(文章id, 文章浏览量)
        articleList.forEach(article -> map.put(article.getId().toString(), article.getViewCount().intValue()));
        redisCache.setCacheMap("article:viewCount", map);
    }

}
