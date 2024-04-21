package com.ankhnotes.service.impl;

import com.ankhnotes.domain.LoginUser;
import com.ankhnotes.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PermissionService")
/**
 * 用于配合@PreAuthorize注解判断当前登录用户是否有权限调用被注解的方法
 * ->需要用户已登录, 即只能用于通过SpringSecurity的.authenticate()方法的接口
 *
 * @PreAuthorize抛出的异常不会被自定义授权处理器接收, 而会被全局异常处理器接收
 */
public class PermissionService {
    public boolean hasPermission(String perm){

        //超级管理员直接放行
        if(SecurityUtils.isSuperAdmin()){
            return true;
        }

        //普通管理员判断权限List里是否有perm
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 这个perms其实就是sys_menu表的perms字段的值
        List<String> perms = loginUser.getPermissions();
        return perms!=null&&perms.contains(perm);
    }
}
