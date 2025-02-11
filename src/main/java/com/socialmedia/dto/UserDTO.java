package com.socialmedia.dto;


import lombok.*;


/**
 * Data Transfer Object for managing users.
 *
 * <p><strong>Constraints:</strong></p>
 * <ul>
 *   <li>email unique, not empty</li>
 *   <li>password not empty (more complex checks can be added in Service)</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    private Long id;
    
    /**
     * Typically the username or full name of the user (manager feedback: "More detail").
     */
    private String username;


    /**
     * The password for the user account (must be hashed/stored securely).
     */
    private String password;


    /**
     * The email address of the user (unique in system).
     */
    private String email;
}
