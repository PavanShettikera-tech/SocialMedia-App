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

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public NotificationDTO createNotification(NotificationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getUserId()));

        Notification notif = new Notification();
        notif.setMessage(dto.getMessage());
        notif.setUser(user);
        notif.setRead(dto.getRead() != null && dto.getRead());

        Notification saved = notificationRepository.save(notif);
        return mapToDTO(saved);
    }

    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public NotificationDTO updateNotification(Long id, NotificationDTO dto) {
        Notification existing = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));
        existing.setMessage(dto.getMessage());
        if (dto.getRead() != null) {
            existing.setRead(dto.getRead());
        }
        Notification updated = notificationRepository.save(existing);
        return mapToDTO(updated);
    }

    public void deleteNotification(Long id) {
        Notification existing = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));
        notificationRepository.delete(existing);
    }

    public NotificationDTO mapToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setUserId(notification.getUser().getId());
        dto.setRead(notification.isRead());
        return dto;
    }
}
