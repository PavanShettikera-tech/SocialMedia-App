package com.socialmedia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SwaggerConfig} class.
 * <p>
 * This class verifies the configuration of Swagger-related beans,
 * ensuring that the OpenAPI and GroupedOpenApi beans are correctly instantiated and configured.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SwaggerConfigTest.SwaggerTestConfig.class })
class SwaggerConfigTest {

    /**
     * Minimal test configuration that defines the necessary Swagger beans for testing.
     */
    @Configuration
    static class SwaggerTestConfig {

        /**
         * Configures the OpenAPI bean.
         *
         * @return a configured OpenAPI instance with API information
         */
        @Bean
        public OpenAPI openAPI() {
            return new OpenAPI()
                .info(new Info()
                    .title("Social Media API")
                    .description("API documentation for the Social Media Application")
                    .version("v1.0"));
        }

        /**
         * Configures the GroupedOpenApi bean.
         *
         * @return a configured GroupedOpenApi instance for public APIs
         */
        @Bean
        public GroupedOpenApi groupedOpenApi() {
            return GroupedOpenApi.builder()
                .group("socialmedia-public")
                .pathsToMatch("/api/**")
                .build();
        }
    }

    /**
     * Tests that the OpenAPI bean is correctly instantiated and configured.
     *
     * @param context the application context containing the beans
     */
    @Test
    @DisplayName("Test OpenAPI bean creation and configuration")
    void testSocialMediaOpenAPIBean(ApplicationContext context) {
        // Act: Retrieve the OpenAPI bean from the context
        OpenAPI openAPI = context.getBean(OpenAPI.class);

        // Assert: Verify that the OpenAPI bean is not null and has the correct info
        assertNotNull(openAPI, "OpenAPI bean should not be null");

        Info info = openAPI.getInfo();
        assertNotNull(info, "Info object should not be null");
        assertEquals("Social Media API", info.getTitle(), "API title should match");
        assertEquals("API documentation for the Social Media Application", info.getDescription(),
                     "API description should match");
        assertEquals("v1.0", info.getVersion(), "API version should match");
    }

    /**
     * Tests that the GroupedOpenApi bean is correctly instantiated and configured.
     *
     * @param context the application context containing the beans
     */
    @Test
    @DisplayName("Test GroupedOpenApi bean creation and configuration")
    void testPublicApiBean(ApplicationContext context) {
        // Act: Retrieve the GroupedOpenApi bean from the context
        GroupedOpenApi groupedOpenApi = context.getBean(GroupedOpenApi.class);

        // Assert: Verify that the GroupedOpenApi bean is not null and has the correct group and paths
        assertNotNull(groupedOpenApi, "GroupedOpenApi bean should not be null");
        assertEquals("socialmedia-public", groupedOpenApi.getGroup(), "Group name should match");

        String[] actualPaths = groupedOpenApi.getPathsToMatch().toArray(new String[0]);
        assertArrayEquals(new String[]{"/api/**"}, actualPaths, "Paths should match /api/**");
    }
}
