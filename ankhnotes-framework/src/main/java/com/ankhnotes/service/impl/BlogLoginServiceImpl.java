package com.ankhnotes.service.impl;

import com.ankhnotes.domain.LoginUser;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import com.ankhnotes.service.BlogLoginService;
import com.ankhnotes.utils.BeanCopyUtils;
import com.ankhnotes.utils.JwtUtil;
import com.ankhnotes.utils.RedisCache;
import com.ankhnotes.vo.BlogUserLoginVO;
import com.ankhnotes.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 功能:
 * 1. 使用Security框架对登陆页面传入的用户名密码做认证, 校验是否正确, 用户是否存在等
 * 2. 若登陆成功, 则将返回的Authentication.UserDetails对象存入Redis
 * 3. 响应前端, 返回BlogUserLoginVO
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //封装Controller传来的用户输入的用户名和密码为Authentication类型的对象
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        //将封装的Authentication对象传入.authenticate()函数进行认证, 即校对用户名密码
        //会自动调用自定义的UserDetailsServiceImpl进行数据库查询并校对用户名密码, 若成功则返回非空的authentication对象
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //校验用户名密码是否通过认证
        if(Objects.isNull(authentication))
            throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);

        //获取UserDetails数据
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        String userId = loginUser.getUser().getId().toString();

        //将loginUser添加到Redis, 用于Security框架在认证之后的异常和授权过程中, 获取已登录用户的信息
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);

        ////响应过程////////////////////////////

        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        //生成userId的JWT的token
        String jwt = JwtUtil.createJWT(userId);
        BlogUserLoginVO blogUserLoginVO = new BlogUserLoginVO(jwt, userInfoVO);
        return ResponseResult.okResult(blogUserLoginVO);
    }

    @Override
    public ResponseResult logout() {

        //获取已登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //redis删除登录的用户信息, 表示退出登录, 这样JwtAuthenticationTokenFilter就查询不到了
        redisCache.deleteObject("bloglogin:" + loginUser.getUser().getId());

        return ResponseResult.okResult();
    }


}
