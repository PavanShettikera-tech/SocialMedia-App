package com.socialmedia.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link InvalidInputException} class.
 * <p>
 * This class ensures that the {@link InvalidInputException} behaves correctly,
 * including its constructors and message handling.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class InvalidInputExceptionTest {

    /**
     * Tests the constructor of InvalidInputException with a valid message.
     * Verifies that the message is set correctly.
     */
    @Test
    @DisplayName("Test InvalidInputException constructor with valid message")
    void testConstructorWithMessage() {
        // Arrange
        String errorMessage = "Invalid input provided";

        // Act: Create a new instance with the message
        InvalidInputException exception = new InvalidInputException(errorMessage);

        // Assert: Verify that the exception is not null and the message matches
        assertNotNull(exception, "InvalidInputException instance should not be null");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the input message");
    }

    /**
     * Tests the constructor of InvalidInputException with a null message.
     * Verifies that the message is set to null.
     */
    @Test
    @DisplayName("Test InvalidInputException constructor with null message")
    void testConstructorWithNullMessage() {
        // Arrange
        String errorMessage = null;

        // Act: Create a new instance with a null message
        InvalidInputException exception = new InvalidInputException(errorMessage);

        // Assert: Verify that the exception is not null and the message is null
        assertNotNull(exception, "InvalidInputException instance should not be null");
        assertNull(exception.getMessage(), "Exception message should be null when initialized with null");
    }

    /**
     * Tests the constructor of InvalidInputException with an empty message.
     * Verifies that the message is set to an empty string.
     */
    @Test
    @DisplayName("Test InvalidInputException constructor with empty message")
    void testConstructorWithEmptyMessage() {
        // Arrange
        String errorMessage = "";

        // Act: Create a new instance with an empty message
        InvalidInputException exception = new InvalidInputException(errorMessage);

        // Assert: Verify that the exception is not null and the message is an empty string
        assertNotNull(exception, "InvalidInputException instance should not be null");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should be an empty string");
    }
}
