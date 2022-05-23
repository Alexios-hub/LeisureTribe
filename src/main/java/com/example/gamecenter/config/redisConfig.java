package com.example.gamecenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class redisConfig {
  @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
      //创建RedisTemplate<String,Object>对象
      RedisTemplate<String,Object>template=new RedisTemplate<>();
      //配置连接工厂(将application.properties中的配置信息读入到template，然后返回)
      template.setConnectionFactory(factory);
      return template;
  }
  @Bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory){
    StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
    stringRedisTemplate.setConnectionFactory(factory);
    return stringRedisTemplate;
  }

}
