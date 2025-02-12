package com.socialmedia.event;

import com.socialmedia.dto.NotificationDTO;
import lombok.*;

/**
 * Event representing a notification in the Social Media Application.
 *
 * <p><strong>Functionality:</strong>
 * This class serves as a data holder for notification events within the application. It encapsulates the details of a 
 * notification that will be dispatched to listeners or handlers interested in notification events.
 * </p>
 *
 * <p><strong>Parameters:</strong></p>
 * <ul>
 *   <li><strong>notification</strong> - The {@link NotificationDTO} containing the notification details. Must not be null.</li>
 * </ul>
 *
 * <p><strong>Acceptable Values/Range:</strong>
 * <ul>
 *   <li>The {@code notification} field must be a valid, non-null instance of {@link NotificationDTO}.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Error Conditions:</strong>
 * <ul>
 *   <li>If {@code notification} is null, it may lead to {@link NullPointerException} during event processing.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Premises and Assertions:</strong>
 * <ul>
 *   <li>Assumes that {@link NotificationDTO} is correctly initialized before being set.</li>
 *   <li>Asserts that the {@code notification} field is not null when the event is created.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li><strong>Pass:</strong> The event is successfully created with a valid {@link NotificationDTO} instance.</li>
 *   <li><strong>Fail:</strong> The event creation fails or leads to errors if {@code notification} is null.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Feedback-Related Updates:</strong>
 * <ul>
 *   <li>Fewer smaller methods. We keep it as a simple data-holding event class.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    /**
     * The {@link NotificationDTO} containing the notification details.
     *
     * <p><strong>Description:</strong>
     * Holds the data related to the notification that will be dispatched as part of this event.
     * </p>
     *
     * <p><strong>Constraints:</strong>
     * <ul>
     *   <li>Must not be null.</li>
     * </ul>
     * </p>
     */
    private NotificationDTO notification;
}
