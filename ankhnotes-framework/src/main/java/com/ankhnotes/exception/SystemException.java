package com.ankhnotes.exception;

import com.ankhnotes.enums.AppHttpCodeEnum;

/**
 * 用于统一异常处理, 可以理解为RuntimeException的vo
 *
 * 用于全局异常处理器中, 向前端返回符合(code, msg)格式的异常信息
 *
 * 同时用于在Controller中抛出包含appHttpCodeEnum信息的SystemException异常,
 * 如 new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME)表示抛出"必需填写用户名"异常
 */

public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum appHttpCodeEnum){
        super(appHttpCodeEnum.getMsg());
        this.code = appHttpCodeEnum.getCode();
        this.msg = appHttpCodeEnum.getMsg();
    }
}
