package com.socialmedia.exception;


import lombok.*;
import java.time.LocalDateTime;


/**
 * Data Transfer Object representing an error response.
 *
 * <p><strong>Feedback-Related Updates:</strong>
 * <ul>
 *   <li>Detailed doc on fields used for error reporting.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {


    private int status;
    private String message;
    private LocalDateTime timestamp;
}
