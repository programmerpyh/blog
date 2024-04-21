package com.ankhnotes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


// redis中
// "bloglogin: "代表前台登录用户的缓存
// "login: "代表后台登录用户的缓存

@Configuration
public class RedisConfig {

    /*
    自定义Redis Template, 比SpringBoot自带的更好用
     */
    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })//用于抑制编译器产生的警告，因为在泛型类型的处理上可能会出现一些警告。
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        //经过如下设置, key即为String格式, value为Json格式

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        // Hash的key也采用StringRedisSerializer的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }

}
