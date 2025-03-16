package com.ankhnotes.service;

import com.ankhnotes.domain.Category;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.dto.AddCategoryDto;
import com.ankhnotes.dto.CategoryListDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-03-06 11:45:23
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();

    ResponseResult getAllCategory();

    ResponseResult getPage(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto);

    ResponseResult addNewCategory(AddCategoryDto addCategoryDto);

    ResponseResult deleteCategory(List<Long> ids);

    ResponseResult getCategoryInfo(Long id);

    boolean requestExcelExport();
}
