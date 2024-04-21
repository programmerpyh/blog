package com.ankhnotes.controller;

import com.ankhnotes.annotation.mySystemLog;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.dto.UpdateUserDto;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.service.UserService;
import com.ankhnotes.utils.SecurityUtils;
import com.ankhnotes.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    UserService userService;

    @mySystemLog(logDescription = "后台分页查询用户信息")
    @GetMapping("/list")
    public ResponseResult selectUserPage(Integer pageNum, Integer pageSize
            , String username, String phonenumber, String status){
        return userService.selectUserPage(pageNum, pageSize, username, phonenumber, status);
    }

    @mySystemLog(logDescription = "后台新增用户")
    @PostMapping
    public ResponseResult addNewUser(@RequestBody User user){
        return userService.addNewUser(user);
    }

    @mySystemLog(logDescription = "后台逻辑删除用户")
    @DeleteMapping("/{userIds}")
    public ResponseResult deleteUser(@PathVariable("userIds") String userIds){
        List<Long> Ids = SystemUtils.stringTransferToListLong(userIds);
        if(Ids.contains(SecurityUtils.getLoginUserId()))
            throw new RuntimeException("不能删除当前正登录的用户");
        boolean isDeleteSuccessful = userService.removeByIds(Ids);
        if(!isDeleteSuccessful)
            throw new RuntimeException("逻辑删除用户失败");
        return ResponseResult.okResult();
    }

    //前端这个页面有BUG, 手机
    @mySystemLog(logDescription = "后台获取用户详细信息用于修改")
    @GetMapping("/{userId}")
    public ResponseResult getUserInfoToUpdate(@PathVariable("userId") Long id){
        return userService.getUserInfoToUpdate(id);
    }


    @mySystemLog(logDescription = "后台更新用户信息")
    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDto dto){
        return userService.updateUser(dto);
    }

}
