package com.ankhnotes.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.Category;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.dto.AddCategoryDto;
import com.ankhnotes.dto.CategoryListDto;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.service.CategoryService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.utils.SystemUtils;
import com.ankhnotes.utils.WebUtils;
import com.ankhnotes.vo.ExcelCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @mySystemLog(logDescription = "写博客页面查询所有分类信息")
    @GetMapping("/listAllCategory")
    public ResponseResult Alllist(){
        return categoryService.getAllCategory();
    }

    @mySystemLog(logDescription = "后台分页查询分类表信息")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, CategoryListDto categoryListDto){
        return categoryService.getPage(pageNum, pageSize, categoryListDto);
    }

    @mySystemLog(logDescription = "后台新增分类")
    @PostMapping
    public ResponseResult addNewCategory(@RequestBody AddCategoryDto addCategoryDto){
        return categoryService.addNewCategory(addCategoryDto);
    }

    @mySystemLog(logDescription = "后台逻辑删除分类")
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id") String stringIds){
        List<Long> ids = SystemUtils.stringTransferToListLong(stringIds);
        return categoryService.deleteCategory(ids);
    }

    @mySystemLog(logDescription = "后台获取分类信息")
    @GetMapping("/{id}")
    public ResponseResult getCategoryInfo(@PathVariable("id") Long id){
        return categoryService.getCategoryInfo(id);
    }

    @mySystemLog(logDescription = "后台修改分类信息")
    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @PreAuthorize("@PermissionService.hasPermission('content:category:export')")
    @mySystemLog(logDescription = "导出所有分类到Excel, rocketmq异步")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response){
        if (!categoryService.requestExcelExport())
            WebUtils.renderString(response, JSON.toJSONString(ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR)));
        WebUtils.renderString(response, JSON.toJSONString(ResponseResult.okResult()));
    }

}
