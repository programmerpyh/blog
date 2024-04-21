package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.dto.AddNewRoleDto;
import com.ankhnotes.dto.RoleChangeStatusDto;
import com.ankhnotes.dto.UpdateRoleDto;
import com.ankhnotes.service.RoleService;
import com.ankhnotes.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @mySystemLog(logDescription = "后台分页查询角色表")
    @GetMapping("/list")
    public ResponseResult selectRolePage(Integer pageNum, Integer pageSize, String roleName, String status){
        return roleService.selectRolePage(pageNum, pageSize, roleName, status);
    }

    @mySystemLog(logDescription = "后台修改角色的停启用状态")
    @PutMapping("/changeStatus")
    public ResponseResult changeRoleStatus(@RequestBody RoleChangeStatusDto dto){
        return roleService.changeRoleStatus(dto);
    }

    @mySystemLog(logDescription = "后台新增角色")
    @PostMapping
    public ResponseResult addNewRole(@RequestBody AddNewRoleDto dto){
        return roleService.addNewRole(dto);
    }

    @mySystemLog(logDescription = "后台查询单个角色的信息")
    @GetMapping("/{roleId}")
    public ResponseResult getRoleInfo(@PathVariable("roleId") Long roleId){
        return roleService.getRoleInfo(roleId);
    }

    @mySystemLog(logDescription = "后台修改角色信息")
    @PutMapping
    public ResponseResult updateRole(@RequestBody UpdateRoleDto dto){
        return roleService.updateRole(dto);
    }

    @mySystemLog(logDescription = "后台逻辑删除角色")
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") String stringroleIds){
        List<Long> roleIds = SystemUtils.stringTransferToListLong(stringroleIds);
        boolean isSuccessful = roleService.removeByIds(roleIds);
        if(!isSuccessful)
            throw new RuntimeException("逻辑删除角色失败");
        return ResponseResult.okResult();
    }

    @mySystemLog(logDescription = "后台查询所有状态正常的角色供用户菜单使用")
    @GetMapping("/listAllRole")
    public ResponseResult selectAllRoles(){
        return roleService.selectAllRoles();
    }

}
