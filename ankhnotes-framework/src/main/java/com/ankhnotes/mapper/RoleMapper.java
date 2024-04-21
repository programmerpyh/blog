package com.ankhnotes.mapper;

import com.ankhnotes.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-19 18:53:27
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    //连表查询非超级管理员(userId!=1)的角色信息
    List<String> selectRoleKeyByOtherUserId(@Param("userId") Long userId);
}
