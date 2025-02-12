package com.socialmedia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger (OpenAPI) documentation.
 *
 * <p><strong>Overview:</strong></p>
 * This class configures Swagger/OpenAPI documentation for the Social Media application,
 * enabling developers and stakeholders to interact with and understand the available REST APIs.
 *
 * <p><strong>References:</strong></p>
 * <ul>
 *   <li>{@link OpenAPI} from Swagger is used to define the API metadata.</li>
 *   <li>{@link GroupedOpenApi} from SpringDoc is utilized to group and filter API endpoints.</li>
 * </ul>
 *
 * <p><strong>Functionality:</strong></p>
 * <ul>
 *   <li>Defines the primary OpenAPI documentation with application-specific information.</li>
 *   <li>Creates a grouped OpenAPI instance for public APIs under the "/api/**" path.</li>
 *   <li>Ensures that Swagger UI is accessible for API exploration and testing.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong></p>
 * <ul>
 *   <li><strong>Pass:</strong> Swagger documentation loads successfully at `/swagger-ui.html` or `/api-docs`.</li>
 *   <li><strong>Fail:</strong> Documentation fails to generate if the necessary libraries are missing or misconfigured.</li>
 * </ul>
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates and configures the primary {@link OpenAPI} bean.
     *
     * <p><strong>Description:</strong></p>
     * This method initializes the OpenAPI documentation with essential metadata such as title, description,
     * and version. This information is displayed in the Swagger UI and serves as the foundational documentation
     * for the REST APIs provided by the Social Media application.
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the Swagger library is not included in the project dependencies, this bean will fail to initialize.</li>
     *   <li>Incorrect metadata configuration may lead to misleading or incomplete API documentation.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *   <li><strong>Title:</strong> A descriptive title for the API documentation (e.g., "Social Media API").</li>
     *   <li><strong>Description:</strong> A detailed description of the API's purpose and functionalities.</li>
     *   <li><strong>Version:</strong> The version of the API (e.g., "v1.0").</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The OpenAPI bean must be correctly configured to reflect the current state and capabilities of the API.</li>
     *   <li>Metadata provided should accurately represent the API to avoid confusion among users.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully creates an {@link OpenAPI} instance with the specified metadata.</li>
     *   <li><strong>Fail:</strong> Throws an exception if the OpenAPI configuration is invalid or incomplete.</li>
     * </ul>
     *
     * @return A configured {@link OpenAPI} instance containing the API's metadata.
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
     * <p><strong>Description:</strong></p>
     * This method sets up a grouped OpenAPI instance named "socialmedia-public" that encompasses all REST endpoints
     * matching the "/api/**" pattern. Grouping APIs helps in organizing the documentation, especially when dealing
     * with a large number of endpoints or multiple API versions.
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the OpenAPI documentation fails to load, possibly due to missing libraries or misconfigurations.</li>
     *   <li>Incorrect path patterns may result in APIs not being documented as intended.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *   <li><strong>Group Name:</strong> A unique identifier for the API group (e.g., "socialmedia-public").</li>
     *   <li><strong>Path Patterns:</strong> Valid Ant-style path patterns that match the desired API endpoints (e.g., "/api/**").</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The specified path patterns must accurately reflect the endpoints intended for public access.</li>
     *   <li>The group name should be unique and descriptive to avoid confusion with other API groups.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully creates a {@link GroupedOpenApi} instance that includes all specified endpoints.</li>
     *   <li><strong>Fail:</strong> Documentation does not appear or is incomplete if the group configuration is incorrect or if required libraries are missing.</li>
     * </ul>
     *
     * @return A configured {@link GroupedOpenApi} instance for grouping public APIs under "/api/**".
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("socialmedia-public")
                .pathsToMatch("/api/**")
                .build();
    }
}
