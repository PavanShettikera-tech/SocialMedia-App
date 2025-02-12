package com.socialmedia.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for managing media files attached to posts within the social media application.
 * 
 * <p>This class encapsulates the necessary information required to create, update, retrieve, or delete media files associated with posts.</p>
 * 
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li><strong>Docstrings:</strong> Expanded to include detailed descriptions and conditions for each field.</li>
 *   <li><strong>Error Conditions:</strong> Specifies non-null constraints and validation requirements.</li>
 *   <li><strong>Parameter Descriptions:</strong> Includes acceptable value ranges and formats.</li>
 *   <li><strong>Premises and Assertions:</strong> Assumes that validation and URL verification are handled in the service layer.</li>
 *   <li><strong>Pass/Fail Conditions:</strong> Relates to the successful creation or retrieval of media based on valid input.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {

    /**
     * The unique identifier of the media file.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must be a positive Long value.</li>
     *   <li>Automatically generated and managed by the system.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Valid when the media exists in the database and the ID corresponds to a valid record.</li>
     * </ul>
     * 
     * <p><strong>Fail Condition:</strong></p>
     * <ul>
     *   <li>Invalid if the ID is null or does not correspond to an existing media record.</li>
     * </ul>
     */
    private Long id;

    /**
     * The URL location of the media file.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Must be a valid URL format (e.g., https://example.com/media/image.png).</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the URL is null or empty.</li>
     *   <li>Throws an error if the URL does not conform to a valid URL format.</li>
     *   <li>Throws an error if the URL points to an inaccessible or non-existent location.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>URL is provided, non-empty, and follows a valid URL format.</li>
     *   <li>URL points to an accessible and existing media resource.</li>
     * </ul>
     */
    private String url;

    /**
     * The type of media file (e.g., image, video, audio).
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Must be one of the predefined media types supported by the application (e.g., "image", "video", "audio").</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the type is null or empty.</li>
     *   <li>Throws an error if the type is not among the supported media types.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Type is provided, non-empty, and matches one of the supported media types.</li>
     * </ul>
     */
    private String type;

    /**
     * The identifier of the post to which the media file is attached.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null.</li>
     *   <li>Must reference an existing post ID in the system.</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the postId is null.</li>
     *   <li>Throws an error if the postId does not correspond to an existing post.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>postId is provided and references an existing post in the database.</li>
     * </ul>
     */
    private Long postId;
}
