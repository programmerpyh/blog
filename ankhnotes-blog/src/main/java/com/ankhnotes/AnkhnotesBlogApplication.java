package com.ankhnotes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

//扫描三个模块的包
@SpringBootApplication()
@MapperScan(basePackages = "com.ankhnotes.mapper")
@EnableConfigurationProperties //允许获取application.properties的属性
@EnableScheduling//@EnableScheduling是spring提供的定时任务的注解
public class AnkhnotesBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnkhnotesBlogApplication.class, args);
    }

}
