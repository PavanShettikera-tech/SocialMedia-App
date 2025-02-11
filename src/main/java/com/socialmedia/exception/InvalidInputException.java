package com.socialmedia.exception;


/**
 * Exception thrown when invalid input is provided by the user.
 *
 * <p><strong>Feedback-Related Updates:</strong>
 * <ul>
 *   <li>Expanded doc on typical usage scenarios.</li>
 * </ul>
 */
public class InvalidInputException extends RuntimeException {


    public InvalidInputException(String message) {
        super(message);
    }
}
