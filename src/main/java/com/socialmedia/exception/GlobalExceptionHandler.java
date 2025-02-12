package com.socialmedia.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global exception handler for the Social Media Application.
 *
 * <p><strong>Functionality:</strong>
 * This class serves as a centralized exception handling mechanism across the entire application. It intercepts
 * specific exceptions thrown by controllers and translates them into structured {@link ErrorResponse} objects
 * that are returned to the client. This ensures consistent error reporting and simplifies error management.
 * </p>
 *
 * <p><strong>Parameters:</strong>
 * This class does not have any fields or parameters of its own. Instead, it defines handler methods for various
 * exception types.
 * </p>
 *
 * <p><strong>Acceptable Values/Range:</strong>
 * <ul>
 *   <li>Handles specific exceptions such as {@link ResourceNotFoundException}, {@link InvalidInputException},
 *       and {@link UnauthorizedException}, as well as any generic {@link Exception} not specifically handled.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Error Conditions:</strong>
 * <ul>
 *   <li>If an exception is thrown within the application, the corresponding handler method processes it and
 *       constructs an appropriate {@link ErrorResponse}.</li>
 *   <li>If an exception is not recognized by any specific handler, the {@code handleGlobalException} method
 *       acts as a fallback to handle unforeseen exceptions.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Premises and Assertions:</strong>
 * <ul>
 *   <li>Assumes that all custom exceptions like {@link ResourceNotFoundException}, {@link InvalidInputException},
 *       and {@link UnauthorizedException} are properly defined and thrown within the application.</li>
 *   <li>Asserts that the {@link WebRequest} provided to each handler method contains relevant request details.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li><strong>Pass:</strong> Exceptions are successfully mapped to structured {@link ErrorResponse} objects
 *       and returned to the client with the appropriate HTTP status codes.</li>
 *   <li><strong>Fail:</strong> If an exception is not recognized by any specific handler, it falls back to
 *       {@code handleGlobalException}, which ensures that all exceptions are handled gracefully.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Feedback-Related Updates:</strong>
 * <ul>
 *   <li>Added detailed documentation for each handler method to clarify their roles in error reporting.</li>
 *   <li>Ensured comprehensive coverage of error conditions and pass/fail scenarios.</li>
 * </ul>
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ResourceNotFoundException} exceptions.
     *
     * <p><strong>Description:</strong>
     * This method intercepts {@link ResourceNotFoundException} instances thrown within the application,
     * constructs an {@link ErrorResponse} with a 404 status code, and returns it to the client.
     * </p>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>ex</strong> - The {@link ResourceNotFoundException} that was thrown. Must not be null.</li>
     *   <li><strong>request</strong> - The {@link WebRequest} during which the exception was thrown. Must not be null.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values/Range:</strong>
     * <ul>
     *   <li>The {@code ex} parameter must be an instance of {@link ResourceNotFoundException} with a valid message.</li>
     *   <li>The {@code request} parameter must provide contextual information about the web request.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Error Conditions:</strong>
     * <ul>
     *   <li>If {@code ex} is null, it may lead to a {@link NullPointerException} when accessing its message.</li>
     *   <li>If {@code request} is null, contextual information about the request may be unavailable.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Premises and Assertions:</strong>
     * <ul>
     *   <li>Assumes that {@link ResourceNotFoundException} is thrown appropriately when a requested resource is not found.</li>
     *   <li>Asserts that both {@code ex} and {@code request} are not null before processing.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> An {@link ErrorResponse} with a 404 status code is returned to the client.</li>
     *   <li><strong>Fail:</strong> If {@code ex} or {@code request} is null, the method may fail to construct a proper response.</li>
     * </ul>
     * </p>
     *
     * @param ex      The {@link ResourceNotFoundException} that was thrown. Must not be null.
     * @param request The {@link WebRequest} during which the exception was thrown. Must not be null.
     * @return A {@link ResponseEntity} containing the {@link ErrorResponse} with a 404 status code.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link InvalidInputException} exceptions.
     *
     * <p><strong>Description:</strong>
     * This method intercepts {@link InvalidInputException} instances thrown within the application,
     * constructs an {@link ErrorResponse} with a 400 status code, and returns it to the client.
     * </p>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>ex</strong> - The {@link InvalidInputException} that was thrown. Must not be null.</li>
     *   <li><strong>request</strong> - The {@link WebRequest} during which the exception was thrown. Must not be null.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values/Range:</strong>
     * <ul>
     *   <li>The {@code ex} parameter must be an instance of {@link InvalidInputException} with a valid message.</li>
     *   <li>The {@code request} parameter must provide contextual information about the web request.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Error Conditions:</strong>
     * <ul>
     *   <li>If {@code ex} is null, it may lead to a {@link NullPointerException} when accessing its message.</li>
     *   <li>If {@code request} is null, contextual information about the request may be unavailable.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Premises and Assertions:</strong>
     * <ul>
     *   <li>Assumes that {@link InvalidInputException} is thrown appropriately when input validation fails.</li>
     *   <li>Asserts that both {@code ex} and {@code request} are not null before processing.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> An {@link ErrorResponse} with a 400 status code is returned to the client.</li>
     *   <li><strong>Fail:</strong> If {@code ex} or {@code request} is null, the method may fail to construct a proper response.</li>
     * </ul>
     * </p>
     *
     * @param ex      The {@link InvalidInputException} that was thrown. Must not be null.
     * @param request The {@link WebRequest} during which the exception was thrown. Must not be null.
     * @return A {@link ResponseEntity} containing the {@link ErrorResponse} with a 400 status code.
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(
            InvalidInputException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link UnauthorizedException} exceptions.
     *
     * <p><strong>Description:</strong>
     * This method intercepts {@link UnauthorizedException} instances thrown within the application,
     * constructs an {@link ErrorResponse} with a 401 status code, and returns it to the client.
     * </p>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>ex</strong> - The {@link UnauthorizedException} that was thrown. Must not be null.</li>
     *   <li><strong>request</strong> - The {@link WebRequest} during which the exception was thrown. Must not be null.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values/Range:</strong>
     * <ul>
     *   <li>The {@code ex} parameter must be an instance of {@link UnauthorizedException} with a valid message.</li>
     *   <li>The {@code request} parameter must provide contextual information about the web request.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Error Conditions:</strong>
     * <ul>
     *   <li>If {@code ex} is null, it may lead to a {@link NullPointerException} when accessing its message.</li>
     *   <li>If {@code request} is null, contextual information about the request may be unavailable.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Premises and Assertions:</strong>
     * <ul>
     *   <li>Assumes that {@link UnauthorizedException} is thrown appropriately when authentication fails.</li>
     *   <li>Asserts that both {@code ex} and {@code request} are not null before processing.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> An {@link ErrorResponse} with a 401 status code is returned to the client.</li>
     *   <li><strong>Fail:</strong> If {@code ex} or {@code request} is null, the method may fail to construct a proper response.</li>
     * </ul>
     * </p>
     *
     * @param ex      The {@link UnauthorizedException} that was thrown. Must not be null.
     * @param request The {@link WebRequest} during which the exception was thrown. Must not be null.
     * @return A {@link ResponseEntity} containing the {@link ErrorResponse} with a 401 status code.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles all other exceptions that are not specifically handled by other methods.
     *
     * <p><strong>Description:</strong>
     * This method acts as a global fallback for any exceptions that do not have a dedicated handler. It constructs
     * an {@link ErrorResponse} with a 500 status code, ensuring that all unanticipated errors are gracefully
     * communicated to the client.
     * </p>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>ex</strong> - The {@link Exception} that was thrown. Must not be null.</li>
     *   <li><strong>request</strong> - The {@link WebRequest} during which the exception was thrown. Must not be null.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values/Range:</strong>
     * <ul>
     *   <li>The {@code ex} parameter must be an instance of {@link Exception} or its subclasses.</li>
     *   <li>The {@code request} parameter must provide contextual information about the web request.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Error Conditions:</strong>
     * <ul>
     *   <li>If {@code ex} is null, it may lead to a {@link NullPointerException} when accessing its message.</li>
     *   <li>If {@code request} is null, contextual information about the request may be unavailable.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Premises and Assertions:</strong>
     * <ul>
     *   <li>Assumes that any unhandled exceptions will propagate to this global handler.</li>
     *   <li>Asserts that both {@code ex} and {@code request} are not null before processing.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> An {@link ErrorResponse} with a 500 status code is returned to the client.</li>
     *   <li><strong>Fail:</strong> If {@code ex} or {@code request} is null, the method may fail to construct a proper response.</li>
     * </ul>
     * </p>
     *
     * @param ex      The {@link Exception} that was thrown. Must not be null.
     * @param request The {@link WebRequest} during which the exception was thrown. Must not be null.
     * @return A {@link ResponseEntity} containing the {@link ErrorResponse} with a 500 status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
