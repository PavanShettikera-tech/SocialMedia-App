package com.socialmedia.dto;

import lombok.*;

/**
 * Data Transfer Object (DTO) for managing notifications sent to users within the social media application.
 * 
 * <p>This class encapsulates the necessary information required to create, update, retrieve, or delete notifications.</p>
 * 
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li><strong>Docstrings:</strong> Expanded to include detailed descriptions and conditions for each field.</li>
 *   <li><strong>Error Conditions:</strong> Specifies non-null constraints and validation requirements.</li>
 *   <li><strong>Parameter Descriptions:</strong> Includes acceptable value ranges and formats.</li>
 *   <li><strong>Premises and Assertions:</strong> Assumes that validation and user existence checks are handled in the service layer.</li>
 *   <li><strong>Pass/Fail Conditions:</strong> Relates to the successful creation or retrieval of notifications based on valid input.</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    /**
     * The unique identifier of the notification.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must be a positive Long value.</li>
     *   <li>Automatically generated and managed by the system.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Valid when the notification exists in the database and the ID corresponds to a valid record.</li>
     * </ul>
     * 
     * <p><strong>Fail Condition:</strong></p>
     * <ul>
     *   <li>Invalid if the ID is null or does not correspond to an existing notification record.</li>
     * </ul>
     */
    private Long id;

    /**
     * The content of the notification message.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null or empty.</li>
     *   <li>Should adhere to the application's content policies (e.g., no prohibited language).</li>
     *   <li>Maximum length of 1000 characters.</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the message is null or empty.</li>
     *   <li>Throws an error if the message exceeds the maximum allowed length.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>Message is provided, non-empty, and within the allowed length.</li>
     * </ul>
     */
    private String message;

    /**
     * The identifier of the user who will receive the notification.
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

    /**
     * Indicates whether the notification has been read by the user.
     * <p><strong>Constraints:</strong></p>
     * <ul>
     *   <li>Must not be null.</li>
     * </ul>
     * 
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Throws an error if the read status is null.</li>
     * </ul>
     * 
     * <p><strong>Pass Condition:</strong></p>
     * <ul>
     *   <li>read is provided as either true (read) or false (unread).</li>
     * </ul>
     */
    private Boolean read;
}
