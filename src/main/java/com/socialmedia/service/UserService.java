package com.socialmedia.service;

import com.socialmedia.dto.UserDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.User;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to Users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     *
     * @param userDTO The data transfer object containing user registration details.
     *                - username: The name of the user. Must not be null or empty.
     *                - email: The email address of the user. Must be a valid email format and unique.
     *                - password: The password for the user account. Must meet security criteria.
     * @return UserDTO The DTO representing the created User entity.
     * @throws IllegalArgumentException If the email is already in use.
     *
     * <b>Pass Condition:</b> A User is successfully created and saved, returning the corresponding UserDTO.
     * <b>Fail Condition:</b> Throws IllegalArgumentException if the email is already in use.
     */
    public UserDTO registerUser(UserDTO userDTO) {
        // Check if the email is already in use; throw exception if it is
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Create a new User entity and set its properties
        User user = new User();
        user.setName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Save the User entity to the repository
        User savedUser = userRepository.save(user);

        // Convert the saved User entity to a UserDTO and return
        return new UserDTO(savedUser.getId(), savedUser.getName(), null, savedUser.getEmail());
    }

    /**
     * Authenticates a user and provides a JWT token upon successful login.
     *
     * @param userDTO The data transfer object containing user login details.
     *                - email: The email address of the user. Must be a valid email format.
     *                - password: The password for the user account. Must not be null.
     * @return String A dummy JWT token string representing the authenticated session.
     * @throws IllegalArgumentException If the email or password is invalid.
     *
     * <b>Pass Condition:</b> Returns a JWT token string upon successful authentication.
     * <b>Fail Condition:</b> Throws IllegalArgumentException if authentication fails.
     */
    public String loginUser(UserDTO userDTO) {
        // Retrieve the user by email; proceed if user exists
        Optional<User> userOpt = userRepository.findByEmail(userDTO.getEmail());

        // Check if the user exists and the password matches
        if (userOpt.isPresent() &&
            passwordEncoder.matches(userDTO.getPassword(), userOpt.get().getPassword())) {
            // In a real application, generate and return a JWT token
            return "dummy-jwt-token";
        } else {
            // Throw exception if authentication fails
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    /**
     * Retrieves all users in the system.
     *
     * @return List&lt;UserDTO&gt; A list of all UserDTOs.
     *
     * <b>Pass Condition:</b> Returns a list of all users. If no users exist, returns an empty list.
     * <b>Fail Condition:</b> N/A as the method handles empty repositories gracefully.
     */
    public List<UserDTO> getAllUsers() {
        // Retrieve all User entities and map them to DTOs
        return userRepository.findAll().stream()
                .map(u -> new UserDTO(u.getId(), u.getName(), null, u.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The ID of the user to retrieve. Must be a positive Long.
     * @return UserDTO The DTO representing the retrieved User entity.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     *
     * <b>Pass Condition:</b> Returns the UserDTO for the specified ID.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the user is not found.
     */
    public UserDTO getUserById(Long id) {
        // Retrieve the User entity by ID; throw exception if not found
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Convert the User entity to a UserDTO and return
        return new UserDTO(user.getId(), user.getName(), null, user.getEmail());
    }

    /**
     * Updates an existing user's details.
     *
     * @param id      The ID of the user to update. Must be a positive Long.
     * @param userDTO The data transfer object containing updated user details.
     *                - username: The new name of the user. Must not be null or empty.
     *                - email: The new email address of the user. Must be a valid email format.
     * @return UserDTO The DTO representing the updated User entity.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     *
     * <b>Pass Condition:</b> The specified User is successfully updated and saved, returning the corresponding UserDTO.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the user is not found.
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        // Retrieve the existing User entity by ID; throw exception if not found
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Update the name and email of the User
        existingUser.setName(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());

        // Save the updated User entity to the repository
        User updatedUser = userRepository.save(existingUser);

        // Convert the updated User entity to a UserDTO and return
        return new UserDTO(updatedUser.getId(), updatedUser.getName(), null, updatedUser.getEmail());
    }

    /**
     * Deletes a user based on their ID.
     *
     * @param id The ID of the user to delete. Must be a positive Long.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     *
     * <b>Pass Condition:</b> The specified User is successfully deleted from the repository.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the user is not found.
     */
    public void deleteUser(Long id) {
        // Retrieve the User entity by ID; throw exception if not found
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Delete the retrieved User entity from the repository
        userRepository.delete(existingUser);
    }
}
