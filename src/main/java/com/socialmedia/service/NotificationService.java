package com.socialmedia.service;

import com.socialmedia.dto.NotificationDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Notification;
import com.socialmedia.model.User;
import com.socialmedia.repository.NotificationRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to Notifications for users.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new notification for a specific user.
     *
     * @param dto The data transfer object containing notification details.
     *            - userId: ID of the user to receive the notification. Must be a positive Long.
     *            - message: The content of the notification. Must not be null or empty.
     *            - read: Indicates whether the notification has been read. Optional; defaults to false if null.
     * @return NotificationDTO The DTO representing the created Notification entity.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     *
     * <b>Pass Condition:</b> A Notification is successfully created and saved, returning the corresponding NotificationDTO.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the user is not found.
     */
    public NotificationDTO createNotification(NotificationDTO dto) {
        // Retrieve the user by ID; throw exception if not found
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getUserId()));

        // Create a new Notification entity and set its properties
        Notification notif = new Notification();
        notif.setMessage(dto.getMessage());
        notif.setUser(user);
        notif.setRead(dto.getRead() != null && dto.getRead());

        // Save the Notification entity to the repository
        Notification saved = notificationRepository.save(notif);

        // Convert the saved Notification entity to a NotificationDTO and return
        return mapToDTO(saved);
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userId The ID of the user whose notifications are to be retrieved. Must be a positive Long.
     * @return List&lt;NotificationDTO&gt; A list of NotificationDTOs associated with the specified user.
     *
     * <b>Pass Condition:</b> Returns a list of notifications for the user. If no notifications exist, returns an empty list.
     * <b>Fail Condition:</b> If the user ID does not exist, returns an empty list.
     */
    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        // Retrieve all Notification entities associated with the given user ID and map them to DTOs
        return notificationRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing notification's message and read status.
     *
     * @param id  The ID of the notification to be updated. Must be a positive Long.
     * @param dto The data transfer object containing updated notification details.
     *            - message: The new content of the notification. Must not be null or empty.
     *            - read: The updated read status of the notification. Optional.
     * @return NotificationDTO The DTO representing the updated Notification entity.
     * @throws ResourceNotFoundException If the notification with the given ID does not exist.
     *
     * <b>Pass Condition:</b> The specified Notification is successfully updated and saved, returning the corresponding NotificationDTO.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the notification is not found.
     */
    public NotificationDTO updateNotification(Long id, NotificationDTO dto) {
        // Retrieve the existing Notification entity by ID; throw exception if not found
        Notification existing = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));

        // Update the message of the Notification
        existing.setMessage(dto.getMessage());

        // Update the read status if provided
        if (dto.getRead() != null) {
            existing.setRead(dto.getRead());
        }

        // Save the updated Notification entity to the repository
        Notification updated = notificationRepository.save(existing);

        // Convert the updated Notification entity to a NotificationDTO and return
        return mapToDTO(updated);
    }

    /**
     * Deletes a notification based on its ID.
     *
     * @param id The ID of the notification to be deleted. Must be a positive Long.
     * @throws ResourceNotFoundException If the notification with the given ID does not exist.
     *
     * <b>Pass Condition:</b> The specified Notification is successfully deleted from the repository.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the notification is not found.
     */
    public void deleteNotification(Long id) {
        // Retrieve the Notification entity by ID; throw exception if not found
        Notification existing = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));

        // Delete the retrieved Notification entity from the repository
        notificationRepository.delete(existing);
    }

    /**
     * Maps a Notification entity to its corresponding NotificationDTO.
     *
     * @param notification The Notification entity to be converted. Must not be null.
     * @return NotificationDTO The data transfer object representing the Notification entity.
     *
     * <b>Premise:</b> The Notification entity contains valid references to a User.
     * <b>Assertion:</b> The returned NotificationDTO accurately reflects the data in the Notification entity.
     * <b>Pass Condition:</b> Successfully maps all relevant fields from Notification to NotificationDTO.
     */
    public NotificationDTO mapToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setUserId(notification.getUser().getId());
        dto.setRead(notification.isRead());
        return dto;
    }
}
