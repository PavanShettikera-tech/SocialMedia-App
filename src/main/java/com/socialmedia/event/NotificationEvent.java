package com.socialmedia.event;


import com.socialmedia.dto.NotificationDTO;
import lombok.*;


/**
 * Event representing a notification in the Social Media Application.
 *
 * <p><strong>Feedback-Related Updates:</strong>
 * <ul>
 *   <li>Fewer smaller methods. We keep it as a simple data-holding event class.</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private NotificationDTO notification;
}
