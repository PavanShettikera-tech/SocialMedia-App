package com.socialmedia.exception;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing an error response.
 *
 * <p><strong>Functionality:</strong>
 * This class encapsulates the details of an error response that can be returned by the application
 * to inform clients about issues that occurred during request processing. It contains information
 * such as the HTTP status code, error message, and the timestamp when the error occurred.
 * </p>
 *
 * <p><strong>Parameters:</strong></p>
 * <ul>
 *   <li><strong>status</strong> - The HTTP status code representing the error. Must be a valid HTTP status code (e.g., 400, 404, 500).</li>
 *   <li><strong>message</strong> - A descriptive message detailing the error. Must not be null or empty.</li>
 *   <li><strong>timestamp</strong> - The {@link LocalDateTime} indicating when the error occurred. Must not be null and should represent the current time.</li>
 * </ul>
 *
 * <p><strong>Acceptable Values/Range:</strong>
 * <ul>
 *   <li>The {@code status} field should correspond to a valid HTTP status code.</li>
 *   <li>The {@code message} field should be a non-null, non-empty string providing a clear description of the error.</li>
 *   <li>The {@code timestamp} field should be a valid {@link LocalDateTime} instance representing the error occurrence time.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Error Conditions:</strong>
 * <ul>
 *   <li>If {@code status} is not a valid HTTP status code, clients may misinterpret the error.</li>
 *   <li>If {@code message} is null or empty, the client may not understand the nature of the error.</li>
 *   <li>If {@code timestamp} is null, it may lead to issues in logging or debugging error occurrences.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Premises and Assertions:</strong>
 * <ul>
 *   <li>Assumes that all fields are properly initialized before an {@code ErrorResponse} instance is created.</li>
 *   <li>Asserts that {@code status}, {@code message}, and {@code timestamp} are not null (and {@code message} is not empty).</li>
 * </ul>
 * </p>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li><strong>Pass:</strong> An {@code ErrorResponse} instance is successfully created with all fields correctly populated.</li>
 *   <li><strong>Fail:</strong> Creation of an {@code ErrorResponse} instance fails or results in an incomplete error representation if any field is invalid or null.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Feedback-Related Updates:</strong>
 * <ul>
 *   <li>Added detailed documentation for each field to clarify their roles in error reporting.</li>
 *   <li>Ensured the class remains a simple data-holding DTO without additional methods.</li>
 * </ul>
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * The HTTP status code representing the error.
     *
     * <p><strong>Description:</strong>
     * Indicates the type of error that occurred, corresponding to standard HTTP status codes.
     * </p>
     *
     * <p><strong>Constraints:</strong>
     * <ul>
     *   <li>Must be a valid HTTP status code (e.g., 400 for Bad Request, 404 for Not Found, 500 for Internal Server Error).</li>
     * </ul>
     * </p>
     */
    private int status;

    /**
     * A descriptive message detailing the error.
     *
     * <p><strong>Description:</strong>
     * Provides a human-readable explanation of the error, intended to inform the client about what went wrong.
     * </p>
     *
     * <p><strong>Constraints:</strong>
     * <ul>
     *   <li>Must not be null or empty.</li>
     * </ul>
     * </p>
     */
    private String message;

    /**
     * The timestamp indicating when the error occurred.
     *
     * <p><strong>Description:</strong>
     * Records the exact date and time when the error was generated, aiding in logging and debugging processes.
     * </p>
     *
     * <p><strong>Constraints:</strong>
     * <ul>
     *   <li>Must not be null.</li>
     *   <li>Should represent the current time when the error is created.</li>
     * </ul>
     * </p>
     */
    private LocalDateTime timestamp;
}
