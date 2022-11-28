package com.cms.order.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class RedisConfigTest {

    private RedisServer redisServer;

    public RedisConfigTest(RedisProperties redisProperties) {
        this.redisServer = RedisServer.builder()
                .port(redisProperties.getPort())
                .build();
    }

    @PostConstruct
    public void startRedis() {
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}