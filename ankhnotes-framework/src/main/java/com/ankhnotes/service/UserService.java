package com.ankhnotes.service;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.dto.UpdateUserDto;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-03-11 18:22:58
 */
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User loginUser);

    ResponseResult register(User registerUser);

    //后台分页查询用户信息
    ResponseResult selectUserPage(Integer pageNum, Integer pageSize, String username, String phonenumber, String status);

    //后台添加用户
    ResponseResult addNewUser(User user);

    //后台获取用户详细信息用于修改
    ResponseResult getUserInfoToUpdate(Long id);

    //后台更新用户信息
    ResponseResult updateUser(UpdateUserDto dto);
}
