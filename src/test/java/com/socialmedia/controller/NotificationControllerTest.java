package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.dto.NotificationDTO;
import com.socialmedia.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link NotificationController}.
 * <p>
 * This class contains unit tests to verify the behavior of the {@link NotificationController} endpoints,
 * including creating, retrieving, updating, and deleting notifications. It utilizes MockMvc to simulate HTTP requests
 * and Mockito to mock service layer dependencies.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
@WebMvcTest(controllers = NotificationController.class)
class NotificationControllerTest {

    /**
     * MockMvc instance for performing HTTP requests in tests.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked instance of {@link NotificationService} to simulate service layer behavior.
     */
    @MockBean
    private NotificationService notificationService;

    /**
     * ObjectMapper instance for serializing and deserializing JSON content.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sample {@link NotificationDTO} used across multiple tests.
     */
    private NotificationDTO notificationDTO;

    /**
     * Initializes the sample {@link NotificationDTO} before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
        
        // Initialize a sample NotificationDTO
        notificationDTO = new NotificationDTO(1L, "New notification", 1L, false);
    }

    /**
     * Tests creating a notification as a USER.
     * <p>
     * Verifies that a USER can successfully create a notification and receives a 201 (Created) status.
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can create a notification and receives 201")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateNotification() throws Exception {
        // Define behavior for notificationService.createNotification
        when(notificationService.createNotification(any(NotificationDTO.class))).thenReturn(notificationDTO);

        // Perform POST request to create a new notification
        mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notificationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("New notification"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.read").value(false));

        // Verify that notificationService.createNotification was called once
        verify(notificationService, times(1)).createNotification(any(NotificationDTO.class));
    }

    /**
     * Tests retrieving notifications by user ID as a USER.
     * <p>
     * Verifies that a USER can successfully retrieve notifications for a specific user and receives a 200 (OK) status.
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can retrieve notifications by user ID and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetNotificationsByUserId() throws Exception {
        // Define a list of NotificationDTOs to be returned by the mocked service
        List<NotificationDTO> notifications = Arrays.asList(notificationDTO);
        when(notificationService.getNotificationsByUserId(1L)).thenReturn(notifications);

        // Perform GET request to retrieve notifications for user ID 1
        mockMvc.perform(get("/api/notifications/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].message").value("New notification"))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].read").value(false));

        // Verify that notificationService.getNotificationsByUserId was called once with user ID 1
        verify(notificationService, times(1)).getNotificationsByUserId(1L);
    }

    /**
     * Tests updating a notification as a USER.
     * <p>
     * Verifies that a USER can successfully update a notification and receives a 200 (OK) status.
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can update a notification and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUpdateNotification() throws Exception {
        // Define the updated NotificationDTO
        NotificationDTO updatedNotificationDTO = new NotificationDTO(1L, "Updated notification", 1L, true);

        // Define behavior for notificationService.updateNotification
        when(notificationService.updateNotification(eq(1L), any(NotificationDTO.class))).thenReturn(updatedNotificationDTO);

        // Perform PUT request to update the notification
        mockMvc.perform(put("/api/notifications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedNotificationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.message").value("Updated notification"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.read").value(true));

        // Verify that notificationService.updateNotification was called once with notification ID 1 and updated DTO
        verify(notificationService, times(1)).updateNotification(eq(1L), any(NotificationDTO.class));
    }

    /**
     * Tests deleting a notification as a USER.
     * <p>
     * Verifies that a USER can successfully delete a notification and receives a 204 (No Content) status.
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can delete a notification and receives 204")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteNotification() throws Exception {
        // Define behavior for notificationService.deleteNotification
        doNothing().when(notificationService).deleteNotification(1L);

        // Perform DELETE request to delete the notification
        mockMvc.perform(delete("/api/notifications/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify that notificationService.deleteNotification was called once with notification ID 1
        verify(notificationService, times(1)).deleteNotification(1L);
    }
}
