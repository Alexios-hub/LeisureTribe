package com.example.gamecenter.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;
@Component
public class redisUtil {
    @Autowired
    private RedisTemplate<String,Object>redisTemplate;
    //set
    public void set(String key,Object value,long time){
        Assert.notNull(key,"key不能为空!");
        Assert.notNull(value,"value不能为空");
        Assert.notNull(time,"time不能为空!");
        redisTemplate.opsForValue().set(key,value,time, TimeUnit.MINUTES);
    }

    public Object get(String key){
        Assert.notNull(key,"key不能为空!");
        return redisTemplate.opsForValue().get(key);

    }

    //del
    public void delKey(String key){
        Assert.notNull(key,"key 不能为空!");
        redisTemplate.delete(key);
    }

    public void expire(String key,long time){
        Assert.notNull(key,"key 不能为空");
        redisTemplate.expire(key,time,TimeUnit.MINUTES);
    }
}
