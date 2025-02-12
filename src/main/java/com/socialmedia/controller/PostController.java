package com.socialmedia.controller;

import com.socialmedia.dto.PostDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * REST controller for managing posts in the Social Media Application.
 *
 * <p><strong>Overview:</strong></p>
 * This controller provides CRUD (Create, Read, Update, Delete) operations for posts within the Social Media application.
 * It interacts with the {@link PostService} to perform business logic and ensures that all post-related operations adhere
 * to the application's validation and authorization rules.
 *
 * <p><strong>References:</strong></p>
 * <ul>
 *   <li>{@link PostService} handles the business logic and interacts with {@link com.socialmedia.repository.PostRepository} to manage post entities.</li>
 *   <li>{@link PostDTO} serves as the Data Transfer Object for post data between client and server.</li>
 * </ul>
 *
 * <p><strong>Functionality:</strong></p>
 * <ul>
 *   <li>Creates new posts associated with users.</li>
 *   <li>Retrieves all posts or a specific post by ID.</li>
 *   <li>Updates existing posts with new content.</li>
 *   <li>Deletes posts based on their unique identifiers.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong></p>
 * <ul>
 *   <li><strong>Pass:</strong> Successfully performs the requested CRUD operation and returns the appropriate HTTP status.</li>
 *   <li><strong>Fail:</strong> Returns error responses (e.g., HTTP 400, 404) when input constraints are violated or resources are not found.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/posts")
public class PostController extends ResponseEntityExceptionHandler {

    @Autowired
    private PostService postService;

