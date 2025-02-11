package com.socialmedia.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link UnauthorizedException} class.
 * <p>
 * This class ensures that the {@link UnauthorizedException} behaves correctly,
 * including its constructors and message handling.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class UnauthorizedExceptionTest {

    /**
     * Tests the constructor of UnauthorizedException with a valid message.
     * Verifies that the message is set correctly.
     */
    @Test
    @DisplayName("Test UnauthorizedException constructor with valid message")
    void testConstructorWithMessage() {
        // Arrange
        String errorMessage = "Unauthorized access";

        // Act: Create a new instance with the message
        UnauthorizedException exception = new UnauthorizedException(errorMessage);

        // Assert: Verify that the exception is not null and the message matches
        assertNotNull(exception, "UnauthorizedException instance should not be null");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the input message");
    }

    /**
     * Tests the constructor of UnauthorizedException with a null message.
     * Verifies that the message is set to null.
     */
    @Test
    @DisplayName("Test UnauthorizedException constructor with null message")
    void testConstructorWithNullMessage() {
        // Arrange
        String errorMessage = null;

        // Act: Create a new instance with a null message
        UnauthorizedException exception = new UnauthorizedException(errorMessage);

        // Assert: Verify that the exception is not null and the message is null
        assertNotNull(exception, "UnauthorizedException instance should not be null");
        assertNull(exception.getMessage(), "Exception message should be null when initialized with null");
    }

    /**
     * Tests the constructor of UnauthorizedException with an empty message.
     * Verifies that the message is set to an empty string.
     */
    @Test
    @DisplayName("Test UnauthorizedException constructor with empty message")
    void testConstructorWithEmptyMessage() {
        // Arrange
        String errorMessage = "";

        // Act: Create a new instance with an empty message
        UnauthorizedException exception = new UnauthorizedException(errorMessage);

        // Assert: Verify that the exception is not null and the message is an empty string
        assertNotNull(exception, "UnauthorizedException instance should not be null");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should be an empty string");
    }
}
