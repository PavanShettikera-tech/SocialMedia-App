package com.socialmedia.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * Configuration class for setting up Redis-related beans.
 *
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: This class references LettuceConnectionFactory from Spring Data Redis.</li>
 *   <li>Functions: Single, larger methods for bean creation with additional detail.</li>
 *   <li>Comments: We specify the acceptable host/port configuration and pass/fail conditions for connection creation.</li>
 * </ul>
 */
@Configuration
public class RedisConfig {


    /**
     * Creates and configures a {@link RedisConnectionFactory} bean using Lettuce.
     *
     * <p>
     * <strong>Error Conditions:</strong>  
     * - If localhost:6379 is not running, a connection exception can occur at runtime.
     * </p>
     *
     * <p>
     * <strong>Acceptable Values / Range:</strong>  
     * - Typically localhost or a remote Redis server host, default port 6379.
     * </p>
     *
     * @return A configured {@link LettuceConnectionFactory} instance.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // For default settings, LettuceConnectionFactory connects to localhost:6379
        return new LettuceConnectionFactory("localhost", 6379);
    }


    /**
     * Creates and configures a {@link RedisTemplate} bean for Redis operations.
     *
     * <p>
     * <strong>Pass/Fail Conditions:</strong>  
     * Pass: Successfully creates a RedisTemplate with valid connection.  
     * Fail: If the RedisConnectionFactory is null or cannot connect, bean creation fails.
     * </p>
     *
     * @return A configured {@link RedisTemplate} instance with String keys and Object values.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
