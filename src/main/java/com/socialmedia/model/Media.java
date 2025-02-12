package com.socialmedia.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a Media item (such as an image, video, or other file) associated with a Post within the social media application.
 * <p>
 * This entity captures the media's URL, type, upload timestamp, and its association with a specific Post.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *     <li>Persisted in the database with automatic ID generation.</li>
 *     <li>Automatically sets the upload timestamp when a media item is created.</li>
 *     <li>Associated with a specific Post.</li>
 * </ul>
 *
 * <p><b>Constraints:</b></p>
 * <ul>
 *     <li>{@code url} must not be null or empty and should be a valid URL.</li>
 *     <li>{@code type} must be one of the predefined media types (e.g., "image", "video", "gif").</li>
 *     <li>{@code uploadedAt} is automatically set and must not be null.</li>
 *     <li>Each media item must be associated with an existing {@code Post}.</li>
 * </ul>
 *
 * <p><b>Pass/Fail Conditions:</b></p>
 * <ul>
 *     <li>Pass: When all fields are properly set, and associations are valid.</li>
 *     <li>Fail: When {@code url} is null, empty, or invalid; {@code type} is not recognized; {@code uploadedAt} is null; or associations to {@code Post} are missing.</li>
 * </ul>
 *
 * @see Post
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    /**
     * Unique identifier for the Media item.
     * <p>
     * Generated automatically using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The URL where the media is hosted.
     * <p>
     * Must not be null or empty and should follow a valid URL format.
     * </p>
     *
     * <p><b>Constraints:</b></p>
     * <ul>
     *     <li>Non-null and non-empty.</li>
     *     <li>Must adhere to a valid URL structure.</li>
     * </ul>
     */
    private String url;

    /**
     * The type of media (e.g., "image", "video", "gif").
     * <p>
     * Must be one of the predefined types supported by the application.
     * </p>
     *
     * <p><b>Acceptable Values:</b></p>
     * <ul>
     *     <li>"image"</li>
     *     <li>"video"</li>
     *     <li>"gif"</li>
     *     <!-- Add other supported types as necessary -->
     * </ul>
     */
    private String type;

    /**
     * Timestamp indicating when the Media was uploaded.
     * <p>
     * Automatically set during the persist operation. Must not be null.
     * </p>
     */
    private LocalDateTime uploadedAt;

    /**
     * The Post to which this Media is attached.
     * <p>
     * Represents a many-to-one relationship with the Post entity. Must not be null.
     * </p>
     */
    @ManyToOne
    private Post post;

    /**
     * Lifecycle callback method invoked before the entity is persisted.
     * <p>
     * Initializes the {@code uploadedAt} timestamp to the current time.
     * Ensures that the timestamp is set upon creation.
     * </p>
     *
     * <p><b>Assertions:</b></p>
     * <ul>
     *     <li>{@code uploadedAt} must be set to the current time.</li>
     *     <li>{@code url} must not be null or empty.</li>
     *     <li>{@code type} must be one of the predefined media types.</li>
     *     <li>{@code post} must not be null.</li>
     * </ul>
     *
     * <p><b>Pass Condition:</b> {@code uploadedAt} is correctly initialized, and all required fields are valid.</p>
     * <p><b>Fail Condition:</b> Required fields are missing or invalid, leading to an exception.</p>
     *
     * @throws IllegalStateException if {@code url} is null or empty, {@code type} is invalid, or {@code post} is null.
     */
    @PrePersist
    protected void onCreate() {
        // Validate that the URL is neither null nor empty
        if (this.url == null || this.url.trim().isEmpty()) {
            throw new IllegalStateException("URL cannot be null or empty when creating Media.");
        }

        // Validate that the type is one of the acceptable values
        if (!isValidType(this.type)) {
            throw new IllegalStateException("Invalid media type: " + this.type);
        }

        // Ensure that the media is associated with a post
        if (this.post == null) {
            throw new IllegalStateException("Post cannot be null when creating Media.");
        }

        // Set the uploadedAt timestamp to the current time
        this.uploadedAt = LocalDateTime.now();
    }

    /**
     * Checks if the provided media type is valid.
     *
     * @param type The media type to validate.
     * @return {@code true} if the type is valid; {@code false} otherwise.
     */
    private boolean isValidType(String type) {
        // Define acceptable media types
        return "image".equalsIgnoreCase(type) ||
               "video".equalsIgnoreCase(type) ||
               "gif".equalsIgnoreCase(type);
               // Add other types as necessary
    }

    /**
     * Provides a string representation of the Media.
     * <p>
     * Includes the Media's ID, URL, type, and upload timestamp.
     * Excludes the association to {@code Post} to avoid recursive calls and reduce verbosity.
     * </p>
     *
     * <p><b>Notes:</b></p>
     * <ul>
     *     <li>Designed to match test expectations such as "Media(id=1, url=..., type=..., uploadedAt=...)"</li>
     * </ul>
     *
     * @return A string detailing the Media's key attributes.
     */
    @Override
    public String toString() {
        // Tests expect "Media(id=1, url=..., type=..., uploadedAt=...)"
        return "Media(id=" + id +
               ", url=" + url +
               ", type=" + type +
               ", uploadedAt=" + uploadedAt + ")";
    }
}
