package com.socialmedia.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RedisConfig} class.
 * <p>
 * This class verifies the configuration of Redis-related beans,
 * ensuring that the RedisConnectionFactory and RedisTemplate are correctly instantiated.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RedisConfigTest.RedisTestConfig.class })
class RedisConfigTest {

    /**
     * Minimal test configuration that defines the necessary Redis beans for testing.
     */
    static class RedisTestConfig {

        /**
         * Configures the RedisConnectionFactory bean.
         *
         * @return a new instance of LettuceConnectionFactory connected to localhost on port 6379
         */
        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            // Provide a simple LettuceConnectionFactory connected to localhost on port 6379
            return new LettuceConnectionFactory("localhost", 6379);
        }

        /**
         * Configures the RedisTemplate bean.
         *
         * @param factory the RedisConnectionFactory to be used by the RedisTemplate
         * @return a configured RedisTemplate instance
         */
        @Bean(name = "redisTemplate")
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(factory);
            return template;
        }
    }

    /**
     * Tests that the RedisConnectionFactory bean is correctly instantiated.
     *
     * @param context the application context containing the beans
     */
    @Test
    @DisplayName("Test RedisConnectionFactory bean creation")
    void testRedisConnectionFactoryBean(ApplicationContext context) {
        // Act: Retrieve the RedisConnectionFactory bean from the context
        RedisConnectionFactory factory = context.getBean(RedisConnectionFactory.class);

        // Assert: Verify that the factory is not null and is an instance of LettuceConnectionFactory
        assertNotNull(factory, "RedisConnectionFactory should not be null");
        assertTrue(factory instanceof LettuceConnectionFactory, "Expected a LettuceConnectionFactory");
    }

    /**
     * Tests that the RedisTemplate bean is correctly instantiated and configured.
     *
     * @param context the application context containing the beans
     */
    @Test
    @DisplayName("Test RedisTemplate bean creation and configuration")
    void testRedisTemplateBean(ApplicationContext context) {
        // Act: Retrieve the RedisTemplate bean from the context
        @SuppressWarnings("unchecked")
        RedisTemplate<String, Object> redisTemplate =
            (RedisTemplate<String, Object>) context.getBean("redisTemplate");

        // Assert: Verify that the RedisTemplate is not null and has the correct ConnectionFactory
        assertNotNull(redisTemplate, "RedisTemplate should not be null");
        assertTrue(redisTemplate.getConnectionFactory() instanceof LettuceConnectionFactory,
                   "Connection factory should be LettuceConnectionFactory");
    }
}
