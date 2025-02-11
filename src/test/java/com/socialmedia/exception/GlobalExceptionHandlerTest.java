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
 * @version 1.0
 * @since 2025-01-28
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest webRequest;

    /**
     * Initializes the GlobalExceptionHandler and mocks before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Create a new instance of GlobalExceptionHandler
        globalExceptionHandler = new GlobalExceptionHandler();

        // Mock the WebRequest
        webRequest = mock(WebRequest.class);
    }

    /**
     * Tests handling of ResourceNotFoundException.
     * Verifies that a 404 Not Found response is returned with the correct error details.
     */
    @Test
    @DisplayName("Test handling ResourceNotFoundException returns 404 Not Found")
    void handleResourceNotFoundException_ReturnsNotFoundResponse() {
        // Arrange: Create a ResourceNotFoundException instance
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        // Act: Handle the exception using GlobalExceptionHandler
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(ex, webRequest);

        // Assert: Verify the response status and body
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "HTTP status should be 404 Not Found");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.NOT_FOUND.value(), body.getStatus(), "Status in body should be 404");
        assertEquals("Resource not found", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }

    /**
     * Tests handling of InvalidInputException.
     * Verifies that a 400 Bad Request response is returned with the correct error details.
     */
    @Test
    @DisplayName("Test handling InvalidInputException returns 400 Bad Request")
    void handleInvalidInputException_ReturnsBadRequestResponse() {
        // Arrange: Create an InvalidInputException instance
        InvalidInputException ex = new InvalidInputException("Invalid input");

        // Act: Handle the exception using GlobalExceptionHandler
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleInvalidInputException(ex, webRequest);

        // Assert: Verify the response status and body
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "HTTP status should be 400 Bad Request");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus(), "Status in body should be 400");
        assertEquals("Invalid input", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }

    /**
     * Tests handling of UnauthorizedException.
     * Verifies that a 401 Unauthorized response is returned with the correct error details.
     */
    @Test
    @DisplayName("Test handling UnauthorizedException returns 401 Unauthorized")
    void handleUnauthorizedException_ReturnsUnauthorizedResponse() {
        // Arrange: Create an UnauthorizedException instance
        UnauthorizedException ex = new UnauthorizedException("Unauthorized");

        // Act: Handle the exception using GlobalExceptionHandler
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleUnauthorizedException(ex, webRequest);

        // Assert: Verify the response status and body
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(), "HTTP status should be 401 Unauthorized");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.getStatus(), "Status in body should be 401");
        assertEquals("Unauthorized", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }

    /**
     * Tests handling of generic Exception.
     * Verifies that a 500 Internal Server Error response is returned with the correct error details.
     */
    @Test
    @DisplayName("Test handling generic Exception returns 500 Internal Server Error")
    void handleGlobalException_ReturnsInternalServerErrorResponse() {
        // Arrange: Create a generic Exception instance
        Exception ex = new Exception("Internal server error");

        // Act: Handle the exception using GlobalExceptionHandler
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(ex, webRequest);

        // Assert: Verify the response status and body
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "HTTP status should be 500 Internal Server Error");

        ErrorResponse body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus(), "Status in body should be 500");
        assertEquals("Internal server error", body.getMessage(), "Error message should match the exception message");
        assertNotNull(body.getTimestamp(), "Timestamp should not be null");
    }
}
