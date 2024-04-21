package com.ankhnotes.cronjob;

import com.ankhnotes.domain.Article;
import com.ankhnotes.service.ArticleService;
import com.ankhnotes.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

/**
 * 设置定时任务
 */
@Component//注入后, 定时任务才能生效
@Slf4j
//通过定时任务实现每隔1分钟把redis中的浏览量更新到mysql数据库中
public class UpdateViewCount {

    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleService articleService;

    //在哪个方法添加了@Scheduled注解，哪个方法就会定时去执行
    @Scheduled(cron = "0 0/1 * * * ?")//cron表达式, 这里表示每1分钟执行一次
    //上面那行@Scheduled注解的cron属性就是具体的定时规则。从每一分钟的0秒开始，每隔1分钟就会执行下面的run方法
    public void run(){

        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        viewCountMap.forEach((articleId, viewCount) -> {
            Article article = new Article(Long.valueOf(articleId), viewCount.longValue());
            articleService.updateById(article);
        });

        log.info("redis的文章浏览量数据已更新到数据库，现在的时间是: {}", LocalTime.now());
    }

}
