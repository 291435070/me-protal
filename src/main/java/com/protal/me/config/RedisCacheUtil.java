package com.protal.me.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisCacheUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 写入缓存(数据不过期)
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 写入缓存设置有效时间(单位:分)
     * @param key
     * @param value
     * @param expire
     * @param database 指定数据库(0-15)，默认为0
     * @return
     */
    public boolean set(final String key, Object value, long expire, int database) {
        try {
            if (database > 0 && database <= 15) {
                // 切换数据库
                LettuceConnectionFactory jedisConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
                jedisConnectionFactory.setDatabase(database);
                jedisConnectionFactory.resetConnection();
                redisTemplate.setConnectionFactory(jedisConnectionFactory);
            }
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量删除key对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除key对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断key是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 批量删除模糊匹配前缀key对应的value<br>
     * key + "*" 匹配相同前缀<br>
     * "*" + key 匹配相同后缀<br>
     * 模糊匹配下*号为站位符，至少要有一位字符
     * @param keyLike
     */
    public void removeLike(final String keyLike) {
        Set<String> keys = redisTemplate.keys(keyLike + "*");
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 写入哈希缓存
     * @param key
     * @param hashKey
     * @param value
     */
    public void setHash(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 获取哈希数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHash(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 写入列表数据，value可重复
     * @param key
     * @param value
     */
    public void setList(String key, Object value) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.leftPush(key, value);    //leftPush:新写入的数据靠前排
    }

    /**
     * 获取列表数据(范围检索)
     * @param key
     * @param start:0是列表第一个元素，1是下一个元素，以此类推
     * @param end:-1表示所有
     * @return
     */
    public List<Object> getList(String key, long start, long end) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(key, start, end);
    }

    /**
     * 自增减
     * @param key
     * @param delta:大于0自增，等于0获取当前值（不增不减），小于0自减<br> 自增量、减量按delta值计算
     * @return
     */
    public Long increment(String key, long delta) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return operations.increment(key, delta);
    }

}