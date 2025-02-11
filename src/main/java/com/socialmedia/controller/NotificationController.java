package com.socialmedia.controller;


import com.socialmedia.dto.NotificationDTO;
import com.socialmedia.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * REST controller for managing notifications in the Social Media Application.
 *
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: Cross-references {@link NotificationService} -> {@code NotificationRepository}.</li>
 *   <li>Functions: Consolidated endpoints with doc about constraints (user must exist) and pass/fail conditions.</li>
 *   <li>Comments: Parameter constraints for userId, how read status is handled, etc.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {


    @Autowired
    private NotificationService notificationService;


    /**
     * Creates a new notification.
     *
     * <p><strong>Pass/Fail Condition:</strong>  
     * Pass: If userId is valid and NotificationDTO has valid data.  
     * Fail: If user not found (ResourceNotFoundException), or invalid input.
     * </p>
     *
     * @param notificationDTO The data transfer object containing notification details.
     * @return Created NotificationDTO (HTTP 201).
     */
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        NotificationDTO createdNotification = notificationService.createNotification(notificationDTO);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }


    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userId The ID of the user for whom notifications are to be retrieved.
     * @return A list of NotificationDTO with HTTP 200 status.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }


    /**
     * Updates an existing notification.
     *
     * <p><strong>Error Conditions:</strong>  
     * - If the notification with specified ID does not exist, ResourceNotFoundException is thrown.
     * </p>
     *
     * @param id The ID of the notification to be updated.
     * @param notificationDTO The data transfer object containing updated notification details.
     * @return Updated NotificationDTO (HTTP 200).
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id,
                                                              @RequestBody NotificationDTO notificationDTO) {
        NotificationDTO updatedNotification = notificationService.updateNotification(id, notificationDTO);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }


    /**
     * Deletes a notification.
     *
     * <p><strong>Pass/Fail Condition:</strong>  
     * Pass: If ID exists, notification removed.  
     * Fail: If ID not found, throws ResourceNotFoundException.
     * </p>
     *
     * @param id The ID of the notification to be deleted.
     * @return HTTP 204 No Content on success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
