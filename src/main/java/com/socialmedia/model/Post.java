package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Post within the Social Media Application.
 * <p>
 * This entity captures the post's title, content, timestamps for creation and updates,
 * associations to the authoring User, and collections of related Comments, Likes, and Media.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *     <li>Persisted in the database with automatic ID generation.</li>
 *     <li>Automatically sets creation and update timestamps.</li>
 *     <li>Associated with an authoring User.</li>
 *     <li>Manages collections of Comments, Likes, and Media related to the Post.</li>
 * </ul>
 *
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>{@code title} must not be null or empty and should adhere to length restrictions.</li>
 *     <li>{@code content} must not be null or empty and is stored as TEXT in the database.</li>
 *     <li>{@code user} (author) must be associated and not null.</li>
 *     <li>{@code createdAt} and {@code updatedAt} are automatically managed and must not be null.</li>
 *     <li>Collections {@code comments}, {@code likes}, and {@code media} should not be null.</li>
 * </ul>
 *
 * <p><b>Pass/Fail Conditions:</b></p>
 * <ul>
 *     <li>Pass: When all fields are properly set, constraints are met, and associations are valid.</li>
 *     <li>Fail: When {@code title} or {@code content} is null or empty, {@code user} is null, or timestamps are not set.</li>
 * </ul>
 *
 * @see User
 * @see Comment
 * @see Like
 * @see Media
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"comments", "likes", "media"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    /**
     * Unique identifier for the Post.
     * <p>
     * Generated automatically using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The title of the Post.
     * <p>
     * Must not be null or empty. Should comply with application-specific length restrictions.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null and non-empty.</li>
     *     <li>Maximum length as defined by application requirements.</li>
     * </ul>
     */
    private String title;

    /**
     * The textual content of the Post.
     * <p>
     * Stored as TEXT in the database to accommodate lengthy content.
     * Must not be null or empty.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null and non-empty.</li>
     *     <li>Should provide meaningful and relevant information.</li>
     * </ul>
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * Timestamp indicating when the Post was created.
     * <p>
     * Automatically set during the persist operation. Must not be null.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last time the Post was updated.
     * <p>
     * Automatically updated during the update operation. Must not be null.
     * </p>
     */
    private LocalDateTime updatedAt;

    /**
     * The User who authored the Post.
     * <p>
     * Represents a many-to-one relationship with the User entity. Must not be null.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Collection of Comments associated with the Post.
     * <p>
     * Represents a one-to-many relationship with the Comment entity.
     * Cascades all operations and removes orphaned comments.
     * </p>
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * Collection of Likes associated with the Post.
     * <p>
     * Represents a one-to-many relationship with the Like entity.
     * Cascades all operations and removes orphaned likes.
     * </p>
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    /**
     * Collection of Media items associated with the Post.
     * <p>
     * Represents a one-to-many relationship with the Media entity.
     * Cascades all operations and removes orphaned media items.
     * </p>
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> media = new ArrayList<>();

    /**
     * Lifecycle callback method invoked before the entity is persisted.
     * <p>
     * Initializes the {@code createdAt} and {@code updatedAt} timestamps to the current time.
     * Validates essential fields to ensure data integrity.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code title} must not be null or empty.</li>
     *     <li>{@code content} must not be null or empty.</li>
     *     <li>{@code user} must not be null.</li>
     *     <li>{@code createdAt} and {@code updatedAt} must be set to the current time.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> {@code createdAt} and {@code updatedAt} are correctly initialized, and all required fields are valid.</p>
     * <p><b>Fail Condition:</b> Required fields are missing or invalid, leading to an exception.</p>
     *
     * @throws IllegalStateException if {@code title}, {@code content}, or {@code user} is null or empty.
     */
    @PrePersist
    protected void onCreate() {
        // Validate that the title is neither null nor empty
        if (this.title == null || this.title.trim().isEmpty()) {
            throw new IllegalStateException("Post title cannot be null or empty.");
        }

        // Validate that the content is neither null nor empty
        if (this.content == null || this.content.trim().isEmpty()) {
            throw new IllegalStateException("Post content cannot be null or empty.");
        }

        // Ensure that the post is associated with a user
        if (this.user == null) {
            throw new IllegalStateException("User cannot be null when creating a Post.");
        }

        // Set the createdAt and updatedAt timestamps to the current time
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Lifecycle callback method invoked before the entity is updated.
     * <p>
     * Updates the {@code updatedAt} timestamp to the current time.
     * Validates essential fields to ensure data integrity.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code title} must not be null or empty.</li>
     *     <li>{@code content} must not be null or empty.</li>
     *     <li>{@code user} must not be null.</li>
     *     <li>{@code updatedAt} must be set to the current time.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> {@code updatedAt} is correctly updated, and all required fields are valid.</p>
     * <p><b>Fail Condition:</b> Required fields are missing or invalid, leading to an exception.</p>
     *
     * @throws IllegalStateException if {@code title}, {@code content}, or {@code user} is null or empty.
     */
    @PreUpdate
    protected void onUpdate() {
        // Validate that the title is neither null nor empty
        if (this.title == null || this.title.trim().isEmpty()) {
            throw new IllegalStateException("Post title cannot be null or empty.");
        }

        // Validate that the content is neither null nor empty
        if (this.content == null || this.content.trim().isEmpty()) {
            throw new IllegalStateException("Post content cannot be null or empty.");
        }

        // Ensure that the post is associated with a user
        if (this.user == null) {
            throw new IllegalStateException("User cannot be null when updating a Post.");
        }

        // Update the updatedAt timestamp to the current time
        this.updatedAt = LocalDateTime.now();
    }
}
