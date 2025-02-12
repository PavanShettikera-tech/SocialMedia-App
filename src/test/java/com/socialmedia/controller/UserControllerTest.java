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
 * <p>
 * Each test method includes:
 * <ul>
 *     <li><b>Docstrings:</b> Detailed descriptions of the test purpose, parameters, expected outcomes, and conditions.</li>
 *     <li><b>Error Conditions:</b> Scenarios where invalid input or unexpected behavior is simulated to ensure proper error handling.</li>
 *     <li><b>Parameter Descriptions:</b> Clear explanations of method parameters, including their types, acceptable values, and ranges.</li>
 *     <li><b>Premises and Assertions:</b> Preconditions set up before executing the test and the assertions made to validate outcomes.</li>
 *     <li><b>Pass/Fail Conditions:</b> Criteria that determine whether the test passes or fails based on the assertions.</li>
 * </ul>
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    /**
     * MockMvc instance for performing HTTP requests in tests.
     * <p>
     * Used to simulate HTTP requests and assert responses without starting the server.
     * </p>
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked instance of {@link UserService} to simulate service layer behavior.
     * <p>
     * Allows for isolation of controller tests by mocking the business logic layer.
     * </p>
     */
    @MockBean
    private UserService userService;

    /**
     * ObjectMapper instance for serializing and deserializing JSON content.
     * <p>
     * Facilitates conversion between Java objects and JSON for request and response bodies.
     * </p>
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sample {@link UserDTO} used across multiple tests.
     * <p>
     * Represents a user entity with predefined values for consistent testing.
     * </p>
     */
    private UserDTO userDTO;

    /**
     * Initializes the sample {@link UserDTO} before each test.
     * <p>
     * Sets up common test data and initializes Mockito annotations to prepare the test environment.
     * </p>
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations for mocking
        MockitoAnnotations.openMocks(this);

        // Initialize a sample UserDTO with valid data
        userDTO = new UserDTO(1L, "testuser", "password", "test@example.com");
    }

    /**
     * Tests user registration.
     * <p>
     * Verifies that a user can successfully register with valid input data and receives a 201 (Created) status.
     * </p>
     *
     * <p>
     * <b>Premise:</b> A valid {@link UserDTO} is provided for registration.
     * <b>Assertions:</b>
     * <ul>
     *     <li>The HTTP status returned is 201 (Created).</li>
     *     <li>The response JSON contains the correct user details.</li>
     *     <li>The {@link UserService#registerUser(UserDTO)} method is invoked exactly once.</li>
     * </ul>
     * <b>Pass Condition:</b> All assertions are met without exceptions.
     * <b>Fail Condition:</b> Any assertion fails or an unexpected exception is thrown.
     * </p>
     *
     * @throws Exception If an error occurs during the request execution.
     */
    @Test
    @DisplayName("Register user successfully and receives 201")
    void testRegisterUser() throws Exception {
        // Define behavior for userService.registerUser with any UserDTO input
        when(userService.registerUser(any(UserDTO.class))).thenReturn(userDTO);

        // Perform POST request to register a new user with valid JSON content
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated()) // Expect HTTP 201 status
                .andExpect(jsonPath("$.id").value(1)) // Verify returned ID
                .andExpect(jsonPath("$.username").value("testuser")) // Verify returned username
                .andExpect(jsonPath("$.email").value("test@example.com")); // Verify returned email

        // Verify that userService.registerUser was called once with any UserDTO
        verify(userService, times(1)).registerUser(any(UserDTO.class));
    }

    /**
     * Tests user login.
     * <p>
     * Verifies that a user can successfully login with valid credentials and receives a 200 (OK) status along with an authentication token.
     * </p>
     *
     * <p>
     * <b>Premise:</b> A valid {@link UserDTO} containing correct username and password is provided.
     * <b>Assertions:</b>
     * <ul>
     *     <li>The HTTP status returned is 200 (OK).</li>
     *     <li>The response body contains the expected authentication token.</li>
     *     <li>The {@link UserService#loginUser(UserDTO)} method is invoked exactly once.</li>
     * </ul>
     * <b>Pass Condition:</b> All assertions are met without exceptions.
     * <b>Fail Condition:</b> Any assertion fails or an unexpected exception is thrown.
     * </p>
     *
     * @throws Exception If an error occurs during the request execution.
     */
    @Test
    @DisplayName("User can login successfully and receives 200 with token")
    void testLoginUser() throws Exception {
        // Define a mock authentication token to be returned by the service
        String token = "mockToken123";
        when(userService.loginUser(any(UserDTO.class))).thenReturn(token);

        // Perform POST request to login with valid credentials
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk()) // Expect HTTP 200 status
                .andExpect(content().string("mockToken123")); // Verify returned token

        // Verify that userService.loginUser was called once with any UserDTO
        verify(userService, times(1)).loginUser(any(UserDTO.class));
    }

    /**
     * Tests retrieving all users as a USER.
     * <p>
     * Verifies that an authenticated user with the role "USER" can successfully retrieve a list of all users and receives a 200 (OK) status.
     * </p>
     *
     * <p>
     * <b>Premise:</b> An authenticated user with the "USER" role requests the list of all users.
     * <b>Assertions:</b>
     * <ul>
     *     <li>The HTTP status returned is 200 (OK).</li>
     *     <li>The response JSON contains the expected list of users with correct details.</li>
     *     <li>The {@link UserService#getAllUsers()} method is invoked exactly once.</li>
     * </ul>
     * <b>Pass Condition:</b> All assertions are met without exceptions.
     * <b>Fail Condition:</b> Any assertion fails, incorrect role access, or an unexpected exception is thrown.
     * </p>
     *
     * @throws Exception If an error occurs during the request execution.
     */
    @Test
    @DisplayName("USER can retrieve all users and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetAllUsers() throws Exception {
        // Define a list of UserDTOs to be returned by the mocked service
        List<UserDTO> users = Arrays.asList(userDTO);
        when(userService.getAllUsers()).thenReturn(users);

        // Perform GET request to retrieve all users as an authenticated USER
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 status
                .andExpect(jsonPath("$[0].id").value(1)) // Verify first user's ID
                .andExpect(jsonPath("$[0].username").value("testuser")) // Verify first user's username
                .andExpect(jsonPath("$[0].email").value("test@example.com")); // Verify first user's email

        // Verify that userService.getAllUsers was called once
        verify(userService, times(1)).getAllUsers();
    }

    /**
     * Tests retrieving a user by their ID as a USER.
     * <p>
     * Verifies that an authenticated user with the role "USER" can successfully retrieve a specific user's details by ID and receives a 200 (OK) status.
     * </p>
     *
     * <p>
     * <b>Premise:</b> An authenticated user with the "USER" role requests the details of a user with a valid ID.
     * <b>Assertions:</b>
     * <ul>
     *     <li>The HTTP status returned is 200 (OK).</li>
     *     <li>The response JSON contains the correct user details corresponding to the provided ID.</li>
     *     <li>The {@link UserService#getUserById(Long)} method is invoked exactly once with the correct ID.</li>
     * </ul>
     * <b>Pass Condition:</b> All assertions are met without exceptions.
     * <b>Fail Condition:</b> Any assertion fails, invalid ID access, or an unexpected exception is thrown.
     * </p>
     *
     * @throws Exception If an error occurs during the request execution.
     */
    @Test
    @DisplayName("USER can retrieve a user by ID and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetUserById() throws Exception {
        // Define behavior for userService.getUserById with ID 1L
        when(userService.getUserById(1L)).thenReturn(userDTO);

        // Perform GET request to retrieve the user by ID as an authenticated USER
        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 status
                .andExpect(jsonPath("$.id").value(1)) // Verify user's ID
                .andExpect(jsonPath("$.username").value("testuser")) // Verify user's username
                .andExpect(jsonPath("$.email").value("test@example.com")); // Verify user's email

        // Verify that userService.getUserById was called once with ID 1L
        verify(userService, times(1)).getUserById(1L);
    }

    /**
     * Tests updating a user as a USER.
     * <p>
     * Verifies that an authenticated user with the role "USER" can successfully update their own details and receives a 200 (OK) status.
     * </p>
     *
     * <p>
     * <b>Premise:</b> An authenticated user with the "USER" role provides valid updated details for their account.
     * <b>Assertions:</b>
     * <ul>
     *     <li>The HTTP status returned is 200 (OK).</li>
     *     <li>The response JSON reflects the updated user details.</li>
     *     <li>The {@link UserService#updateUser(Long, UserDTO)} method is invoked exactly once with the correct ID and updated data.</li>
     * </ul>
     * <b>Pass Condition:</b> All assertions are met without exceptions.
     * <b>Fail Condition:</b> Any assertion fails, invalid update data, unauthorized access, or an unexpected exception is thrown.
     * </p>
     *
     * @throws Exception If an error occurs during the request execution.
     */
    @Test
    @DisplayName("USER can update their details and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUpdateUser() throws Exception {
        // Define the updated UserDTO with new details
        UserDTO updatedUserDTO = new UserDTO(1L, "updateduser", "newpassword", "updated@example.com");

        // Define behavior for userService.updateUser with ID 1L and any UserDTO
        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUserDTO);

        // Perform PUT request to update the user as an authenticated USER
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDTO)))
                .andExpect(status().isOk()) // Expect HTTP 200 status
                .andExpect(jsonPath("$.id").value(1)) // Verify user's ID
                .andExpect(jsonPath("$.username").value("updateduser")) // Verify updated username
                .andExpect(jsonPath("$.email").value("updated@example.com")); // Verify updated email

        // Verify that userService.updateUser was called once with ID 1L and the updated UserDTO
        verify(userService, times(1)).updateUser(eq(1L), any(UserDTO.class));
    }

    /**
     * Tests deleting a user as a USER.
     * <p>
     * Verifies that an authenticated user with the role "USER" can successfully delete their account and receives a 204 (No Content) status.
     * </p>
     *
     * <p>
     * <b>Premise:</b> An authenticated user with the "USER" role requests to delete their own account by providing a valid ID.
     * <b>Assertions:</b>
     * <ul>
     *     <li>The HTTP status returned is 204 (No Content).</li>
     *     <li>The {@link UserService#deleteUser(Long)} method is invoked exactly once with the correct ID.</li>
     * </ul>
     * <b>Pass Condition:</b> All assertions are met without exceptions.
     * <b>Fail Condition:</b> Any assertion fails, invalid ID deletion attempt, unauthorized access, or an unexpected exception is thrown.
     * </p>
     *
     * @throws Exception If an error occurs during the request execution.
     */
    @Test
    @DisplayName("USER can delete their account and receives 204")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteUser() throws Exception {
        // Define behavior for userService.deleteUser with ID 1L to do nothing (successful deletion)
        doNothing().when(userService).deleteUser(1L);

        // Perform DELETE request to delete the user as an authenticated USER
        mockMvc.perform(delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Expect HTTP 204 status

        // Verify that userService.deleteUser was called once with ID 1L
        verify(userService, times(1)).deleteUser(1L);
    }
}
