package com.ankhnotes.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * 自定义RedisSerializer, 通过继承RedisSerializer来实现
 * 配置Redis使用FastJson序列化, 即Redis的FastJson序列化器
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Class<T> clazz;

    /*
     *配置FastJson以在反序列化过程中支持自动类型检测。通过在ParserConfig类中启用自动类型支持来实现这一点。
     */
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 序列化
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        return t!=null ? JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET) : new byte[0];
    }

    /**
     * 反序列化
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {

        if(bytes==null||bytes.length<=0)
            return null;

        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }

    /*
    提供了一个受保护的方法，用于获取JavaType对象，该对象表示指定类的类型信息。
     */
    protected JavaType getJavaType(Class<?> clazz){
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
