package com.socialmedia.exception;


/**
 * Exception thrown when a requested resource is not found.
 *
 * <p>
 * Typically used if a user, post, or comment ID is non-existent.
 * </p>
 */
public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String message) {
        super(message);
    }
}
