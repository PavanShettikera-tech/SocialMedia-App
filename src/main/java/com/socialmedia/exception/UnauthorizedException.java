package com.socialmedia.exception;


/**
 * Exception thrown when a user is unauthorized to perform a certain action.
 *
 * <p>Used if the user lacks the necessary role or authentication.</p>
 */
public class UnauthorizedException extends RuntimeException {


    public UnauthorizedException(String message) {
        super(message);
    }
}
