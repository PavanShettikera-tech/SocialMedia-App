package com.socialmedia.service;

import com.socialmedia.dto.NotificationDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Notification;
import com.socialmedia.model.User;
import com.socialmedia.repository.NotificationRepository;
import com.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link NotificationService} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link NotificationService} class,
 * including creating, retrieving, updating, and deleting notifications. It utilizes Mockito to mock
 * dependencies and JUnit 5 for testing.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    private NotificationDTO testDTO;
    private Notification testNotification;
    private User testUser;

    /**
     * Initializes mock objects and sample data before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Prepare User entity with sample data
        testUser = new User();
        testUser.setId(1L);

        // Prepare NotificationDTO with sample data
        testDTO = new NotificationDTO();
        testDTO.setMessage("Test message");
        testDTO.setUserId(1L);

        // Prepare Notification entity with sample data
        testNotification = new Notification();
        testNotification.setId(1L);
        testNotification.setMessage("Test message");
        testNotification.setUser(testUser);
    }

    /**
     * Tests the successful creation of a notification.
     */
    @Test
    @DisplayName("Test creating a notification successfully")
    void createNotification_ValidInput_ReturnsNotificationDTO() {
        // Arrange: Mock the repository methods to return the prepared entities
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        // Act: Call the service method to create a notification
        NotificationDTO result = notificationService.createNotification(testDTO);

        // Assert: Verify the result is as expected
        assertNotNull(result, "Resulting NotificationDTO should not be null");
        assertEquals("Test message", result.getMessage(), "Notification message mismatch");
        assertEquals(1L, result.getUserId(), "User ID mismatch");

        // Verify that repository methods were called the expected number of times
        verify(userRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    /**
     * Tests retrieving notifications by user ID.
     */
    @Test
    @DisplayName("Test retrieving notifications by user ID")
    void getNotificationsByUserId_ValidId_ReturnsDTOList() {
        // Arrange: Mock the notificationRepository to return a list containing the prepared notification
        when(notificationRepository.findByUserId(1L)).thenReturn(Collections.singletonList(testNotification));

        // Act: Call the service method to get notifications by user ID
        List<NotificationDTO> results = notificationService.getNotificationsByUserId(1L);

        // Assert: Verify the results are as expected
        assertNotNull(results, "Resulting list should not be null");
        assertEquals(1, results.size(), "Resulting list size should be 1");
        NotificationDTO retrievedNotification = results.get(0);
        assertEquals("Test message", retrievedNotification.getMessage(), "Notification message mismatch");
        assertEquals(1L, retrievedNotification.getUserId(), "User ID mismatch");

        // Verify that notificationRepository.findByUserId was called once
        verify(notificationRepository, times(1)).findByUserId(1L);
    }

    /**
     * Tests updating a notification successfully.
     */
    @Test
    @DisplayName("Test updating a notification successfully")
    void updateNotification_ExistingId_ReturnsUpdatedDTO() {
        // Arrange: Prepare a NotificationDTO with updated message
        NotificationDTO updateDTO = new NotificationDTO();
        updateDTO.setMessage("Updated message");

        // Mock the repository to find the existing notification and save the updated notification
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(testNotification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        // Act: Call the service method to update the notification
        NotificationDTO result = notificationService.updateNotification(1L, updateDTO);

        // Assert: Verify the result is as expected
        assertEquals("Updated message", result.getMessage(), "Updated message mismatch");

        // Verify that repository methods were called the expected number of times
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    /**
     * Tests updating a notification that does not exist.
     */
    @Test
    @DisplayName("Test updating a notification that does not exist")
    void updateNotification_NonExistingId_ThrowsException() {
        // Arrange: Mock the notificationRepository to return empty when searching for the notification
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException when updating a non-existent notification
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> notificationService.updateNotification(1L, new NotificationDTO()),
                "Expected updateNotification to throw ResourceNotFoundException"
        );

        assertEquals("Notification not found with id 1", exception.getMessage(), "Exception message mismatch");

        // Verify that notificationRepository.save is never called
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    /**
     * Tests deleting a notification successfully.
     */
    @Test
    @DisplayName("Test deleting a notification successfully")
    void deleteNotification_ExistingId_DeletesSuccessfully() {
        // Arrange: Mock the notificationRepository to find and delete the existing notification
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(testNotification));
        doNothing().when(notificationRepository).delete(any(Notification.class));

        // Act: Call the service method to delete the notification
        notificationService.deleteNotification(1L);

        // Assert: Verify that the notification was deleted successfully
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).delete(any(Notification.class));
    }

    /**
     * Tests deleting a notification that does not exist.
     */
    @Test
    @DisplayName("Test deleting a notification that does not exist")
    void deleteNotification_NonExistingId_ThrowsException() {
        // Arrange: Mock the notificationRepository to return empty when searching for the notification
        when(notificationRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException when deleting a non-existent notification
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> notificationService.deleteNotification(1L),
                "Expected deleteNotification to throw ResourceNotFoundException"
        );

        assertEquals("Notification not found with id 1", exception.getMessage(), "Exception message mismatch");

        // Verify that notificationRepository.delete is never called
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, never()).delete(any(Notification.class));
    }

    /**
     * Tests mapping a NotificationDTO to a Notification entity when the associated user is not found.
     */
    @Test
    @DisplayName("Test mapping NotificationDTO to Notification entity when user is not found")
    void mapToEntity_InvalidUserId_ThrowsException() {
        // Arrange: Prepare a NotificationDTO with a non-existing user ID
        NotificationDTO invalidDTO = new NotificationDTO();
        invalidDTO.setUserId(99L);

        // Mock the userRepository to return empty
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException when mapping fails
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> notificationService.createNotification(invalidDTO),
                "Expected createNotification to throw ResourceNotFoundException"
        );

        assertEquals("User not found with id 99", exception.getMessage(), "Exception message mismatch");

        // Verify that notificationRepository.save is never called
        verify(userRepository, times(1)).findById(99L);
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    /**
     * Tests mapping a Notification entity to a NotificationDTO.
     */
    @Test
    @DisplayName("Test mapping Notification entity to NotificationDTO")
    void mapToDTO_ValidEntity_ReturnsCorrectDTO() {
        // Act: Call the service method to map to DTO
        NotificationDTO result = notificationService.mapToDTO(testNotification);

        // Assert: Verify the mapped DTO is as expected
        assertEquals("Test message", result.getMessage(), "Notification message mismatch");
        assertEquals(1L, result.getUserId(), "User ID mismatch");
    }
}
