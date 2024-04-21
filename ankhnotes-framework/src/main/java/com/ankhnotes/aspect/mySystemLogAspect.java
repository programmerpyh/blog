package com.ankhnotes.aspect;

import com.alibaba.fastjson.JSON;
import com.ankhnotes.annotation.mySystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j //用于调用log
public class mySystemLogAspect {

    @Pointcut("@annotation(com.ankhnotes.annotation.mySystemLog)")
    public void mySystemLog(){}

    @Around("mySystemLog()")
    //需要synchronized使得方法同步执行, 避免异步带来的日志乱序输出
    public synchronized Object printLog(ProceedingJoinPoint pointcut) throws Throwable {
        log.info("======================Start======================");
        Object result = null;
        try {
            printLogBefore(pointcut);
            result = pointcut.proceed();
            printLogAfter(result);
        } catch (Throwable throwable) {
            log.info("日志输出切面检测到异常");
            throw throwable;
        }finally {
            log.info("======================End======================");
        }

        //返回方法运行结果, 不返回则运行结果前端无法接收到
        return result;
    }

    private void printLogBefore(ProceedingJoinPoint pointcut){
        //获取request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被aop的方法反射
        MethodSignature methodSignature = (MethodSignature) pointcut.getSignature();
        Method method = methodSignature.getMethod();

        //获取注解的logDescription
        mySystemLog annotation = method.getAnnotation(com.ankhnotes.annotation.mySystemLog.class);

        //获取信息
        String requestUrl = request.getRequestURL().toString();
        String interfaceDescription = annotation.logDescription();
        String requestMethod = request.getMethod();
        String requestClassName = method.getDeclaringClass().getName()
                + "." + method.getName();
        String requestIp = request.getRemoteHost();

        //请求参数有可能有MultipartFile, fastjson转换不了, 此处特殊处理
        Object[] args = pointcut.getArgs();
        for(int i=0 ; i<args.length ; i++) {
            if (args[i] instanceof MultipartFile)
                args[i] = "一张图片(fastjson转换不了, 手动转换为字符串)";
            if (args[i] instanceof HttpServletResponse)
                args[i] = "HttpServletResponse";
        }

        //fastjson转换
        String requestArgs = JSON.toJSONString(args);

        // 打印请求 URL
        log.info("请求URL   : {}", requestUrl);
        // 打印描述信息，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解的描述信息
        log.info("接口描述   : {}", interfaceDescription);
        // 打印 Http method
        log.info("请求方式   : {}", requestMethod);
        // 打印调用 controller 的全路径(全类名)、方法名
        log.info("请求类名   : {}", requestClassName);
        // 打印请求的 IP
        log.info("访问IP    : {}", requestIp);
        // 打印请求入参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
        log.info("传入参数   : {}", requestArgs);
    }

    private void printLogAfter(Object executeResult){
        String returnArgs = JSON.toJSONString(executeResult);
        log.info("返回参数   : {}", returnArgs);
    }

}
