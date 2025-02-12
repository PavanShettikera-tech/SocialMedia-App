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
 * <p>
 * Feedback Incorporation:
 * <ul>
 *     <li>Added comprehensive docstrings for each test method.</li>
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
class InvalidInputExceptionTest {

    /**
     * Tests the constructor of {@link InvalidInputException} with a valid message.
     * <p>
     * This test verifies that when a valid, non-null, non-empty message is provided to the
     * {@link InvalidInputException} constructor, the exception instance is created correctly
     * with the specified message.
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Valid, non-null, and non-empty message string is provided.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Pass/Fail Conditions:
     * <ul>
     *     <li>Pass: The exception instance is not null, and the message matches the input message.</li>
     *     <li>Fail: The exception instance is null, or the message does not match the input message.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test InvalidInputException constructor with valid message")
    void testConstructorWithMessage() {
        // Arrange: Define a valid error message
        String errorMessage = "Invalid input provided";

        // Act: Create a new instance of InvalidInputException with the valid message
        InvalidInputException exception = new InvalidInputException(errorMessage);

        // Assert: Verify that the exception instance is created correctly with the specified message
        assertNotNull(exception, "InvalidInputException instance should not be null");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the input message");
    }

    /**
     * Tests the constructor of {@link InvalidInputException} with a null message.
     * <p>
     * This test verifies that when a null message is provided to the
     * {@link InvalidInputException} constructor, the exception instance is created correctly
     * with a null message.
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Null message is provided.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Pass/Fail Conditions:
     * <ul>
     *     <li>Pass: The exception instance is not null, and the message is null.</li>
     *     <li>Fail: The exception instance is null, or the message is not null.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test InvalidInputException constructor with null message")
    void testConstructorWithNullMessage() {
        // Arrange: Define a null error message
        String errorMessage = null;

        // Act: Create a new instance of InvalidInputException with the null message
        InvalidInputException exception = new InvalidInputException(errorMessage);

        // Assert: Verify that the exception instance is created correctly with a null message
        assertNotNull(exception, "InvalidInputException instance should not be null");
        assertNull(exception.getMessage(), "Exception message should be null when initialized with null");
    }

    /**
     * Tests the constructor of {@link InvalidInputException} with an empty message.
     * <p>
     * This test verifies that when an empty string is provided to the
     * {@link InvalidInputException} constructor, the exception instance is created correctly
     * with an empty message.
     * </p>
     *
     * <p>
     * Error Conditions:
     * <ul>
     *     <li>Empty string message is provided.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Pass/Fail Conditions:
     * <ul>
     *     <li>Pass: The exception instance is not null, and the message is an empty string.</li>
     *     <li>Fail: The exception instance is null, or the message is not an empty string.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test InvalidInputException constructor with empty message")
    void testConstructorWithEmptyMessage() {
        // Arrange: Define an empty error message
        String errorMessage = "";

        // Act: Create a new instance of InvalidInputException with the empty message
        InvalidInputException exception = new InvalidInputException(errorMessage);

        // Assert: Verify that the exception instance is created correctly with an empty message
        assertNotNull(exception, "InvalidInputException instance should not be null");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should be an empty string");
    }
}
