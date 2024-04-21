package com.ankhnotes.service;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.Role;
import com.ankhnotes.dto.AddNewRoleDto;
import com.ankhnotes.dto.RoleChangeStatusDto;
import com.ankhnotes.dto.UpdateRoleDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2024-03-19 18:52:41
 */
public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long userId);

    //后台分页查询角色表
    ResponseResult selectRolePage(Integer pageNum, Integer pageSize, String roleName, String status);

    //后台修改角色的停启用状态
    ResponseResult changeRoleStatus(RoleChangeStatusDto dto);

    //后台添加新角色
    ResponseResult addNewRole(AddNewRoleDto dto);

    //后台查询单个角色的信息
    ResponseResult getRoleInfo(Long roleId);

    //后台修改角色信息
    ResponseResult updateRole(UpdateRoleDto dto);

    //后台查询所有状态正常的角色供用户菜单使用
    ResponseResult selectAllRoles();

}
