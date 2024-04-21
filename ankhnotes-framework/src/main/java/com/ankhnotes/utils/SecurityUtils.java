package com.ankhnotes.utils;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用于获取当前登录的用户信息(确保使用在已经.authenticated()的接口)
 */
public class SecurityUtils {

    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }

    public static Long getLoginUserId(){
        return getLoginUser().getUser().getId();
    }

    /**
     * 判断当前登录用户是否为超级管理员
     */
    public static Boolean isSuperAdmin(){
        Long loginUserId = getLoginUserId();
        return SystemConstants.Administrator_ID.equals(loginUserId);
    }

}
