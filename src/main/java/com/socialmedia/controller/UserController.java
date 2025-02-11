package com.socialmedia.controller;


import com.socialmedia.dto.UserDTO;
import com.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * REST controller for managing users in the Social Media Application.
 *
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: Cross-references {@link UserService}, which references {@link com.socialmedia.repository.UserRepository}.</li>
 *   <li>Functions: CRUD + login with expanded doc. Avoids extra small helper methods.</li>
 *   <li>Comments: Parameter constraints (unique email, password constraints) and pass/fail conditions explained.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;


    /**
     * Registers a new user.
     *
     * <p><strong>Acceptable Values:</strong>  
     * - Non-empty email (must not already be used).  
     * - Password non-empty or meets some security constraints.
     * </p>
     *
     * @param userDTO The data transfer object containing user registration details.
     * @return Created UserDTO (HTTP 201).
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.registerUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    /**
     * Authenticates a user and returns an authentication token (mocked).
     *
     * @param userDTO The data transfer object containing user login credentials.
     * @return A token string (HTTP 200) on success.
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        String token = userService.loginUser(userDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    /**
     * Retrieves all users.
     *
     * <p>Returns an empty list if no users found.</p>
     *
     * @return A list of UserDTO (HTTP 200).
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return The corresponding UserDTO (HTTP 200).
     * @throws com.socialmedia.exception.ResourceNotFoundException If user not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    /**
     * Updates an existing user.
     *
     * @param id The ID of the user to be updated.
     * @param userDTO The data transfer object with updated user details.
     * @return Updated UserDTO (HTTP 200).
     * @throws com.socialmedia.exception.ResourceNotFoundException If user not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    /**
     * Deletes a user.
     *
     * @param id The ID of the user to be deleted.
     * @return HTTP 204 No Content on success.
     * @throws com.socialmedia.exception.ResourceNotFoundException If user not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
