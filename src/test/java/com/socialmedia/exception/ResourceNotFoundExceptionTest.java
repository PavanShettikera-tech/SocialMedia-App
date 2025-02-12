package com.socialmedia.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ResourceNotFoundException} class.
 * <p>
 * This class ensures that the {@link ResourceNotFoundException} behaves correctly,
 * including its constructors, message handling, and inheritance from {@link RuntimeException}.
 * </p>
 *
 * <p>
 * <strong>Feedback Incorporation:</strong>
 * <ul>
 *     <li>Added comprehensive docstrings for clarity.</li>
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
class ResourceNotFoundExceptionTest {

    /**
     * Tests the constructor of {@link ResourceNotFoundException} with a custom message.
     * <p>
     * This test verifies that when a valid, non-null, non-empty message is provided to the
     * {@link ResourceNotFoundException} constructor, the exception instance is created correctly
     * with the specified message.
     * </p>
     *
     * <p>
     * <strong>Error Conditions / Acceptable Values:</strong>
     * <ul>
     *   <li>Message can be any non-null string, including empty strings.</li>
     *   <li>Here, we test typical usage with a valid, non-empty message.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> The exception message is correctly stored and retrievable.</li>
     *   <li><strong>Fail:</strong> If the exception message does not match the input message.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test ResourceNotFoundException with custom message")
    void testExceptionMessage() {
        // Arrange: Define a custom error message
        String message = "Resource not found";

        // Act: Create a new instance of ResourceNotFoundException with the custom message
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        // Assert: Verify that the exception message matches the input message
        assertEquals(message, exception.getMessage(), "Exception message should match the input message");
    }

    /**
     * Tests the inheritance of {@link ResourceNotFoundException} from {@link RuntimeException}.
     * <p>
     * This test verifies that {@link ResourceNotFoundException} is a subclass of {@link RuntimeException},
     * ensuring it behaves as an unchecked exception.
     * </p>
     *
     * <p>
     * <strong>Error Conditions / Acceptable Values:</strong>
     * <ul>
     *   <li>The exception should inherit from {@link RuntimeException} to be recognized as an unchecked exception.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> The exception instance is an instance of {@link RuntimeException}.</li>
     *   <li><strong>Fail:</strong> If the exception instance is not recognized as an instance of {@link RuntimeException}.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test ResourceNotFoundException inheritance from RuntimeException")
    void testExceptionInheritance() {
        // Arrange: Create a new instance of ResourceNotFoundException with a custom message
        ResourceNotFoundException exception = new ResourceNotFoundException("Not found");

        // Act & Assert: Verify that the exception is an instance of RuntimeException
        assertTrue(exception instanceof RuntimeException, "ResourceNotFoundException should inherit from RuntimeException");
    }
}
