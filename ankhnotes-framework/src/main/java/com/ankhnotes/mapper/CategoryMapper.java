package com.ankhnotes.mapper;

import com.ankhnotes.domain.Category;
import com.ankhnotes.domain.ResponseResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-06 11:44:54
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    void myUpdateById(@Param("id") Long id, @Param("flag") int flag);
}
