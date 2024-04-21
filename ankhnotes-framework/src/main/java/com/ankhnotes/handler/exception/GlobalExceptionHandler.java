package com.ankhnotes.handler.exception;

import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器, 处理所有经过Controller抛出的异常
 *
 * 主要是将所有异常进行统一处理, 使得处理过程可以集中在此处, 便于修改
 *
 * 处理过程:
 * 1. 日志输出异常信息, 方便后端查看并调试
 * 2. 使用规范返回格式将异常响应给前端
 */
@RestControllerAdvice//@ControllerAdvice和@ResponseBody的结合
@Slf4j//使用Lombok提供的Slf4j注解，实现日志功能
public class GlobalExceptionHandler {

    //处理SystemException异常
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){

        // 打印异常信息
        // {}是占位符，具体值由e决定
        log.error("出现SystemException, 出错信息: ↓↓", e);

        //返回前端
        return ResponseResult.errorResult(e.getCode(), e.getMsg());

    }

    //处理Spring Security的DaoAuthenticationProvider抛出的BadCredentialsException异常
    //即密码校对阶段抛出的"用户名或密码错误"异常
    //BlogLoginServiceImpl也抛出了这个异常, 但DaoAuthenticationProvider在他之前抛出, 所以在此特殊处理
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseResult badCredentialsExceptionHandler(BadCredentialsException e){

        log.error("出现BadCredentialsException, 出错信息: ↓↓", e);

        return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
    }

    //处理@PreAuthorize由于当前登录用户无权限访问接口所抛出的异常
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult accessDeniedExceptionHander(AccessDeniedException e){
        log.error("当前登录用户无权限访问正在访问的接口, 出错信息: ↓↓", e);

        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }

    //处理其他异常
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){

        log.error("出现Exception, 出错信息: ↓↓", e);

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());

    }

}
