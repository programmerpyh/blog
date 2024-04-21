package com.ankhnotes.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenuMapper {

    void saveRoleMenu(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    List<Long> selectMenuIdsByRoleId(@Param("roleId") Long roleId);

    void deleteAllByRoleId(@Param("roleId") Long roleId);

}
