package com.ankhnotes.service;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.vo.AdminUserInfoVO;

public interface SystemLoginService {

    ResponseResult login(User user);

    ResponseResult<AdminUserInfoVO> getInfo(Long loginUserId);

    ResponseResult getRouters(Long loginUserId);

    ResponseResult logout(Long loginUserId);

}
