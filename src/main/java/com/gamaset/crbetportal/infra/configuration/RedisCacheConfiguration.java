package com.gamaset.crbetportal.infra.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@ConfigurationProperties(prefix = "spring.redis")
public class RedisCacheConfiguration {
    private String host = "";
    private int port = 6379;

    /**
     * @param redisConnectionFactory
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        RedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);

        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setStringSerializer(serializer);

        return redisTemplate;
    }


    /**
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }
}
