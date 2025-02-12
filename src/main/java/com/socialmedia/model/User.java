package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User within the Social Media Application.
 * <p>
 * This entity captures the user's personal information, authentication credentials,
 * role within the application, and associations to various content such as Posts,
 * Comments, Likes, and Notifications.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *     <li>Persisted in the database with automatic ID generation.</li>
 *     <li>Ensures unique email addresses for each user.</li>
 *     <li>Manages user roles to control access and permissions.</li>
 *     <li>Associates with collections of Posts, Comments, Likes, and Notifications authored by the user.</li>
 * </ul>
 *
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>{@code name} must not be null or empty.</li>
 *     <li>{@code email} must be unique, not null, and follow a valid email format.</li>
 *     <li>{@code password} must not be null or empty and should adhere to security standards.</li>
 *     <li>{@code role} must be one of the predefined roles (e.g., "USER", "ADMIN").</li>
 *     <li>Collections {@code posts}, {@code comments}, {@code likes}, and {@code notifications} should not be null.</li>
 * </ul>
 *
 * <p><b>Pass/Fail Conditions:</b></p>
 * <ul>
 *     <li>Pass: When all fields are properly set, constraints are met, and associations are valid.</li>
 *     <li>Fail: When {@code name}, {@code email}, or {@code password} is null or empty; {@code email} is not unique or invalid; {@code role} is undefined; or associations are missing.</li>
 * </ul>
 *
 * @see Post
 * @see Comment
 * @see Like
 * @see Notification
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    /**
     * Unique identifier for the User.
     * <p>
     * Generated automatically using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the User.
     * <p>
     * Typically represents the username or the actual name of the user.
     * Must not be null or empty.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null and non-empty.</li>
     *     <li>Should comply with application-specific length restrictions.</li>
     * </ul>
     */
    private String name; // Typically the username or actual name

    /**
     * The email address of the User.
     * <p>
     * Must be unique across all users, not null, and follow a valid email format.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Unique: No two users can have the same email.</li>
     *     <li>Non-null and non-empty.</li>
     *     <li>Must adhere to standard email formatting.</li>
     * </ul>
     *
     * <p><b>Pass/Fail Conditions:</b></p>
     * <ul>
     *     <li>Pass: When the email is unique, properly formatted, and not null.</li>
     *     <li>Fail: When the email is duplicate, improperly formatted, or null.</li>
     * </ul>
     */
    @Column(unique = true)
    private String email;

    /**
     * The password for the User's account.
     * <p>
     * Must not be null or empty and should adhere to security standards such as minimum length and complexity.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null and non-empty.</li>
     *     <li>Should be stored in an encrypted or hashed format.</li>
     *     <li>Should meet security requirements (e.g., minimum length, inclusion of special characters).</li>
     * </ul>
     *
     * <p><b>Security Considerations:</b></p>
     * <ul>
     *     <li>Passwords should never be exposed in logs or to the client.</li>
     *     <li>Implement proper hashing algorithms (e.g., BCrypt) when storing passwords.</li>
     * </ul>
     */
    private String password;

    /**
     * The role assigned to the User.
     * <p>
     * Determines the level of access and permissions within the application.
     * Defaults to {@code "USER"} upon creation.
     * </p>
     *
     * <p><b>Acceptable Values:</b></p>
     * <ul>
     *     <li>"USER"</li>
     *     <li>"ADMIN"</li>
     *     <!-- Add other roles as necessary -->
     * </ul>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Must be one of the predefined roles.</li>
     * </ul>
     *
     * <p><b>Default Value:</b> "USER"</p>
     */
    private String role = "USER";

    /**
     * Collection of Posts authored by the User.
     * <p>
     * Represents a one-to-many relationship with the Post entity.
     * Cascades all operations and removes orphaned posts when the user is deleted.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null: Initialized as an empty list.</li>
     * </ul>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    /**
     * Collection of Comments made by the User.
     * <p>
     * Represents a one-to-many relationship with the Comment entity.
     * Cascades all operations and removes orphaned comments when the user is deleted.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null: Initialized as an empty list.</li>
     * </ul>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * Collection of Likes made by the User.
     * <p>
     * Represents a one-to-many relationship with the Like entity.
     * Cascades all operations and removes orphaned likes when the user is deleted.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null: Initialized as an empty list.</li>
     * </ul>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    /**
     * Collection of Notifications received by the User.
     * <p>
     * Represents a one-to-many relationship with the Notification entity.
     * Cascades all operations and removes orphaned notifications when the user is deleted.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null: Initialized as an empty list.</li>
     * </ul>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    /**
     * Lifecycle callback method invoked before the entity is persisted.
     * <p>
     * Validates essential fields to ensure data integrity before saving the User to the database.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code name} must not be null or empty.</li>
     *     <li>{@code email} must not be null, empty, and must follow a valid email format.</li>
     *     <li>{@code password} must not be null or empty.</li>
     *     <li>{@code role} must be one of the predefined roles.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> All required fields are valid and meet their constraints.</p>
     * <p><b>Fail Condition:</b> Any required field is missing or invalid, leading to an exception.</p>
     *
     * @throws IllegalStateException if {@code name}, {@code email}, or {@code password} is null or empty,
     *                               if {@code email} is not properly formatted, or if {@code role} is undefined.
     */
    @PrePersist
    protected void onCreate() {
        // Validate that the name is neither null nor empty
        if (this.name == null || this.name.trim().isEmpty()) {
            throw new IllegalStateException("User name cannot be null or empty.");
        }

        // Validate that the email is neither null nor empty and follows a valid format
        if (this.email == null || this.email.trim().isEmpty()) {
            throw new IllegalStateException("User email cannot be null or empty.");
        }
        if (!isValidEmail(this.email)) {
            throw new IllegalStateException("User email is not in a valid format.");
        }

        // Validate that the password is neither null nor empty
        if (this.password == null || this.password.trim().isEmpty()) {
            throw new IllegalStateException("User password cannot be null or empty.");
        }

        // Validate that the role is one of the predefined roles
        if (!isValidRole(this.role)) {
            throw new IllegalStateException("Invalid user role: " + this.role);
        }
    }

    /**
     * Checks if the provided email is in a valid format.
     *
     * @param email The email address to validate.
     * @return {@code true} if the email format is valid; {@code false} otherwise.
     */
    private boolean isValidEmail(String email) {
        // Simple regex for email validation. For more robust validation, consider using specialized libraries.
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    /**
     * Checks if the provided role is one of the predefined roles.
     *
     * @param role The role to validate.
     * @return {@code true} if the role is valid; {@code false} otherwise.
     */
    private boolean isValidRole(String role) {
        // Define acceptable roles
        return "USER".equalsIgnoreCase(role) ||
               "ADMIN".equalsIgnoreCase(role);
               // Add other roles as necessary
    }
}
