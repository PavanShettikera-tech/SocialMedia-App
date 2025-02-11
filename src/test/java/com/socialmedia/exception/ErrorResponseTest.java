package com.socialmedia.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ErrorResponse} class.
 * <p>
 * This class ensures that the {@link ErrorResponse} class functions correctly,
 * including its constructors, getters, setters, equals, hashCode, and toString methods.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class ErrorResponseTest {

    /**
     * Tests the no-argument constructor of ErrorResponse.
     * Verifies that a new instance is created with default values.
     */
    @Test
    @DisplayName("Test ErrorResponse no-args constructor")
    void testNoArgsConstructor() {
        // Act: Create a new instance using the no-args constructor
        ErrorResponse errorResponse = new ErrorResponse();

        // Assert: Verify that the instance is not null and fields are initialized correctly
        assertNotNull(errorResponse, "ErrorResponse instance should not be null after no-args constructor");
        assertEquals(0, errorResponse.getStatus(), "Default status should be 0");
        assertNull(errorResponse.getMessage(), "Message should be null by default");
        assertNull(errorResponse.getTimestamp(), "Timestamp should be null by default");
    }

    /**
     * Tests the all-arguments constructor of ErrorResponse.
     * Verifies that fields are initialized correctly with provided values.
     */
    @Test
    @DisplayName("Test ErrorResponse all-args constructor")
    void testAllArgsConstructor() {
        // Arrange: Define sample values for ErrorResponse
        int status = 404;
        String message = "Resource not found";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act: Create a new instance using the all-args constructor
        ErrorResponse errorResponse = new ErrorResponse(status, message, timestamp);

        // Assert: Verify that the fields are set correctly
        assertNotNull(errorResponse, "ErrorResponse instance should not be null after all-args constructor");
        assertEquals(status, errorResponse.getStatus(), "Status should match the provided value");
        assertEquals(message, errorResponse.getMessage(), "Message should match the provided value");
        assertEquals(timestamp, errorResponse.getTimestamp(), "Timestamp should match the provided value");
    }

    /**
     * Tests the setter and getter methods of ErrorResponse.
     * Verifies that fields are updated and retrieved correctly.
     */
    @Test
    @DisplayName("Test ErrorResponse setters and getters")
    void testSettersAndGetters() {
        // Arrange: Create a new instance and define sample values
        ErrorResponse errorResponse = new ErrorResponse();
        int status = 500;
        String message = "Internal server error";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act: Set the fields using setter methods
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setTimestamp(timestamp);

        // Assert: Verify that the getters return the correct values
        assertEquals(status, errorResponse.getStatus(), "Status should be set correctly");
        assertEquals(message, errorResponse.getMessage(), "Message should be set correctly");
        assertEquals(timestamp, errorResponse.getTimestamp(), "Timestamp should be set correctly");
    }

    /**
     * Tests the equals and hashCode methods of ErrorResponse.
     * Verifies that two instances with the same values are equal and have the same hash code.
     */
    @Test
    @DisplayName("Test ErrorResponse equals and hashCode methods")
    void testEqualsAndHashCode() {
        // Arrange: Create two instances with the same values
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponse errorResponse1 = new ErrorResponse(404, "Resource not found", timestamp);
        ErrorResponse errorResponse2 = new ErrorResponse(404, "Resource not found", timestamp);

        // Act & Assert: Verify that both instances are equal and have the same hash code
        assertEquals(errorResponse1, errorResponse2, "ErrorResponse instances should be equal");
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode(), "Hash codes should be equal");
    }

    /**
     * Tests the equals method of ErrorResponse with different values.
     * Verifies that two instances with different values are not equal.
     */
    @Test
    @DisplayName("Test ErrorResponse equals method with different values")
    void testNotEquals() {
        // Arrange: Create two instances with different values
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponse errorResponse1 = new ErrorResponse(404, "Resource not found", timestamp);
        ErrorResponse errorResponse2 = new ErrorResponse(500, "Internal server error", timestamp);

        // Act & Assert: Verify that the instances are not equal
        assertNotEquals(errorResponse1, errorResponse2, "ErrorResponse instances should not be equal");
        assertNotEquals(errorResponse1.hashCode(), errorResponse2.hashCode(), "Hash codes should not be equal");
    }

    /**
     * Tests the equals method of ErrorResponse when compared with null.
     * Verifies that an ErrorResponse instance is not equal to null.
     */
    @Test
    @DisplayName("Test ErrorResponse equals method with null")
    void testEqualsWithNull() {
        // Arrange: Create an instance of ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse(404, "Resource not found", LocalDateTime.now());

        // Act & Assert: Verify that the instance is not equal to null
        assertNotEquals(null, errorResponse, "ErrorResponse should not be equal to null");
    }

    /**
     * Tests the equals method of ErrorResponse when compared with an instance of a different class.
     * Verifies that an ErrorResponse instance is not equal to an instance of another class.
     */
    @Test
    @DisplayName("Test ErrorResponse equals method with different class")
    void testEqualsWithDifferentClass() {
        // Arrange: Create an instance of ErrorResponse and an instance of a different class
        ErrorResponse errorResponse = new ErrorResponse(404, "Resource not found", LocalDateTime.now());
        String otherObject = "Not an ErrorResponse";

        // Act & Assert: Verify that the instance is not equal to an object of a different class
        assertNotEquals(errorResponse, otherObject, "ErrorResponse should not be equal to an instance of a different class");
    }

    /**
     * Tests the toString method of ErrorResponse.
     * Verifies that the string representation contains all relevant fields.
     */
    @Test
    @DisplayName("Test ErrorResponse toString method")
    void testToString() {
        // Arrange: Create an instance of ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse(404, "Resource not found", LocalDateTime.now());

        // Act: Call the toString method
        String toStringResult = errorResponse.toString();

        // Assert: Verify that the string contains all relevant fields
        assertNotNull(toStringResult, "toString result should not be null");
        assertTrue(toStringResult.contains("status=404"), "toString should contain the status field");
        assertTrue(toStringResult.contains("message=Resource not found"), "toString should contain the message field");
        assertTrue(toStringResult.contains("timestamp="), "toString should contain the timestamp field");
    }

    /**
     * Tests the canEqual method of ErrorResponse.
     * Verifies that an ErrorResponse instance can equal another instance of the same class.
     */
    @Test
    @DisplayName("Test ErrorResponse canEqual method")
    void testCanEqual() {
        // Arrange: Create two instances of ErrorResponse with the same values
        ErrorResponse errorResponse1 = new ErrorResponse(404, "Resource not found", LocalDateTime.now());
        ErrorResponse errorResponse2 = new ErrorResponse(404, "Resource not found", LocalDateTime.now());

        // Act & Assert: Verify that both instances can equal each other
        assertTrue(errorResponse1.canEqual(errorResponse2), "ErrorResponse instances should be able to equal each other");
    }
}
