package com.ankhnotes.service.impl;

import com.ankhnotes.constants.SystemConstants;
import com.ankhnotes.domain.LoginUser;
import com.ankhnotes.domain.User;
import com.ankhnotes.mapper.MenuMapper;
import com.ankhnotes.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用于替换Security官方的UserDetailsService实现类
 * 官方实现类是从内存查询, 这里写自定义实现类实现数据库查询用户信息
 * 要实现的功能:根据username从数据库中读取用户信息并封装成UserDetail返回(后序Security框架会自动根据封装的UserDetail验证输入的用户名密码)
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库根据username查询用户
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(userWrapper);

        if(Objects.isNull(user))
            throw new RuntimeException("用户不存在");

        //填充权限信息
        List<String> perms = menuMapper.selectPermsByOtherUserId(user.getId());
        ////返回UserDetails
        return new LoginUser(user, perms);
    }
}
