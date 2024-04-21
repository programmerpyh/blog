package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.Article;
import com.ankhnotes.domain.ArticleTag;
import com.ankhnotes.domain.Category;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.dto.UpdateArticleDto;
import com.ankhnotes.dto.WriteBlogDto;
import com.ankhnotes.mapper.ArticleMapper;
import com.ankhnotes.service.ArticleService;
import com.ankhnotes.service.ArticleTagService;
import com.ankhnotes.service.CategoryService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.utils.RedisCache;
import com.ankhnotes.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.util.*;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2024-02-23 22:00:24
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    @Lazy //解决循环依赖
    private CategoryService categoryService;

    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleTagService articleTagService;

    /**
     * 查询浏览量最多的十篇文章
     * @return List<Article></>
     */
    @Override
    public ResponseResult hotArticleList() {

        //实时获取redis中的浏览量数据
        updateViewCountByRedis();

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //不能是草稿
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按浏览量倒序查询
        queryWrapper.orderByDesc(Article::getViewCount);
        //分页, 查询最高的10条记录
        Page<Article> page = new Page<>(SystemConstants.ARTICLE_STATUS_CURRENT
                , SystemConstants.ARTICLE_STATUS_SIZE);
        //查询
        page(page, queryWrapper);

        List<Article> articleList = page.getRecords();

        //将所有Article转成对应VO, 规范返回字段
//        List<HotArticleVO> hotArticleVOList = new ArrayList<>();
//        articleList.forEach(article -> {
//            HotArticleVO articleVO = new HotArticleVO();
//            BeanUtils.copyProperties(article, articleVO);
//            hotArticleVOList.add(articleVO);
//        });
        List<HotArticleVO> hotArticleVOList = BeanCopyUtils.copyBeanList(articleList, HotArticleVO.class);

        //包装响应返回
        return ResponseResult.okResult(hotArticleVOList);
    }

    /**
     * 获得分页的文章列表, 显示在主页面, 或根据分类显示
     * @param pageNum 分页页数
     * @param pageSize 页面大小
     * @param categoryId 分类id 为null即查询所有文章在主页面显示
     * @return 分页后的文章列表
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();

        articleWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0, Article::getCategoryId, categoryId);
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        articleWrapper.orderByDesc(Article::getIsTop);

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, articleWrapper);

        List<Article> articleList = page.getRecords();

        //将articleList中所有的article添加上categoryName字段
        articleList.forEach(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()));

        //更新所有article的viewCount为Redis的viewCount, 实时获取浏览量
        articleList.forEach(this::getViewCountByRedis);

        List<ArticleListVO> articleListVOS = BeanCopyUtils.copyBeanList(articleList, ArticleListVO.class);
        PageVO<ArticleListVO> pageVO = new PageVO<>(articleListVOS, page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    //后台查询文章列表使用
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.like(StringUtils.hasText(title), Article::getTitle, title);
        wrapper.like(StringUtils.hasText(summary), Article::getSummary, summary);
        //wrapper.orderByDesc(Article::getIsTop);

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);

        List<Article> articleList = page.getRecords();
        List<AdminArticleVO> articleVOS = BeanCopyUtils.copyBeanList(articleList, AdminArticleVO.class);
        return ResponseResult.okResult(new PageVO<>(articleVOS, page.getTotal()));
    }

    /**
     * 查询指定id的文章细节
     * @param id 文章id
     * @return ArticleDetailVO(特殊处理categoryName)
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);

        //更新article的viewCount为Redis的viewCount, 实时获取浏览量
        getViewCountByRedis(article);

        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);

        //处理categoryName
        Category category = categoryService.getById(articleDetailVO.getCategoryId());
        if(category!=null)
            articleDetailVO.setCategoryName(category.getName());

        return ResponseResult.okResult(articleDetailVO);
    }

    //后台获取单个文章的详细信息
    @Override
    public ResponseResult adminGetArticleInfo(Long id) {
        Article article = getById(id);
        AdminArticleInfoVO articleVO = BeanCopyUtils.copyBean(article, AdminArticleInfoVO.class);
        //查询该文章所有相关的tagId
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, id);
        wrapper.select(ArticleTag::getTagId);
        articleVO.setTags(articleTagService.listObjs(wrapper, object -> (Long) object));
        return ResponseResult.okResult(articleVO);
    }

    @Override
    public ResponseResult updateViewCount(String articleId) {
        //Redis里viewCount的类型为Integer
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", articleId);
        redisCache.setCacheMapValue("article:viewCount", articleId, viewCount+1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult writeArticle(WriteBlogDto writeBlogDto) {
        //是否初始化viewCount?
        Article article = BeanCopyUtils.copyBean(writeBlogDto, Article.class);
        save(article);

        //处理文章-标签关联表
        Long articleId =article.getId();
        List<Long> tagIds = writeBlogDto.getTags();
        tagIds.forEach(tag -> articleTagService.save(new ArticleTag(articleId, tag)));

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateArticle(UpdateArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        updateById(article);
        //还要保存修改的tag信息到article_tag关联表
        ///先删除原先的所有tag
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, articleDto.getId());
        articleTagService.remove(wrapper);
        ///在添加新的
        List<ArticleTag> articleTagList = new ArrayList<>();
        articleDto.getTags().forEach(tagId -> articleTagList.add(new ArticleTag(articleDto.getId(), tagId)));
        articleTagService.saveBatch(articleTagList);
        return ResponseResult.okResult();
    }

    //查询redis中文章浏览量
    private void getViewCountByRedis(Article article){
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId().toString());
        article.setViewCount(viewCount.longValue());
    }

    //同步所有redis内文章的浏览量到mysql
    private void updateViewCountByRedis(){

        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        viewCountMap.forEach((articleId, viewCount) -> {
            Article article = new Article(Long.valueOf(articleId), viewCount.longValue());
            updateById(article);
        });

        log.info("redis的文章浏览量数据已更新到数据库，现在的时间是: {}", LocalTime.now());
    }
}
