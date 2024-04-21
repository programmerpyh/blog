package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogLoginController {

    @Autowired
    BlogLoginService blogLoginService;

    @mySystemLog(logDescription = "用户登录前台")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        //处理用户名未输入, 抛出异常, 全局异常处理器会处理
        if(!StringUtils.hasText(user.getUserName()))
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);

        return blogLoginService.login(user);
    }

    @mySystemLog(logDescription = "注销")
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