    /**
     * Creates a new post.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to create a new post. It validates the input data to ensure that the
     * {@code userId} exists and that the post's title and content meet the required constraints. Upon successful
     * validation, a new post is created and persisted in the database.
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>PostDTO.userId:</strong> Must reference an existing user in the database.</li>
     *   <li><strong>PostDTO.title:</strong> Must be non-null, non-empty, and adhere to length constraints (e.g., maximum 255 characters).</li>
     *   <li><strong>PostDTO.content:</strong> Must be non-null, non-empty, and adhere to length constraints (e.g., maximum 5000 characters).</li>
     * </ul>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>postDTO</strong> (<em>{@link PostDTO}</em>): The data transfer object containing details of the post to be created, including {@code userId}, {@code title}, and {@code content}.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If {@code userId} does not correspond to an existing user, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If {@code title} or {@code content} are null, empty, or exceed length constraints, an {@link InvalidInputException} might be thrown.</li>
     *   <li>If input validation fails due to malformed data, a {@link MethodArgumentNotValidException} may be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The user creating the post must be authenticated and authorized to create posts.</li>
     *   <li>The {@link PostService} must correctly handle the creation logic and interact with the repository layer to persist the post.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully creates a post and returns the created {@link PostDTO} with HTTP 201 (Created) status.</li>
     *   <li><strong>Fail:</strong> Returns appropriate error responses if the user does not exist or if input validation fails.</li>
     * </ul>
     *
     * @param postDTO The data transfer object containing post details, including {@code userId}, {@code title}, and {@code content}.
     * @return The created {@link PostDTO} with HTTP 201 (Created) status.
     */
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * Retrieves all posts.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint fetches all posts available in the Social Media application. It returns a list of {@link PostDTO}
     * objects, representing each post's details. If no posts exist, it returns an empty list.
     *
     * <p><strong>Parameters:</strong></p>
     * <p>No parameters required.</p>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully retrieves and returns a list of all posts with HTTP 200 (OK) status, even if the list is empty.</li>
     *   <li><strong>Fail:</strong> Unlikely to fail under normal circumstances; however, server errors may result in HTTP 500 (Internal Server Error).</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If a server error occurs while fetching posts, an {@link InternalServerErrorException} might be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The {@link PostService} must correctly retrieve all posts from the repository layer.</li>
     *   <li>Proper exception handling must be in place to manage unexpected server-side errors.</li>
     * </ul>
     *
     * @return A list of {@link PostDTO} with HTTP 200 (OK) status. Returns an empty list if no posts are found.
     */
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Retrieves a post by its ID.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint fetches the details of a specific post identified by its {@code id}. It returns a {@link PostDTO}
     * object containing the post's information. If the post with the given ID does not exist, a {@link ResourceNotFoundException} is thrown.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>id</strong> (<em>Long</em>): The unique identifier of the post to be retrieved.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>id:</strong> Must be a positive Long value corresponding to an existing post in the database.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the post with the given {@code id} does not exist, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If {@code id} is null or invalid, a {@link MethodArgumentTypeMismatchException} might be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The specified {@code id} should reference an existing post within the database.</li>
     *   <li>The {@link PostService} must correctly retrieve the post from the repository layer.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully retrieves the post and returns the corresponding {@link PostDTO} with HTTP 200 (OK) status.</li>
     *   <li><strong>Fail:</strong> Returns a 404 (Not Found) error if the post does not exist.</li>
     * </ul>
     *
     * @param id The ID of the post to be retrieved.
     * @return The corresponding {@link PostDTO} with HTTP 200 (OK) status.
     * @throws ResourceNotFoundException If the post with the given ID is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    /**
     * Updates an existing post.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to update the details of an existing post identified by its {@code id}.
     * It validates the input data to ensure that the post exists and that the updated content meets the required constraints.
     * Upon successful validation, the post is updated and persisted in the database.
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>id:</strong> Must reference an existing post in the database.</li>
     *   <li><strong>PostDTO.title:</strong> Must be non-null, non-empty, and adhere to length constraints (e.g., maximum 255 characters).</li>
     *   <li><strong>PostDTO.content:</strong> Must be non-null, non-empty, and adhere to length constraints (e.g., maximum 5000 characters).</li>
     * </ul>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>id</strong> (<em>Long</em>): The unique identifier of the post to be updated.</li>
     *   <li><strong>postDTO</strong> (<em>{@link PostDTO}</em>): The data transfer object containing updated details of the post, including {@code title} and {@code content}.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the post with the given {@code id} does not exist, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If {@code title} or {@code content} are null, empty, or exceed length constraints, an {@link InvalidInputException} might be thrown.</li>
     *   <li>If input validation fails due to malformed data, a {@link MethodArgumentNotValidException} may be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The post to be updated must exist in the database.</li>
     *   <li>The user performing the update must be authenticated and authorized to modify the post.</li>
     *   <li>The {@link PostService} must correctly handle the update logic and interact with the repository layer to persist changes.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully updates the post and returns the updated {@link PostDTO} with HTTP 200 (OK) status.</li>
     *   <li><strong>Fail:</strong> Returns appropriate error responses if the post does not exist or if input validation fails.</li>
     * </ul>
     *
     * @param id The ID of the post to be updated.
     * @param postDTO The data transfer object containing updated post details, including {@code title} and {@code content}.
     * @return The updated {@link PostDTO} with HTTP 200 (OK) status.
     * @throws ResourceNotFoundException If the post with the given ID does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    /**
     * Deletes a post.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to delete a specific post identified by its {@code id}. It ensures that the
     * post exists before performing the deletion to maintain data integrity. Upon successful deletion, the endpoint
     * returns an HTTP 204 (No Content) status.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>id</strong> (<em>Long</em>): The unique identifier of the post to be deleted.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>id:</strong> Must reference an existing post in the database.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the post with the given {@code id} does not exist, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If the user attempting to delete the post does not have the necessary permissions, an {@link AccessDeniedException} might be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The post to be deleted must exist in the database.</li>
     *   <li>The user performing the deletion must be authenticated and authorized to delete the post.</li>
     *   <li>The {@link PostService} must correctly handle the deletion logic and interact with the repository layer to remove the post.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully deletes the post and returns HTTP 204 (No Content) status.</li>
     *   <li><strong>Fail:</strong> Returns appropriate error responses if the post does not exist or if the user lacks permissions.</li>
     * </ul>
     *
     * @param id The ID of the post to be deleted.
     * @return HTTP 204 (No Content) status on successful deletion.
     * @throws ResourceNotFoundException If the post with the given ID is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * Exception handler for {@link ResourceNotFoundException}.
     *
     * <p><strong>Description:</strong></p>
     * Handles {@link ResourceNotFoundException} thrown by controller methods by returning a structured error response
     * with an HTTP 404 (Not Found) status and the exception message. This ensures that clients receive meaningful
     * error information when resources are not found.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>ex</strong> (<em>{@link ResourceNotFoundException}</em>): The exception instance containing error details.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully catches {@link ResourceNotFoundException} and returns an HTTP 404 response with the error message.</li>
     *   <li><strong>Fail:</strong> If the exception handler is not correctly configured, the exception may propagate and result in a generic error response.</li>
     * </ul>
     *
     * @param ex The {@link ResourceNotFoundException} thrown by controller methods.
     * @return A {@link ResponseEntity} containing the error message and HTTP 404 (Not Found) status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
