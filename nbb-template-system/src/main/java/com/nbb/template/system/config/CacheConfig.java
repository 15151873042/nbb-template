package com.nbb.template.system.config;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 胡鹏
 */
@Configuration
public class CacheConfig {

//    /**
//     * 配置 Redis 缓存管理器，使用 Jackson 序列化
//     */
//    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
//        ObjectMapper copyObjectMapper = objectMapper.copy();
//        // 如果没有此配置项，value值序列化到redis是一个纯json，反序列化时候，无法匹配对应的Class对象，会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
//        copyObjectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//
//        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        jacksonSerializer.setObjectMapper(copyObjectMapper);
//
//        // 2. 配置缓存键和值的序列化方式
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(30)) // 默认缓存过期时间
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // 键用 String 序列化
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer)) // 值用 Jackson 序列化
//                .disableCachingNullValues(); // 不缓存 null 值
//
//
//        // 4. 创建缓存管理器
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(defaultCacheConfig) // 默认配置
//                .build();
//    }


    /**
     * RedisCacheConfiguration Bean
     * <p>
     * 参考 org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration 的 createConfiguration 方法
     */
    @Bean
    @Primary
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties, ObjectMapper objectMapper) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置使用 : 单冒号，而不是双 :: 冒号，避免 Redis Desktop Manager 多余空格
        // 详细可见 https://blog.csdn.net/chuixue24/article/details/103928965 博客
        // 再次修复单冒号，而不是双 :: 冒号问题，Issues 详情：https://gitee.com/zhijiantianya/yudao-cloud/issues/I86VY2
        config = config.computePrefixWith(cacheName -> {
            String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
            if (StringUtils.hasText(keyPrefix)) {
                keyPrefix = keyPrefix.lastIndexOf(StrUtil.COLON) == -1 ? keyPrefix + StrUtil.COLON : keyPrefix;
                return keyPrefix + cacheName + StrUtil.COLON;
            }
            return cacheName + StrUtil.COLON;
        });

        // 设置使用 JSON 序列化方式
        ObjectMapper copyObjectMapper = objectMapper.copy();
        // 如果没有此配置项，value值序列化到redis是一个纯json，反序列化时候，无法匹配对应的Class对象，会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
        copyObjectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        GenericJackson2JsonRedisSerializer jacksonJsonRedisSerializer = new GenericJackson2JsonRedisSerializer(copyObjectMapper);
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jacksonJsonRedisSerializer));

        // 设置 CacheProperties.Redis 的属性
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }
}
