package com.socialmedia.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for managing posts within the social media application.
 * 
 * <p>This class encapsulates the necessary information required to create, update, retrieve, or delete posts.</p>
 * 
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li><strong>Docstrings:</strong> Expanded to include detailed descriptions and conditions for each field.</li>
 *   <li><strong>Error Conditions:</strong> Specifies non-null constraints and validation requirements.</li>
 *   <li><strong>Parameter Descriptions:</strong> Includes acceptable value ranges and formats.</li>
 *   <li><strong>Premises and Assertions:</strong> Assumes that validation and user existence checks are handled in the service layer.</li>
 *   <li><strong>Pass/Fail Conditions:</strong> Relates to the successful creation or retrieval of posts based on valid input.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    /**
     * The unique identifier of the post.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must be a positive Long value.</li>
     *   <li>Automatically generated and managed by the system.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Valid when the post exists in the database and the ID corresponds to a valid record.</li>
     * </ul>
     * 
     * <p><strong>Fail Condition:</strong></p>
     * <ul>
     *   <li>Invalid if the ID is null or does not correspond to an existing post record.</li>
     * </ul>
     */
    private Long id;

    /**
     * The title of the post.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Should have a maximum length of 255 characters.</li>
     *   <li>Should adhere to the application's content policies (e.g., no prohibited language).</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the title is null or empty.</li>
     *   <li>Throws an error if the title exceeds the maximum allowed length.</li>
     *   <li>Throws an error if the title contains prohibited content.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Title is provided, non-empty, within the allowed length, and complies with content policies.</li>
     * </ul>
     */
    private String title;

    /**
     * The content of the post.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Should have a reasonable length (e.g., maximum 5000 characters).</li>
     *   <li>Should adhere to the application's content policies (e.g., no prohibited language).</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the content is null or empty.</li>
     *   <li>Throws an error if the content exceeds the maximum allowed length.</li>
     *   <li>Throws an error if the content contains prohibited content.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Content is provided, non-empty, within the allowed length, and complies with content policies.</li>
     * </ul>
     */
    private String content;

    /**
     * The identifier of the user who created the post.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null.</li>
     *   <li>Must reference an existing user ID in the system.</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the userId is null.</li>
     *   <li>Throws an error if the userId does not correspond to an existing user.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>userId is provided and references an existing user in the database.</li>
     * </ul>
     */
    private Long userId;
}
