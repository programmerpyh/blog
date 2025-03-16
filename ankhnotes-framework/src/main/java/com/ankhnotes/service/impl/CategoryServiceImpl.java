package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.Article;
import com.ankhnotes.domain.Category;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.dto.AddCategoryDto;
import com.ankhnotes.dto.CategoryListDto;
import com.ankhnotes.mapper.CategoryMapper;
import com.ankhnotes.service.ArticleService;
import com.ankhnotes.service.CategoryService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-03-06 11:45:39
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    @Lazy //解决循环依赖
    private ArticleService articleService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    //获取所有 有文章 的分类
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        Set<Long> categoryIdSet = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());

        List<Category> categoryList = listByIds(categoryIdSet);

        categoryList = categoryList.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        List<GetCategoryListVO> categoryVOs = BeanCopyUtils.copyBeanList(categoryList, GetCategoryListVO.class);

        return ResponseResult.okResult(categoryVOs);
    }

    @Override
    //获取所有状态正常, 未删除的分类
    public ResponseResult getAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);

        List<Category> categoryList = list(wrapper);

        List<CategoryListVO> categoryListVOS
                = BeanCopyUtils.copyBeanList(categoryList, CategoryListVO.class);

        return ResponseResult.okResult(categoryListVOS);
    }

    @Override
    public ResponseResult getPage(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(categoryListDto.getName())
                , Category::getName, categoryListDto.getName());
        wrapper.eq(StringUtils.hasText(categoryListDto.getStatus())
                , Category::getStatus, categoryListDto.getStatus());
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryVO.class);
        return ResponseResult.okResult(new PageVO<>(categoryVOS, page.getTotal()));
    }

    @Override
    public ResponseResult addNewCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        //暂时还没做父子分类功能, 先全部设置为根分类
        category.setPid(-1L);
        //判断分类名是否为空
        if(!StringUtils.hasText(category.getName()))
            throw new RuntimeException("新增分类时, 分类名不能为空");
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(List<Long> ids) {
        ids.forEach(id -> {
            if(isCategoryHaveArticle(id))
                throw new RuntimeException("分类中仍有文章, 不可删除");
        });
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    private boolean isCategoryHaveArticle(Long CategoryId){
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getCategoryId, CategoryId);

        return articleService.count(wrapper)>0;
    }


    @Override
    public ResponseResult getCategoryInfo(Long id) {
        Category category = getById(id);
        CategoryVO categoryVO = BeanCopyUtils.copyBean(category, CategoryVO.class);
        return ResponseResult.okResult(categoryVO);
    }

    /**
     * 获取所有分类, 封装为消息通知rocketmq
     */
    @Override
    public boolean requestExcelExport() {
        List<Category> categoryList = list();
        List<ExcelCategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVO.class);

        Message<List<ExcelCategoryVO>> message = MessageBuilder.withPayload(categoryVOS).build();
        SendResult sendResult = rocketMQTemplate.syncSend("excel-export", message);
        return sendResult.getSendStatus().equals(SendStatus.SEND_OK);
    }
}
