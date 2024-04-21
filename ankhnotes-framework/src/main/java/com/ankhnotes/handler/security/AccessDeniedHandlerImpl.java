package com.ankhnotes.handler.security;

import com.alibaba.fastjson.JSON;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义授权失败异常处理器
 * AccessDeniedHandler是Security官方官方提供的授权失败处理器
 * 通过实现AccessDeniedHandler接口来自定义在授权失败时返回给前端的异常格式
 *
 * 在@PreAuthorize抛出的异常不会被自定义授权处理器接收, 而会被全局异常处理器接收
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.error("自定义授权异常处理器发现异常, 报错信息: {}", AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());

        accessDeniedException.printStackTrace();

        //返回(403, 无权限操作)
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);

        //返回前端异常信息
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
