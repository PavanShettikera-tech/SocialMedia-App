package com.socialmedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Configuration class for setting up Redis-related beans.
 *
 * <p><strong>Overview:</strong></p>
 * This class configures the necessary beans for interacting with Redis using Lettuce as the client.
 *
 * <p><strong>References:</strong></p>
 * <ul>
 *   <li>{@link LettuceConnectionFactory} from Spring Data Redis is used to establish connections to the Redis server.</li>
 *   <li>{@link RedisTemplate} is configured for Redis operations with String keys and Object values.</li>
 * </ul>
 *
 * <p><strong>Functionality:</strong></p>
 * <ul>
 *   <li>Defines beans for {@link RedisConnectionFactory} and {@link RedisTemplate} with detailed configurations.</li>
 *   <li>Specifies acceptable host and port configurations for connecting to the Redis server.</li>
 *   <li>Includes pass/fail conditions for bean creation based on connection success.</li>
 * </ul>
 */
@Configuration
public class RedisConfig {

    /**
     * Creates and configures a {@link RedisConnectionFactory} bean using Lettuce.
     *
     * <p><strong>Description:</strong></p>
     * This method initializes a {@link LettuceConnectionFactory} with the specified Redis host and port.
     * It serves as the primary connection factory for Redis operations within the application.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>host</strong>: The hostname of the Redis server. Default is "localhost".</li>
     *   <li><strong>port</strong>: The port number on which the Redis server is listening. Default is 6379.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the Redis server is not running on the specified host and port (e.g., localhost:6379), a connection exception will occur at runtime.</li>
     *   <li>If invalid host or port values are provided, the connection factory may fail to initialize properly.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *   <li><strong>host:</strong> A valid hostname or IP address where the Redis server is accessible.</li>
     *   <li><strong>port:</strong> An integer between 1 and 65535. The default Redis port is 6379.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The Redis server must be accessible at the specified host and port.</li>
     *   <li>Network configurations (firewalls, security groups) must allow traffic on the Redis port.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully creates a {@link LettuceConnectionFactory} instance with the provided host and port.</li>
     *   <li><strong>Fail:</strong> Throws an exception if the connection to Redis cannot be established with the provided configurations.</li>
     * </ul>
     *
     * @return A configured {@link LettuceConnectionFactory} instance for Redis connections.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // For default settings, LettuceConnectionFactory connects to localhost:6379
        return new LettuceConnectionFactory("localhost", 6379);
    }

    /**
     * Creates and configures a {@link RedisTemplate} bean for Redis operations.
     *
     * <p><strong>Description:</strong></p>
     * This method sets up a {@link RedisTemplate} with String keys and Object values, utilizing the configured {@link RedisConnectionFactory}.
     * The template facilitates Redis operations such as value setting, retrieval, and manipulation.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li>No direct parameters; relies on the {@link RedisConnectionFactory} bean.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the {@link RedisConnectionFactory} is null or cannot establish a connection, the {@link RedisTemplate} creation will fail.</li>
     *   <li>Misconfigurations in the connection factory may lead to runtime exceptions during Redis operations.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *   <li>Keys: Must be non-null Strings.</li>
     *   <li>Values: Can be any Object type that is serializable by the configured serializers.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The Redis connection factory must be properly initialized and connected to the Redis server.</li>
     *   <li>The template should handle serialization and deserialization of keys and values correctly.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully creates a {@link RedisTemplate} with a valid connection factory, enabling Redis operations.</li>
     *   <li><strong>Fail:</strong> If the {@link RedisConnectionFactory} is null or cannot connect, bean creation fails, potentially causing application startup to fail.</li>
     * </ul>
     *
     * @return A configured {@link RedisTemplate} instance with String keys and Object values for performing Redis operations.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
