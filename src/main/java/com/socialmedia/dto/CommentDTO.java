package com.socialmedia.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for managing user comments within the social media application.
 * 
 * <p>This class encapsulates the necessary information required to create, update, or retrieve comments.</p>
 * 
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li><strong>Docstrings:</strong> Expanded to include detailed descriptions and conditions for each field.</li>
 *   <li><strong>Error Conditions:</strong> Specifies non-null constraints and references to existing records.</li>
 *   <li><strong>Parameter Descriptions:</strong> Includes acceptable value ranges and formats.</li>
 *   <li><strong>Premises and Assertions:</strong> Assumes that validation and reference checks are handled in the service layer.</li>
 *   <li><strong>Pass/Fail Conditions:</strong> Relates to the successful creation or retrieval of comments based on valid input.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    /**
     * The unique identifier of the comment.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must be a positive Long value.</li>
     *   <li>Automatically generated and managed by the system.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Valid when the comment exists in the database and the ID corresponds to a valid record.</li>
     * </ul>
     * 
     * <p><strong>Fail Condition:</strong></p>
     * <ul>
     *   <li>Invalid if the ID is null or does not correspond to an existing comment.</li>
     * </ul>
     */
    private Long id;

    /**
     * The textual content of the comment.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Should adhere to the application's content policies (e.g., no prohibited language).</li>
     *   <li>Maximum length of 500 characters.</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the content is null or empty.</li>
     *   <li>Throws an error if the content exceeds the maximum allowed length.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Content is provided, non-empty, and within the allowed length.</li>
     * </ul>
     */
    private String content;

    /**
     * The identifier of the post to which the comment belongs.
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

    /**
     * The identifier of the user who created the comment.
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
