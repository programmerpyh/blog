package com.ankhnotes.utils;


import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
/**
 * 用于对Redis进行操作的Redis操作工具类
 * 自己封装, 主要是对于RedisTemplate的操作再封装
 */
public class RedisCache {

    @Autowired
    public RedisTemplate redisTemplate;// key序列化器为String类型


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }
    //重载
    public <T> void setCacheObject(final String key, final T value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置指定Redis键值对的有效时间
     * @param key 键
     * @param timeout 时间(秒)
     * @return 成功与否
     */
    public boolean expire(final String key, final long timeout){
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
    //重载
    public boolean expire(final String key, final long timeout, final TimeUnit timeUnit){
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获得Redis缓存的基本对象
     * @param key 键
     * @return 返回对应key存储的对象
     */
    public <T> T getCacheObject(final String key){
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }


    /**
     * 删除单个键值对
     * @param key 待删除的键
     * @return 成功与否
     */
    public boolean deleteObject(final String key){
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个键值对
     * @param keys 键集合
     * @return 成功与否
     */
    public long deleteObject(final Collection keys){
        return redisTemplate.delete(keys);
    }

    /**
     * 缓存List
     * @param key 键
     * @param dataList 等待缓存的List
     * @return 成功数量
     */
    public <T> Long setCacheList(final String key, final List<T> dataList){
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count!=null?count:0;
    }

    /**
     * 获得缓存的List
     * @param key 查询的key
     * @return 对应List
     */
    public <T> List<T> getCacheList(String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     * @param key 键
     * @param dataSet 元素Set
     * @return boundSetOperations
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet){
        BoundSetOperations<String, T> boundSetOperations = redisTemplate.boundSetOps(key);
        dataSet.forEach(data -> boundSetOperations.add(data));
        return boundSetOperations;
    }

    /**
     * 获得缓存的Set
     * @param key 键
     * @return 缓存的Set
     */
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     * @param key 键
     * @param dataMap 元素map
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap){
        if(dataMap!=null)
            redisTemplate.opsForHash().putAll(key, dataMap);
    }

    /**
     * 获得缓存的Map
     * @param key 键
     * @return 缓存的Map
     */
    public <T> Map<String, T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash map中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash map中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash map中的一个entry
     *
     * @param key Redis键
     * @param hkey entry的键
     */
    public void delCacheMapValue(final String key, final String hkey)
    {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 获取Hash map中的多个数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串匹配模式
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }
}
