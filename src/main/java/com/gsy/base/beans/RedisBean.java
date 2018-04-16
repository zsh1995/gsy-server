package com.gsy.base.beans;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Created by mrzsh on 2018/1/27.
 */
@Component
public class RedisBean {

    private StringRedisTemplate template;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public RedisBean(StringRedisTemplate template){
        this.template = template;
    }

    public Object get(String name){
        return redisTemplate.opsForValue().get(name);
    }
    public void set(String name,Object T){
        redisTemplate.opsForValue().set(name,T,30, TimeUnit.MINUTES);
    }
    public void set(String name,Object T,int time){
        redisTemplate.opsForValue().set(name,T,time, TimeUnit.MILLISECONDS);
    }
    public void delete(String name){
        redisTemplate.delete(name);
    }

}
