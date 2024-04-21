package com.ankhnotes.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    void saveUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);

    void deleteUserRoleByUserId(@Param("userId") Long userId);

}
