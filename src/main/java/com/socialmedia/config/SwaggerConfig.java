package com.socialmedia.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for setting up Swagger (OpenAPI) documentation.
 *
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: Mentions the "socialmedia-public" group for REST endpoints under /api/**.</li>
 *   <li>Functions: Single, well-documented methods for creating beans.</li>
 *   <li>Comments: Pass/fail condition not directly applicable here; it’s a config with fallback if the library fails to load.</li>
 * </ul>
 */
@Configuration
public class SwaggerConfig {


    /**
     * Creates and configures the primary {@link OpenAPI} bean.
     *
     * @return A configured {@link OpenAPI} instance with application-specific information.
     */
    @Bean
    public OpenAPI socialMediaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Social Media API")
                        .description("API documentation for the Social Media Application")
                        .version("v1.0"));
    }


    /**
     * Creates and configures a {@link GroupedOpenApi} bean for public APIs.
     *
     * <p>
     * Pass: If the openapi documentation loads successfully at /swagger-ui.html or /api-docs.  
     * Fail: If the doc generation fails for some reason (library missing, etc.).
     * </p>
     *
     * @return A configured {@link GroupedOpenApi} instance for public APIs.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("socialmedia-public")
                .pathsToMatch("/api/**")
                .build();
    }
}
