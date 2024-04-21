package com.ankhnotes.mapper;

import com.ankhnotes.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-19 18:54:49
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByOtherUserId(@Param("userId") Long userId);

    List<Menu> selectMenusByOtherUserId(@Param("userId") Long userId);
}
