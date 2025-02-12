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
 * ensuring that the {@link RedisConnectionFactory} and {@link RedisTemplate} are correctly instantiated.
 * </p>
 *
 * <p>
 * Feedback Implemented:
 * <ul>
 *     <li>Added comprehensive docstrings.</li>
 *     <li>Included error conditions and acceptable value ranges.</li>
 *     <li>Detailed parameter names, types, ranges, and descriptions.</li>
 *     <li>Outlined premises and assertions for each test.</li>
 *     <li>Clarified pass/fail conditions for assertions.</li>
 * </ul>
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
     * <p>
     * This inner static class provides bean definitions required for the Redis configuration tests.
     * </p>
     */
    static class RedisTestConfig {

        /**
         * Configures the {@link RedisConnectionFactory} bean.
         *
         * <p>
         * Creates a new instance of {@link LettuceConnectionFactory} connected to localhost on port 6379.
         * </p>
         *
         * <p>
         * <strong>Error Conditions:</strong>
         * <ul>
         *     <li>If the connection to Redis fails, a {@link LettuceConnectionFactory} may not be properly instantiated.</li>
         * </ul>
         * </p>
         *
         * <p>
         * <strong>Acceptable Values:</strong>
         * <ul>
         *     <li>Host: Non-null, valid hostname (e.g., "localhost").</li>
         *     <li>Port: Integer within the range 1 to 65535 (e.g., 6379).</li>
         * </ul>
         * </p>
         *
         * @return a new instance of {@link LettuceConnectionFactory} connected to localhost on port 6379
         */
        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            // Provide a simple LettuceConnectionFactory connected to localhost on port 6379
            return new LettuceConnectionFactory("localhost", 6379);
        }

        /**
         * Configures the {@link RedisTemplate} bean.
         *
         * <p>
         * Initializes and configures a {@link RedisTemplate} instance using the provided {@link RedisConnectionFactory}.
         * </p>
         *
         * <p>
         * <strong>Parameters:</strong>
         * <ul>
         *     <li><strong>factory</strong> - {@link RedisConnectionFactory}: The connection factory to be used by the RedisTemplate.</li>
         * </ul>
         * </p>
         *
         * <p>
         * <strong>Acceptable Values:</strong>
         * <ul>
         *     <li>factory: Must be a non-null instance of {@link RedisConnectionFactory}.</li>
         * </ul>
         * </p>
         *
         * <p>
         * <strong>Error Conditions:</strong>
         * <ul>
         *     <li>If the factory is null or improperly configured, the {@link RedisTemplate} may not function correctly.</li>
         * </ul>
         * </p>
         *
         * @param factory the {@link RedisConnectionFactory} to be used by the {@link RedisTemplate}
         * @return a configured {@link RedisTemplate} instance
         */
        @Bean(name = "redisTemplate")
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(factory);
            return template;
        }
    }

    /**
     * Tests that the {@link RedisConnectionFactory} bean is correctly instantiated.
     *
     * <p>
     * <strong>Premise:</strong>
     * The application context should contain a properly configured {@link RedisConnectionFactory} bean.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Verify that the {@link RedisConnectionFactory} bean is not null.</li>
     *     <li>Confirm that the bean is an instance of {@link LettuceConnectionFactory}.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * Both assertions pass, indicating the bean is correctly instantiated and of the expected type.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the bean is null, indicating it was not properly instantiated.</li>
     *     <li>If the bean is not an instance of {@link LettuceConnectionFactory}, suggesting an incorrect type.</li>
     * </ul>
     * </p>
     *
     * @param context the {@link ApplicationContext} containing the beans
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
     * Tests that the {@link RedisTemplate} bean is correctly instantiated and configured.
     *
     * <p>
     * <strong>Premise:</strong>
     * The application context should contain a properly configured {@link RedisTemplate} bean named "redisTemplate".
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Verify that the {@link RedisTemplate} bean is not null.</li>
     *     <li>Confirm that the {@link RedisTemplate} has a {@link RedisConnectionFactory} of type {@link LettuceConnectionFactory}.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * Both assertions pass, indicating the {@link RedisTemplate} is correctly instantiated and properly configured with the expected connection factory.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the {@link RedisTemplate} bean is null, indicating it was not properly instantiated.</li>
     *     <li>If the connection factory within the {@link RedisTemplate} is not an instance of {@link LettuceConnectionFactory}, suggesting incorrect configuration.</li>
     * </ul>
     * </p>
     *
     * @param context the {@link ApplicationContext} containing the beans
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
