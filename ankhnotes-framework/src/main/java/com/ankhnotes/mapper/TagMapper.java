package com.ankhnotes.mapper;

import com.ankhnotes.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-14 16:15:05
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    void myUpdateById(@Param("id") Long id, @Param("flag") int flag);
}
