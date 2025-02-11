package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.dto.UserDTO;
import com.socialmedia.service.UserService;
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
 * Test class for {@link UserController}.
 * <p>
 * This class contains unit tests to verify the behavior of the {@link UserController} endpoints,
 * including user registration, login, retrieval, updating, and deletion. It utilizes MockMvc to simulate HTTP requests
 * and Mockito to mock service layer dependencies.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    /**
     * MockMvc instance for performing HTTP requests in tests.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked instance of {@link UserService} to simulate service layer behavior.
     */
    @MockBean
    private UserService userService;

    /**
     * ObjectMapper instance for serializing and deserializing JSON content.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sample {@link UserDTO} used across multiple tests.
     */
    private UserDTO userDTO;

    /**
     * Initializes the sample {@link UserDTO} before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Initialize a sample UserDTO
        userDTO = new UserDTO(1L, "testuser", "password", "test@example.com");
    }

    /**
     * Tests user registration.
     * <p>
     * Verifies that a user can successfully register and receives a 201 (Created) status.
     * </p>
     *
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("Register user successfully and receives 201")
    void testRegisterUser() throws Exception {
        // Define behavior for userService.registerUser
        when(userService.registerUser(any(UserDTO.class))).thenReturn(userDTO);

        // Perform POST request to register a new user
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        // Verify that userService.registerUser was called once
        verify(userService, times(1)).registerUser(any(UserDTO.class));
    }

    /**
     * Tests user login.
     * <p>
     * Verifies that a user can successfully login and receives a 200 (OK) status with a token.
     * </p>
     *
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("User can login successfully and receives 200 with token")
    void testLoginUser() throws Exception {
        // Define a mock token to be returned by the service
        String token = "mockToken123";
        when(userService.loginUser(any(UserDTO.class))).thenReturn(token);

        // Perform POST request to login
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("mockToken123"));

        // Verify that userService.loginUser was called once
        verify(userService, times(1)).loginUser(any(UserDTO.class));
    }

    /**
     * Tests retrieving all users as a USER.
     * <p>
     * Verifies that a USER can successfully retrieve all users and receives a 200 (OK) status.
     * </p>
     *
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can retrieve all users and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetAllUsers() throws Exception {
        // Define a list of UserDTOs to be returned by the mocked service
        List<UserDTO> users = Arrays.asList(userDTO);
        when(userService.getAllUsers()).thenReturn(users);

        // Perform GET request to retrieve all users
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));

        // Verify that userService.getAllUsers was called once
        verify(userService, times(1)).getAllUsers();
    }

    /**
     * Tests retrieving a user by their ID as a USER.
     * <p>
     * Verifies that a USER can successfully retrieve a specific user and receives a 200 (OK) status.
     * </p>
     *
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can retrieve a user by ID and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetUserById() throws Exception {
        // Define behavior for userService.getUserById
        when(userService.getUserById(1L)).thenReturn(userDTO);

        // Perform GET request to retrieve the user by ID
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        // Verify that userService.getUserById was called once with ID 1L
        verify(userService, times(1)).getUserById(1L);
    }

    /**
     * Tests updating a user as a USER.
     * <p>
     * Verifies that a USER can successfully update their details and receives a 200 (OK) status.
     * </p>
     *
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can update their details and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUpdateUser() throws Exception {
        // Define the updated UserDTO
        UserDTO updatedUserDTO = new UserDTO(1L, "updateduser", "newpassword", "updated@example.com");

        // Define behavior for userService.updateUser
        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUserDTO);

        // Perform PUT request to update the user
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));

        // Verify that userService.updateUser was called once with ID 1L and updated DTO
        verify(userService, times(1)).updateUser(eq(1L), any(UserDTO.class));
    }

    /**
     * Tests deleting a user as a USER.
     * <p>
     * Verifies that a USER can successfully delete their account and receives a 204 (No Content) status.
     * </p>
     *
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @DisplayName("USER can delete their account and receives 204")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteUser() throws Exception {
        // Define behavior for userService.deleteUser
        doNothing().when(userService).deleteUser(1L);

        // Perform DELETE request to delete the user
        mockMvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify that userService.deleteUser was called once with ID 1L
        verify(userService, times(1)).deleteUser(1L);
    }
}
