package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a Like made by a User on a Post within the social media application.
 * <p>
 * This entity captures the timestamp of when the like was made and maintains associations
 * to the related Post and User entities.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *     <li>Persisted in the database with automatic ID generation.</li>
 *     <li>Automatically sets the timestamp when a like is created.</li>
 *     <li>Associated with a specific Post and User.</li>
 * </ul>
 *
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>{@code likedAt} is automatically set and should not be null.</li>
 *     <li>Each like must be associated with an existing {@code Post} and {@code User}.</li>
 * </ul>
 *
 * <p><b>Pass/Fail Conditions:</b></p>
 * <ul>
 *     <li>Pass: When all fields are properly set and associations are valid.</li>
 *     <li>Fail: When {@code likedAt} is null or associations to {@code Post} or {@code User} are missing.</li>
 * </ul>
 *
 * @see Post
 * @see User
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {

    /**
     * Unique identifier for the Like.
     * <p>
     * Generated automatically using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Timestamp indicating when the Like was made.
     * <p>
     * Automatically set during the persist operation. Must not be null.
     * </p>
     */
    private LocalDateTime likedAt;

    /**
     * The Post that has been liked.
     * <p>
     * Represents a many-to-one relationship with the Post entity.
     * Must not be null.
     * </p>
     */
    @ManyToOne
    private Post post;

    /**
     * The User who made the Like.
     * <p>
     * Represents a many-to-one relationship with the User entity.
     * Must not be null.
     * </p>
     */
    @ManyToOne
    private User user;

    /**
     * Lifecycle callback method invoked before the entity is persisted.
     * <p>
     * Initializes the {@code likedAt} timestamp to the current time.
     * Ensures that the timestamp is set upon creation.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code likedAt} must be set to the current time.</li>
     *     <li>Neither {@code post} nor {@code user} should be null.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> {@code likedAt} is correctly initialized and associations are valid.</p>
     * <p><b>Fail Condition:</b> {@code likedAt} remains unset, or associations to {@code Post} or {@code User} are missing.</p>
     *
     * @throws IllegalStateException if {@code post} or {@code user} is null.
     */
    @PrePersist
    protected void onCreate() {
        // Ensure that the like is associated with a post and a user before setting the timestamp
        if (this.post == null) {
            throw new IllegalStateException("Post cannot be null when creating a Like.");
        }
        if (this.user == null) {
            throw new IllegalStateException("User cannot be null when creating a Like.");
        }
        // Set the likedAt timestamp to the current time
        this.likedAt = LocalDateTime.now();
    }

    /**
     * Provides a string representation of the Like.
     * <p>
     * Includes the Like's ID and the timestamp when it was made.
     * Excludes associations to {@code Post} and {@code User} to avoid recursive calls and reduce verbosity.
     * </p>
     *
     * <p><b>Notes:</b></p>
     * <ul>
     *     <li>Designed to match test expectations such as "Like(id=1, likedAt=null)".</li>
     * </ul>
     *
     * @return A string detailing the Like's key attributes.
     */
    @Override
    public String toString() {
        // Tests expect "Like(id=1, likedAt=null)"
        return "Like(id=" + id +
               ", likedAt=" + likedAt + ")";
    }
}
