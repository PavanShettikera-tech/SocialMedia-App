package com.socialmedia.controller;

import com.socialmedia.dto.CommentDTO;
import com.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing comments in the Social Media Application.
 *
 * <p><strong>Overview:</strong></p>
 * This controller provides CRUD (Create, Read, Update, Delete) operations for comments associated with posts
 * within the Social Media application. It interacts with the {@link CommentService} to perform business logic
 * and ensures that all comment-related operations adhere to the application's validation and authorization rules.
 *
 * <p><strong>References:</strong></p>
 * <ul>
 *   <li>{@link CommentService} handles the business logic and interacts with repositories to manage comments.</li>
 *   <li>{@link CommentDTO} serves as the Data Transfer Object for comment data between client and server.</li>
 * </ul>
 *
 * <p><strong>Functionality:</strong></p>
 * <ul>
 *   <li>Creates new comments associated with specific posts and users.</li>
 *   <li>Retrieves comments for a given post.</li>
 *   <li>Updates existing comments with new content.</li>
 *   <li>Deletes comments based on their unique identifiers.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong></p>
 * <ul>
 *   <li><strong>Pass:</strong> Successfully performs the requested CRUD operation and returns the appropriate HTTP status.</li>
 *   <li><strong>Fail:</strong> Returns error responses (e.g., HTTP 400, 404) when input constraints are violated or resources are not found.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Creates a new comment.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to create a new comment associated with a specific post.
     * It validates the input data and ensures that the referenced post and user exist in the database.
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>CommentDTO.content:</strong> Must be non-null, non-empty, and adhere to length constraints (e.g., maximum 500 characters).</li>
     *   <li><strong>postId:</strong> Must reference an existing post in the database.</li>
     *   <li><strong>userId:</strong> Must reference an existing user in the database.</li>
     * </ul>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>commentDTO</strong> (<em>{@link CommentDTO}</em>): The data transfer object containing details of the comment to be created.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If {@code commentDTO.content} is null or empty, an {@link InvalidInputException} is thrown.</li>
     *   <li>If the provided {@code postId} or {@code userId} does not exist in the database, a {@link ResourceNotFoundException} is thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The user creating the comment must be authenticated and authorized to comment on the specified post.</li>
     *   <li>The {@link CommentService} must correctly handle the creation logic and interact with the repository layer.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully creates a comment and returns the created {@link CommentDTO} with HTTP 201 (Created) status.</li>
     *   <li><strong>Fail:</strong> Returns appropriate error responses if input validation fails or referenced resources are not found.</li>
     * </ul>
     *
     * @param commentDTO The data transfer object containing comment details.
     * @return The created {@link CommentDTO} with HTTP 201 (Created) status.
     */
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    /**
     * Retrieves all comments associated with a specific post.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint fetches all comments linked to the provided {@code postId}. It returns a list of {@link CommentDTO}
     * objects, which represent the comments made on the specified post. If the post has no comments, an empty list is returned.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>postId</strong> (<em>Long</em>): The unique identifier of the post for which comments are to be retrieved.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully retrieves and returns a list of comments associated with the specified post, even if the list is empty.</li>
     *   <li><strong>Fail:</strong> If the {@code postId} does not correspond to an existing post, a {@link ResourceNotFoundException} might be thrown depending on service implementation.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the specified {@code postId} does not exist, the service may handle it gracefully by returning an empty list or throwing a {@link ResourceNotFoundException}.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The specified {@code postId} should reference an existing post within the database.</li>
     *   <li>The {@link CommentService} must correctly retrieve comments linked to the given post.</li>
     * </ul>
     *
     * @param postId The ID of the post for which comments are to be retrieved.
     * @return A list of {@link CommentDTO} with HTTP 200 (OK) status.
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * Updates an existing comment.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to update the content of an existing comment identified by its {@code id}.
     * It validates the input data and ensures that the comment exists before performing the update.
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>CommentDTO.content:</strong> Must be non-null, non-empty, and adhere to length constraints (e.g., maximum 500 characters).</li>
     * </ul>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>id</strong> (<em>Long</em>): The unique identifier of the comment to be updated.</li>
     *   <li><strong>commentDTO</strong> (<em>{@link CommentDTO}</em>): The data transfer object containing updated comment details.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the comment with the given {@code id} does not exist, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If the new content in {@code commentDTO} is invalid (e.g., null, empty, or exceeds length constraints), an {@link InvalidInputException} might be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The comment to be updated must exist in the database.</li>
     *   <li>The user performing the update must have the necessary permissions to modify the comment.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully updates the comment and returns the updated {@link CommentDTO} with HTTP 200 (OK) status.</li>
     *   <li><strong>Fail:</strong> Returns appropriate error responses if the comment does not exist or if input validation fails.</li>
     * </ul>
     *
     * @param id         The ID of the comment to be updated.
     * @param commentDTO The data transfer object containing updated comment details.
     * @return The updated {@link CommentDTO} with HTTP 200 (OK) status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(id, commentDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    /**
     * Deletes a comment.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to delete a comment identified by its {@code id}. It ensures that the comment exists
     * before performing the deletion.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>id</strong> (<em>Long</em>): The unique identifier of the comment to be deleted.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> If the comment exists and is successfully deleted, returns HTTP 204 (No Content) status.</li>
     *   <li><strong>Fail:</strong> If the comment does not exist, a {@link ResourceNotFoundException} is thrown.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the comment with the given {@code id} does not exist, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If the user does not have permission to delete the comment, an {@link AccessDeniedException} might be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The comment to be deleted must exist in the database.</li>
     *   <li>The user performing the deletion must have the necessary permissions to delete the comment.</li>
     * </ul>
     *
     * @param id The ID of the comment to be deleted.
     * @return HTTP 204 (No Content) status on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
