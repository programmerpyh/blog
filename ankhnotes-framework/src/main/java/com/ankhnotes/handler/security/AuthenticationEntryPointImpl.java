package com.ankhnotes.handler.security;

import com.alibaba.fastjson.JSON;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败异常处理器
 * AuthenticationEntryPoint是Security官方官方提供的认证失败处理器
 * 通过实现AuthenticationEntryPoint接口来自定义在认证失败(登录无效)时返回给前端的异常格式
 *
 * 在登录过程中, 我定义了两种异常可能抛出
 * 1. 没有登录或登录失效就访问需要登录的页面, 抛出"需要登录"异常
 * 2. 登录时用户名或密码错误, 抛出"用户名或密码错误"异常
 * 3. 其他异常, 抛出"出现错误"异常
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        //判断异常类型, 返回符合接口规范的异常信息
        ResponseResult result = null;
        if(authException instanceof BadCredentialsException){

            //输出异常信息到控制台
            log.error("自定义认证异常处理器发现异常, 报错信息: {}", AppHttpCodeEnum.LOGIN_ERROR.getMsg());

            //返回(505,"用户名或密码错误")
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }else if (authException instanceof InsufficientAuthenticationException){

            //输出异常信息到控制台
            log.error("自定义认证异常处理器发现异常, 报错信息: {}", AppHttpCodeEnum.NEED_LOGIN.getMsg());

            //返回(401,"需要登录后操作")
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {

            //输出异常信息到控制台
            log.error("自定义认证异常处理器发现异常, 报错信息: {}", AppHttpCodeEnum.SYSTEM_ERROR.getMsg());

            //返回(500,"出现错误")
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        authException.printStackTrace();

        //返回前端异常信息
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
