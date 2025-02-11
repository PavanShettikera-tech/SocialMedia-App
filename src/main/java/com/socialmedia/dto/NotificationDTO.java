package com.socialmedia.dto;

import lombok.*;

/**
 * Data Transfer Object for managing notifications sent to users.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Long id;
    private String message;
    private Long userId;
    private Boolean read;
}
