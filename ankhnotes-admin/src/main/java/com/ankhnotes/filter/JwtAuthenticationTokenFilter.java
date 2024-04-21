package com.ankhnotes.filter;


import com.alibaba.fastjson.JSON;
import com.ankhnotes.domain.LoginUser;
import com.ankhnotes.domain.ResponseResult;
import com.ankhnotes.domain.User;
import com.ankhnotes.enums.AppHttpCodeEnum;
import com.ankhnotes.utils.JwtUtil;
import com.ankhnotes.utils.RedisCache;
import com.ankhnotes.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 权限认证过滤器:使用JWT对用户的登录进行校验，查验是否有登录状态,
 * 若有则存到SecurityContextHolder, 表示认证通过, 允许访问需要登录认证的接口
 * 若无则抛出异常给前端, 需要重新登录
 * 1. 获取前端发来的Token(若无Token则表示接口不需要登录)
 * 2. 解析Token获取userId(若token在解析时发现过期或被篡改则抛出异常, 重新登录)
 * 3. 通过userId读取Redis中的登录用户信息(若读取不到则重新登录)
 * 4. 读取后保存到SecurityContextHolder上下文保存器中, 表示已登录
 *
 * 所有需要登录后才能访问的接口都会在SecurityConfig中设置.authenticate()
 * 然后就都会经过这一过滤器来验证登录状态, 即通过token查询redis
 * 验证成功, 则之后的接口都能够通过SecurityContextHolder.getContext().getAuthentication()获得访问已登录用户信息
 */

@Component
//OncePerRequestFilter是SpringSecurity提供的类
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if(!Strings.hasText(token)){
            //若无Token则表示接口不需要登录, 直接放行
            filterChain.doFilter(request, response);
            return;
        }

        //token解析后保存为claims
        Claims claims = null;
        try{
            claims = JwtUtil.parseJWT(token);
        }catch (Exception e){
            //解析出现异常, 表示token有异常, 将异常响应给前端
            ResponseResult error = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(error));
            return;
        }

        String userId = claims.getSubject();

        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);

        if(Objects.isNull(loginUser)){
            //redis没有保存该userId的信息, 需要重新登录, 响应前端needLogin
            ResponseResult needLogin = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(needLogin));
            return;
        }

        //将读取的信息转换为Authentication保存到SecurityContextHolder
        ///此处保存不太一样, 不是单单保存用户名和密码, 而是保存继承UserDetails类型的loginUser
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //System.out.println(SecurityContextHolder.getContext().getAuthentication());

        //传递过滤器
        filterChain.doFilter(request, response);
    }
}
