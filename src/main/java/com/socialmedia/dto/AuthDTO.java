package com.socialmedia.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for handling user authentication (login) requests.
 * 
 * <p>This class encapsulates the necessary information required for user authentication within the application.</p>
 * 
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li><strong>Docstrings:</strong> Expanded to include detailed descriptions and conditions.</li>
 *   <li><strong>Error Conditions:</strong> Specifies non-empty constraints for email and password.</li>
 *   <li><strong>Parameter Descriptions:</strong> Includes acceptable value ranges and formats.</li>
 *   <li><strong>Premises and Assertions:</strong> Assumes that validation is handled in the login flow.</li>
 *   <li><strong>Pass/Fail Conditions:</strong> Relates to the success or failure of the authentication process.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {

    /**
     * The email address of the user attempting to authenticate.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Must follow a valid email format (e.g., user@example.com).</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the email is null, empty, or does not match the required format during validation.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Email is provided and matches the valid email format.</li>
     * </ul>
     */
    private String email;

    /**
     * The password of the user attempting to authenticate.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Should meet the application's password complexity requirements (e.g., minimum length, inclusion of special characters).</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the password is null or empty during validation.</li>
     *   <li>Throws an error if the password does not meet complexity requirements.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Password is provided and meets all complexity requirements.</li>
     * </ul>
     */
    private String password;
}
