package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a Comment made by a User on a Post within the social media application.
 * <p>
 * This entity captures the content of the comment, timestamps for creation and updates,
 * and associations to the related Post and User entities.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *     <li>Persisted in the database with automatic ID generation.</li>
 *     <li>Automatically sets creation and update timestamps.</li>
 *     <li>Associated with a specific Post and User.</li>
 * </ul>
 *
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>{@code content} must not be null or empty.</li>
 *     <li>{@code content} should adhere to application-specific length restrictions.</li>
 * </ul>
 *
 * <p><b>Pass/Fail Conditions:</b></p>
 * <ul>
 *     <li>Pass: When all fields meet their validation criteria.</li>
 *     <li>Fail: When {@code content} is null, empty, or exceeds length limits.</li>
 * </ul>
 *
 * @see Post
 * @see User
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    /**
     * Unique identifier for the Comment.
     * <p>
     * Generated automatically using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The textual content of the Comment.
     * <p>
     * Must not be null or empty. Should comply with the application's length constraints.
     * </p>
     */
    private String content;

    /**
     * Timestamp indicating when the Comment was created.
     * <p>
     * Automatically set during the persist operation.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last time the Comment was updated.
     * <p>
     * Automatically updated during the update operation.
     * </p>
     */
    private LocalDateTime updatedAt;

    /**
     * The Post to which this Comment belongs.
     * <p>
     * Represents a many-to-one relationship with the Post entity.
     * </p>
     */
    @ManyToOne
    private Post post;

    /**
     * The User who authored this Comment.
     * <p>
     * Represents a many-to-one relationship with the User entity.
     * </p>
     */
    @ManyToOne
    private User user;

    /**
     * Lifecycle callback method invoked before the entity is persisted.
     * <p>
     * Initializes the {@code createdAt} and {@code updatedAt} timestamps to the current time.
     * Ensures that both timestamps are identical at creation.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code createdAt} must be set to the current time.</li>
     *     <li>{@code updatedAt} must be set to the same value as {@code createdAt}.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> Timestamps are correctly initialized.</p>
     * <p><b>Fail Condition:</b> Timestamps are not set, leading to null values.</p>
     */
    @PrePersist
    public void onCreate() {
        // Use a single variable to ensure exact equality
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Lifecycle callback method invoked before the entity is updated.
     * <p>
     * Updates the {@code updatedAt} timestamp to the current time.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code updatedAt} must be updated to the current time.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> {@code updatedAt} reflects the latest update time.</p>
     * <p><b>Fail Condition:</b> {@code updatedAt} remains unchanged, not reflecting the update.</p>
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Provides a string representation of the Comment.
     * <p>
     * Includes the Comment's ID, content, creation timestamp, and last update timestamp.
     * </p>
     *
     * @return A string detailing the Comment's key attributes.
     */
    @Override
    public String toString() {
        return "Comment(id=" + id +
               ", content=" + content +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt + ")";
    }
}
