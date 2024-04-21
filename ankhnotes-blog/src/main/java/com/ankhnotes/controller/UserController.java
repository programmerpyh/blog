package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.service.OssUploadService;
import com.ankhnotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //查询用户信息
    @mySystemLog(logDescription = "查询用户信息")
    @GetMapping("/userInfo")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }

    //更新用户信息
    @mySystemLog(logDescription = "更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User loginUser){
        return userService.updateUserInfo(loginUser);
    }

    //手机号码前端还没加上, 先不管
    @mySystemLog(logDescription = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User registerUser){
        return userService.register(registerUser);
    }
}
