package com.socialmedia.service;

import com.socialmedia.dto.UserDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.User;
import com.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link UserService} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link UserService} class,
 * including registering, logging in, retrieving, updating, and deleting users. It utilizes
 * Mockito to mock dependencies and JUnit 5 for testing.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;

    /**
     * Initializes mock objects and sample data before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Prepare UserDTO with sample data
        userDTO = new UserDTO(1L, "testuser", "password", "test@example.com");

        // Prepare User entity with sample data
        user = new User();
        user.setId(1L);
        user.setName("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
    }

    /**
     * Tests the successful registration of a user.
     */
    @Test
    @DisplayName("Test registering a user successfully")
    void testRegisterUser() {
        // Arrange: Mock the repository methods to simulate a new user registration
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act: Call the service method to register a user
        UserDTO result = userService.registerUser(userDTO);

        // Assert: Verify the result is as expected
        assertNotNull(result, "Resulting UserDTO should not be null");
        assertEquals(userDTO.getUsername(), result.getUsername(), "Username mismatch");
        assertEquals(userDTO.getEmail(), result.getEmail(), "Email mismatch");

        // Verify that repository methods were called the expected number of times
        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests registering a user when the email is already in use.
     */
    @Test
    @DisplayName("Test registering a user with an email that's already in use")
    void testRegisterUser_EmailAlreadyInUse() {
        // Arrange: Mock the userRepository to return an existing user
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert: Expect IllegalArgumentException when registering with duplicate email
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(userDTO),
                "Expected registerUser to throw IllegalArgumentException"
        );

        assertEquals("Email is already in use", exception.getMessage(), "Exception message mismatch");

        // Verify that passwordEncoder.encode and userRepository.save are never called
        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests the successful login of a user.
     */
    @Test
    @DisplayName("Test logging in a user successfully")
    void testLoginUser() {
        // Arrange: Mock the repository methods to simulate a valid login
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(true);

        // Act: Call the service method to log in the user
        String result = userService.loginUser(userDTO);

        // Assert: Verify the result is as expected (e.g., JWT token)
        assertEquals("dummy-jwt-token", result, "JWT token mismatch");

        // Verify that repository methods were called the expected number of times
        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(passwordEncoder, times(1)).matches(userDTO.getPassword(), user.getPassword());
    }

    /**
     * Tests logging in a user with invalid email or password.
     */
    @Test
    @DisplayName("Test logging in a user with invalid email or password")
    void testLoginUser_InvalidEmailOrPassword() {
        // Arrange: Mock the repository to return empty (invalid email)
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        // Act & Assert: Expect IllegalArgumentException when logging in with invalid credentials
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.loginUser(userDTO),
                "Expected loginUser to throw IllegalArgumentException"
        );

        assertEquals("Invalid email or password", exception.getMessage(), "Exception message mismatch");

        // Verify that passwordEncoder.matches is never called
        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    /**
     * Tests retrieving all users.
     */
    @Test
    @DisplayName("Test retrieving all users")
    void testGetAllUsers() {
        // Arrange: Mock the userRepository to return a list containing the prepared user
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // Act: Call the service method to get all users
        List<UserDTO> result = userService.getAllUsers();

        // Assert: Verify the results are as expected
        assertNotNull(result, "Resulting list should not be null");
        assertEquals(1, result.size(), "Resulting list size should be 1");
        UserDTO retrievedUser = result.get(0);
        assertEquals(userDTO.getUsername(), retrievedUser.getUsername(), "Username mismatch");
        assertEquals(userDTO.getEmail(), retrievedUser.getEmail(), "Email mismatch");

        // Verify that userRepository.findAll was called once
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Tests retrieving a user by ID when the user is found.
     */
    @Test
    @DisplayName("Test retrieving a user by ID when found")
    void testGetUserById() {
        // Arrange: Mock the userRepository to return the prepared user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act: Call the service method to get a user by ID
        UserDTO result = userService.getUserById(1L);

        // Assert: Verify the result is as expected
        assertNotNull(result, "Resulting UserDTO should not be null");
        assertEquals(userDTO.getUsername(), result.getUsername(), "Username mismatch");
        assertEquals(userDTO.getEmail(), result.getEmail(), "Email mismatch");

        // Verify that userRepository.findById was called once
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests retrieving a user by ID when the user is not found.
     */
    @Test
    @DisplayName("Test retrieving a user by ID when not found")
    void testGetUserById_NotFound() {
        // Arrange: Mock the userRepository to return empty
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException when retrieving a non-existent user
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUserById(1L),
                "Expected getUserById to throw ResourceNotFoundException"
        );

        assertEquals("User not found with id 1", exception.getMessage(), "Exception message mismatch");

        // Verify that userRepository.findById was called once
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests updating a user successfully.
     */
    @Test
    @DisplayName("Test updating a user successfully")
    void testUpdateUser() {
        // Arrange: Mock the repository methods to find and save the user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act: Call the service method to update the user
        UserDTO result = userService.updateUser(1L, userDTO);

        // Assert: Verify the result is as expected
        assertNotNull(result, "Resulting UserDTO should not be null");
        assertEquals(userDTO.getUsername(), result.getUsername(), "Username mismatch");
        assertEquals(userDTO.getEmail(), result.getEmail(), "Email mismatch");

        // Verify that repository methods were called the expected number of times
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests updating a user that does not exist.
     */
    @Test
    @DisplayName("Test updating a user that does not exist")
    void testUpdateUser_NotFound() {
        // Arrange: Mock the userRepository to return empty when searching for the user
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException when updating a non-existent user
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.updateUser(1L, userDTO),
                "Expected updateUser to throw ResourceNotFoundException"
        );

        assertEquals("User not found with id 1", exception.getMessage(), "Exception message mismatch");

        // Verify that userRepository.save is never called
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests deleting a user successfully.
     */
    @Test
    @DisplayName("Test deleting a user successfully")
    void testDeleteUser() {
        // Arrange: Mock the userRepository to find and delete the existing user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act: Call the service method to delete the user
        userService.deleteUser(1L);

        // Assert: Verify that the user was deleted successfully
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    /**
     * Tests deleting a user that does not exist.
     */
    @Test
    @DisplayName("Test deleting a user that does not exist")
    void testDeleteUser_NotFound() {
        // Arrange: Mock the userRepository to return empty when searching for the user
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect ResourceNotFoundException when deleting a non-existent user
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.deleteUser(1L),
                "Expected deleteUser to throw ResourceNotFoundException"
        );

        assertEquals("User not found with id 1", exception.getMessage(), "Exception message mismatch");

        // Verify that userRepository.delete is never called
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).delete(any(User.class));
    }

    /**
     * Tests mapping a User entity to a UserDTO indirectly by retrieving a user by ID.
     */
    @Test
    @DisplayName("Test mapping User entity to UserDTO indirectly")
    void testMapToDTO_Indirectly() {
        // Arrange: Mock the userRepository to return the prepared user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act: Retrieve user by ID and map to DTO
        UserDTO result = userService.getUserById(1L);

        // Assert: Verify the mapped UserDTO is as expected
        assertNotNull(result, "Resulting UserDTO should not be null");
        assertEquals(user.getId(), result.getId(), "User ID mismatch");
        assertEquals(user.getName(), result.getUsername(), "Username mismatch");
        assertEquals(user.getEmail(), result.getEmail(), "Email mismatch");

        // Verify that repository methods were called the expected number of times
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests mapping a UserDTO to a User entity indirectly by registering a user.
     */
    @Test
    @DisplayName("Test mapping UserDTO to User entity indirectly")
    void testMapToEntity_Indirectly() {
        // Arrange: Mock the repository methods to simulate a new user registration
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act: Call the service method to register a user
        UserDTO result = userService.registerUser(userDTO);

        // Assert: Verify the result is as expected
        assertNotNull(result, "Resulting UserDTO should not be null");
        assertEquals(userDTO.getUsername(), result.getUsername(), "Username mismatch");
        assertEquals(userDTO.getEmail(), result.getEmail(), "Email mismatch");

        // Verify that repository methods were called the expected number of times
        verify(userRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
