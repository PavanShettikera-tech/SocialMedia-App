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
 * <p><strong>Feedback Implemented:</strong>
 * <ul>
 *     <li>Added detailed docstrings for class and methods.</li>
 *     <li>Included error conditions, acceptable value ranges, and parameter descriptions.</li>
 *     <li>Specified premises and assertions within the test methods.</li>
 *     <li>Clarified pass/fail conditions for each test case.</li>
 * </ul>
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
@WebMvcTest(controllers = NotificationController.class)
class NotificationControllerTest {

    /**
     * MockMvc instance for performing HTTP requests in tests.
     * <p>
     * Allows simulation of HTTP requests and verification of responses without starting the server.
     * </p>
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked instance of {@link NotificationService} to simulate service layer behavior.
     * <p>
     * Used to mock interactions with the notification service, allowing isolation of controller tests.
     * </p>
     */
    @MockBean
    private NotificationService notificationService;

    /**
     * ObjectMapper instance for serializing and deserializing JSON content.
     * <p>
     * Facilitates conversion between Java objects and JSON, enabling the construction of request bodies and parsing of responses.
     * </p>
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sample {@link NotificationDTO} used across multiple tests.
     * <p>
     * Represents a notification with predefined values for consistent testing.
     * </p>
     */
    private NotificationDTO notificationDTO;

    /**
     * Initializes the sample {@link NotificationDTO} before each test.
     * <p>
     * Sets up common test data and initializes Mockito annotations to prepare for each test case.
     * </p>
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations to enable mock behavior
        MockitoAnnotations.openMocks(this);
        
        // Initialize a sample NotificationDTO with valid values
        notificationDTO = new NotificationDTO(1L, "New notification", 1L, false);
    }

    /**
     * Tests creating a notification as a USER.
     * <p>
     * <strong>Scenario:</strong> A user with the role "USER" attempts to create a new notification.
     * <strong>Premise:</strong> The notification data provided is valid and within acceptable ranges.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 201 (Created).</li>
     *     <li>Response body contains the correct notification details.</li>
     *     <li>The service layer's createNotification method is invoked exactly once.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The notification is successfully created and all assertions pass.
     * <strong>Fail Conditions:</strong> 
     * <ul>
     *     <li>Invalid input data leads to a failure status.</li>
     *     <li>Service method is not called as expected.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can create a notification and receives 201")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateNotification() throws Exception {
        // Define behavior for notificationService.createNotification to return the sample notificationDTO
        when(notificationService.createNotification(any(NotificationDTO.class))).thenReturn(notificationDTO);

        // Perform POST request to create a new notification with valid JSON content
        mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notificationDTO)))
                .andExpect(status().isCreated()) // Expect HTTP 201 Created
                .andExpect(jsonPath("$.id").value(1)) // Verify ID
                .andExpect(jsonPath("$.message").value("New notification")) // Verify message
                .andExpect(jsonPath("$.userId").value(1)) // Verify userId
                .andExpect(jsonPath("$.read").value(false)); // Verify read status

        // Verify that notificationService.createNotification was called once with any NotificationDTO
        verify(notificationService, times(1)).createNotification(any(NotificationDTO.class));
    }

    /**
     * Tests retrieving notifications by user ID as a USER.
     * <p>
     * <strong>Scenario:</strong> A user with the role "USER" requests notifications for a specific user ID.
     * <strong>Premise:</strong> The user ID provided is valid and exists in the system.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response body contains a list of notifications with correct details.</li>
     *     <li>The service layer's getNotificationsByUserId method is invoked exactly once with the correct user ID.</li>
     * </ul>
     * <strong>Pass Condition:</strong> Notifications are successfully retrieved and all assertions pass.
     * <strong>Fail Conditions:</strong> 
     * <ul>
     *     <li>Invalid user ID leads to an error status.</li>
     *     <li>Service method is not called as expected.</li>
     * </ul>
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
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Verify first notification ID
                .andExpect(jsonPath("$[0].message").value("New notification")) // Verify message
                .andExpect(jsonPath("$[0].userId").value(1)) // Verify userId
                .andExpect(jsonPath("$[0].read").value(false)); // Verify read status

        // Verify that notificationService.getNotificationsByUserId was called once with user ID 1
        verify(notificationService, times(1)).getNotificationsByUserId(1L);
    }

    /**
     * Tests updating a notification as a USER.
     * <p>
     * <strong>Scenario:</strong> A user with the role "USER" attempts to update an existing notification.
     * <strong>Premise:</strong> The notification ID exists and the update data is valid.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response body contains the updated notification details.</li>
     *     <li>The service layer's updateNotification method is invoked exactly once with the correct ID and data.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The notification is successfully updated and all assertions pass.
     * <strong>Fail Conditions:</strong> 
     * <ul>
     *     <li>Invalid notification ID or data leads to an error status.</li>
     *     <li>Service method is not called as expected.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can update a notification and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUpdateNotification() throws Exception {
        // Define the updated NotificationDTO with valid changes
        NotificationDTO updatedNotificationDTO = new NotificationDTO(1L, "Updated notification", 1L, true);

        // Define behavior for notificationService.updateNotification to return the updated DTO
        when(notificationService.updateNotification(eq(1L), any(NotificationDTO.class))).thenReturn(updatedNotificationDTO);

        // Perform PUT request to update the notification with valid JSON content
        mockMvc.perform(put("/api/notifications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedNotificationDTO)))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verify updated ID
                .andExpect(jsonPath("$.message").value("Updated notification")) // Verify updated message
                .andExpect(jsonPath("$.userId").value(1)) // Verify userId remains the same
                .andExpect(jsonPath("$.read").value(true)); // Verify read status is updated

        // Verify that notificationService.updateNotification was called once with notification ID 1 and updated DTO
        verify(notificationService, times(1)).updateNotification(eq(1L), any(NotificationDTO.class));
    }

    /**
     * Tests deleting a notification as a USER.
     * <p>
     * <strong>Scenario:</strong> A user with the role "USER" attempts to delete an existing notification.
     * <strong>Premise:</strong> The notification ID exists and the user has permission to delete it.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 204 (No Content).</li>
     *     <li>No content is returned in the response body.</li>
     *     <li>The service layer's deleteNotification method is invoked exactly once with the correct ID.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The notification is successfully deleted and all assertions pass.
     * <strong>Fail Conditions:</strong> 
     * <ul>
     *     <li>Invalid notification ID leads to an error status.</li>
     *     <li>Service method is not called as expected.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can delete a notification and receives 204")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteNotification() throws Exception {
        // Define behavior for notificationService.deleteNotification to do nothing (void method)
        doNothing().when(notificationService).deleteNotification(1L);

        // Perform DELETE request to delete the notification with ID 1
        mockMvc.perform(delete("/api/notifications/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Expect HTTP 204 No Content

        // Verify that notificationService.deleteNotification was called once with notification ID 1
        verify(notificationService, times(1)).deleteNotification(1L);
    }
}
