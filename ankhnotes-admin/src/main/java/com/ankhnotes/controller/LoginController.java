package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.service.SystemLoginService;
import com.ankhnotes.utils.SecurityUtils;
import com.ankhnotes.vo.AdminUserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    SystemLoginService systemLoginService;

    @mySystemLog(logDescription = "用户登录后台")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User loginUser){

        if(!StringUtils.hasText(loginUser.getUserName()))
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);

        return systemLoginService.login(loginUser);
    }

    @mySystemLog(logDescription = "后台获取(超级管理员|非超级管理员)的权限和角色信息")
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVO> getInfo(){
        //获取后台登陆用户的userId
        Long loginUserId = SecurityUtils.getLoginUserId();
        return systemLoginService.getInfo(loginUserId);
    }

    @mySystemLog(logDescription = "查询登录用户的权限菜单(动态路由)信息")
    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        //获取后台登陆用户的userId
        Long loginUserId = SecurityUtils.getLoginUserId();
        return systemLoginService.getRouters(loginUserId);
    }

    @mySystemLog(logDescription = "退出登录")
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        //获取后台登陆用户的userId
        Long loginUserId = SecurityUtils.getLoginUserId();
        return systemLoginService.logout(loginUserId);
    }
}
