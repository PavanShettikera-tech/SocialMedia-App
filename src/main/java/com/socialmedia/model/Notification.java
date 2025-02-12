package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a Notification within the Social Media Application.
 * <p>
 * This entity captures the notification message, its creation timestamp, read status,
 * and association to the intended User.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *     <li>Persisted in the database with automatic ID generation.</li>
 *     <li>Automatically sets the creation timestamp when a notification is created.</li>
 *     <li>Indicates whether the notification has been read by the user.</li>
 *     <li>Associated with a specific User.</li>
 * </ul>
 *
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>{@code message} must not be null or empty.</li>
 *     <li>{@code user} must be associated and not null.</li>
 *     <li>{@code createdAt} is automatically set and must not be null.</li>
 *     <li>{@code read} is a primitive boolean indicating the read status, defaulting to {@code false}.</li>
 * </ul>
 *
 * <p><b>Pass/Fail Conditions:</b></p>
 * <ul>
 *     <li>Pass: When all fields are properly set, and associations are valid.</li>
 *     <li>Fail: When {@code message} is null or empty, {@code user} is null, or {@code createdAt} is not set.</li>
 * </ul>
 *
 * @see User
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    /**
     * Unique identifier for the Notification.
     * <p>
     * Generated automatically using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The content of the notification message.
     * <p>
     * Must not be null or empty. Should provide meaningful information to the user.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null and non-empty.</li>
     * </ul>
     */
    private String message;

    /**
     * Timestamp indicating when the Notification was created.
     * <p>
     * Automatically set during the persist operation. Must not be null.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Indicates whether the Notification has been read by the user.
     * <p>
     * Defaults to {@code false} upon creation.
     * </p>
     */
    private boolean read = false;

    /**
     * The User to whom this Notification is addressed.
     * <p>
     * Represents a many-to-one relationship with the User entity. Must not be null.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Lifecycle callback method invoked before the entity is persisted.
     * <p>
     * Initializes the {@code createdAt} timestamp to the current time and validates essential fields.
     * Ensures that the notification message and associated user are valid before persisting.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code message} must not be null or empty.</li>
     *     <li>{@code user} must not be null.</li>
     *     <li>{@code createdAt} must be set to the current time.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> {@code createdAt} is correctly initialized, and all required fields are valid.</p>
     * <p><b>Fail Condition:</b> Required fields are missing or invalid, leading to an exception.</p>
     *
     * @throws IllegalStateException if {@code message} is null or empty, or if {@code user} is null.
     */
    @PrePersist
    protected void onCreate() {
        // Validate that the message is neither null nor empty
        if (this.message == null || this.message.trim().isEmpty()) {
            throw new IllegalStateException("Notification message cannot be null or empty.");
        }

        // Ensure that the notification is associated with a user
        if (this.user == null) {
            throw new IllegalStateException("User cannot be null when creating a Notification.");
        }

        // Set the createdAt timestamp to the current time
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Provides a string representation of the Notification.
     * <p>
     * Includes the Notification's ID, message, creation timestamp, and read status.
     * Excludes the association to {@code User} to avoid recursive calls and reduce verbosity.
     * </p>
     *
     * @return A string detailing the Notification's key attributes.
     */
    @Override
    public String toString() {
        return "Notification(id=" + id +
               ", message=" + message +
               ", createdAt=" + createdAt +
               ", read=" + read + ")";
    }
}
