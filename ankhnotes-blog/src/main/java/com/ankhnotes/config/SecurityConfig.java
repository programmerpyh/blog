package com.ankhnotes.config;

import com.ankhnotes.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
/**
 * 用于设置Spring Security
 */
// WebSecurityConfigurerAdapter: 这是Spring Security提供的一个适配器类
// 它简化了安全配置的过程。通过继承这个类，你可以重写一些方法来配置特定的安全性设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean//获得认证管理器, 用于调用.authenticate方法启动对封装了用户名和密码的authenticationToken的认证, 从而判断登陆是否成功
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    //把官方的PasswordEncoder密码加密方式替换成BCryptPasswordEncoder
    //PasswordEncoder用于Spring Security自行去做密码核对
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    //注入jwt认证过滤器, 用于验证登录状态
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    public AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    //禁用CSRF（Cross-Site Request Forgery）防护，通常在使用Token认证的情况下禁用
                    .csrf().disable()
                    //不通过Session获取SecurityContext, 禁用Session，采用无状态的Token认证方式
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    // 开始定义请求的授权规则
                    .authorizeRequests()
                    // 对于登录接口 允许匿名访问, 即不需要认证就可以访问
                    .antMatchers("/login").anonymous()

                    //为方便测试认证过滤器，我把查询友链的接口设置为需要登录才能访问。然后我们去访问的时候就能测试登录认证功能了
                    //.antMatchers("/link/getAllLink").authenticated()

                    .antMatchers("/logout").authenticated()

                    //设置用户只有登录了才能发送评论
                    .antMatchers(HttpMethod.POST, "/comment").authenticated()

                    //只有登录后才能获取个人中心的用户信息
                    .antMatchers("/user/userInfo").authenticated()

                    //登录后才能上传文件(好像前端没有给token, 先注释)
                    //.antMatchers("/upload").authenticated()

                    // 允许所有其他请求不需要认证就可以访问。
                    .anyRequest().permitAll();

        //设置自定义异常处理器
        http.exceptionHandling()
                //设置自定义认证异常处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                //设置自定义授权异常处理器
                .accessDeniedHandler(accessDeniedHandler);

        //禁用注销功能
        http.logout().disable();

        //启用JWT认证过滤器,将JWT认证过滤器放置在过滤器链的最前面
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //允许跨域
        http.cors();
    }
}
