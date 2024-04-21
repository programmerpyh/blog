package com.ankhnotes.controller;



import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ankhnotes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2024-03-06 11:48:27
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @mySystemLog(logDescription = "获取所有分类")
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}

