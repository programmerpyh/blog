package com.ankhnotes.service;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;

public interface BlogLoginService {

    ResponseResult login(User user);

    //不需要参数, 因为已登录用户信息可以从SecurityContextHolder获取
    ResponseResult logout();

}
