package com.socialmedia.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link GlobalExceptionHandler} class.
 * <p>
 * This class verifies the exception handling mechanisms of the {@link GlobalExceptionHandler},
 * ensuring that appropriate HTTP responses are returned for different exception scenarios.
 * It utilizes Mockito to mock dependencies and JUnit 5 for testing.
 * </p>
 *
 * <p>
 * Feedback Incorporation:
 * <ul>
 *     <li>Added detailed docstrings for clarity.</li>
 *     <li>Specified error conditions and acceptable value ranges.</li>
 *     <li>Described parameter types and their purposes.</li>
 *     <li>Outlined premises and assertions for each test case.</li>
 *     <li>Clarified pass/fail conditions based on assertions.</li>
 * </ul>
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest webRequest;

    /**
     * Initializes the GlobalExceptionHandler and mocks necessary dependencies before each test.
     *
     * <p>
     * This setup method prepares the testing environment by:
     * <ul>
     *     <li>Initializing Mockito annotations to enable mocking.</li>
     *     <li>Creating a new instance of {@link GlobalExceptionHandler} to be tested.</li>
     *     <li>Mocking the {@link WebRequest} to simulate web request context.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Ensures that the handler and web request are properly initialized.</li>
     * </ul>
     * </p>
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations for mocking dependencies
        MockitoAnnotations.openMocks(this);

        // Create a new instance of GlobalExceptionHandler to be tested
        globalExceptionHandler = new GlobalExceptionHandler();

        // Mock the WebRequest to simulate web request context without actual HTTP calls
        webRequest = mock(WebRequest.class);
    }

    /**
     * Tests handling of {@link ResourceNotFoundException}.
     * <p>
     * This test verifies that when a {@link ResourceNotFoundException} is thrown,
     * the {@link GlobalExceptionHandler} returns a {@link ResponseEntity} with:
     * <ul>
     *     <li>HTTP status code 404 (Not Found)</li>
     *     <li>Error message matching the exception message</li>
     *     <li>A non-null timestamp indicating when the error occurred</li>
     * </ul>
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Exception message is provided and should be reflected in the response.</li>
     *     <li>HTTP status code should correspond to NOT_FOUND.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Pass/Fail Conditions:
     * <ul>
     *     <li>Pass: Response is not null, status code is 404, message matches, and timestamp is present.</li>
     *     <li>Fail: Any of the above conditions are not met.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test handling ResourceNotFoundException returns 404 Not Found")
    void handleResourceNotFoundException_ReturnsNotFoundResponse() {
        // Arrange: Create a ResourceNotFoundException instance with a specific message
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        // Act: Handle the exception using GlobalExceptionHandler to get the response
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(ex, webRequest);

        // Assert: Verify the response is correctly formed
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "HTTP status should be 404 Not Found");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.NOT_FOUND.value(), body.getStatus(), "Status in body should be 404");
        assertEquals("Resource not found", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }

    /**
     * Tests handling of {@link InvalidInputException}.
     * <p>
     * This test verifies that when an {@link InvalidInputException} is thrown,
     * the {@link GlobalExceptionHandler} returns a {@link ResponseEntity} with:
     * <ul>
     *     <li>HTTP status code 400 (Bad Request)</li>
     *     <li>Error message matching the exception message</li>
     *     <li>A non-null timestamp indicating when the error occurred</li>
     * </ul>
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Exception message is provided and should be reflected in the response.</li>
     *     <li>HTTP status code should correspond to BAD_REQUEST.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Pass/Fail Conditions:
     * <ul>
     *     <li>Pass: Response is not null, status code is 400, message matches, and timestamp is present.</li>
     *     <li>Fail: Any of the above conditions are not met.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test handling InvalidInputException returns 400 Bad Request")
    void handleInvalidInputException_ReturnsBadRequestResponse() {
        // Arrange: Create an InvalidInputException instance with a specific message
        InvalidInputException ex = new InvalidInputException("Invalid input");

        // Act: Handle the exception using GlobalExceptionHandler to get the response
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidInputException(ex, webRequest);

        // Assert: Verify the response is correctly formed
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "HTTP status should be 400 Bad Request");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus(), "Status in body should be 400");
        assertEquals("Invalid input", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }

    /**
     * Tests handling of {@link UnauthorizedException}.
     * <p>
     * This test verifies that when an {@link UnauthorizedException} is thrown,
     * the {@link GlobalExceptionHandler} returns a {@link ResponseEntity} with:
     * <ul>
     *     <li>HTTP status code 401 (Unauthorized)</li>
     *     <li>Error message matching the exception message</li>
     *     <li>A non-null timestamp indicating when the error occurred</li>
     * </ul>
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Exception message is provided and should be reflected in the response.</li>
     *     <li>HTTP status code should correspond to UNAUTHORIZED.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Pass/Fail Conditions:
     * <ul>
     *     <li>Pass: Response is not null, status code is 401, message matches, and timestamp is present.</li>
     *     <li>Fail: Any of the above conditions are not met.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test handling UnauthorizedException returns 401 Unauthorized")
    void handleUnauthorizedException_ReturnsUnauthorizedResponse() {
        // Arrange: Create an UnauthorizedException instance with a specific message
        UnauthorizedException ex = new UnauthorizedException("Unauthorized");

        // Act: Handle the exception using GlobalExceptionHandler to get the response
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUnauthorizedException(ex, webRequest);

        // Assert: Verify the response is correctly formed
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(), "HTTP status should be 401 Unauthorized");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.getStatus(), "Status in body should be 401");
        assertEquals("Unauthorized", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }

    /**
     * Tests handling of generic {@link Exception}.
     * <p>
     * This test verifies that when a generic {@link Exception} is thrown,
     * the {@link GlobalExceptionHandler} returns a {@link ResponseEntity} with:
     * <ul>
     *     <li>HTTP status code 500 (Internal Server Error)</li>
     *     <li>Error message matching the exception message</li>
     *     <li>A non-null timestamp indicating when the error occurred</li>
     * </ul>
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Exception message is provided and should be reflected in the response.</li>
     *     <li>HTTP status code should correspond to INTERNAL_SERVER_ERROR.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Pass/Fail Conditions:
     * <ul>
     *     <li>Pass: Response is not null, status code is 500, message matches, and timestamp is present.</li>
     *     <li>Fail: Any of the above conditions are not met.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test handling generic Exception returns 500 Internal Server Error")
    void handleGlobalException_ReturnsInternalServerErrorResponse() {
        // Arrange: Create a generic Exception instance with a specific message
        Exception ex = new Exception("Internal server error");

        // Act: Handle the exception using GlobalExceptionHandler to get the response
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(ex, webRequest);

        // Assert: Verify the response is correctly formed
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "HTTP status should be 500 Internal Server Error");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus(), "Status in body should be 500");
        assertEquals("Internal server error", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }
}
