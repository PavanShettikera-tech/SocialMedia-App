package com.socialmedia.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link ResourceNotFoundException}.
 *
 * <p><strong>Premise:</strong>
 * Ensures that constructing and throwing this exception with a message works as intended.
 *
 * <p><strong>Error Conditions / Acceptable Values:</strong></p>
 * <ul>
 *   <li>Message can be any string or even null. Here we test typical usage with a valid message.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li>Pass: Exception message is stored and retrievable.</li>
 *   <li>Fail: If the exception message is incorrect or the type is not recognized as a RuntimeException.</li>
 * </ul>
 */
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Test ResourceNotFoundException with custom message")
    void testExceptionMessage() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Test ResourceNotFoundException inheritance from RuntimeException")
    void testExceptionInheritance() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Not found");
        assertTrue(exception instanceof RuntimeException);
    }
}
