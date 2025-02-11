package com.socialmedia.dto;


import lombok.*;


/**
 * Data Transfer Object for handling user authentication (login).
 *
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>Comments: Acceptable values for email/password and pass/fail conditions not strictly enforced here but used in login flow.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {


    /**
     * The email address of the user (must not be empty).
     */
    private String email;


    /**
     * The password of the user (must not be empty).
     */
    private String password;
}
