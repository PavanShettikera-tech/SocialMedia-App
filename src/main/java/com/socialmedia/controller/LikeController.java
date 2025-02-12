package com.socialmedia.controller;

import com.socialmedia.dto.LikeDTO;
import com.socialmedia.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing likes in the Social Media Application.
 *
 * <p><strong>Overview:</strong></p>
 * This controller provides endpoints for users to like and unlike posts, as well as to retrieve the total number of likes
 * associated with a specific post. It interacts with the {@link LikeService} to perform business logic and ensures that
 * all like-related operations adhere to the application's validation and authorization rules.
 *
 * <p><strong>References:</strong></p>
 * <ul>
 *   <li>{@link LikeService} handles the business logic and interacts with {@code LikeRepository} to manage like entities.</li>
 *   <li>{@link LikeDTO} serves as the Data Transfer Object for like data between client and server.</li>
 * </ul>
 *
 * <p><strong>Functionality:</strong></p>
 * <ul>
 *   <li>Allows users to like a post.</li>
 *   <li>Retrieves the total number of likes for a specific post.</li>
 *   <li>Enables users to unlike a post by removing their like.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong></p>
 * <ul>
 *   <li><strong>Pass:</strong> Successfully performs like/unlike operations and retrieves like counts, returning appropriate HTTP statuses.</li>
 *   <li><strong>Fail:</strong> Returns error responses (e.g., HTTP 400, 404) when input constraints are violated or resources are not found.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * Likes a post.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to like a specific post. It validates the input data to ensure that both
     * the {@code postId} and {@code userId} exist in the database before creating a new like entry. This prevents duplicate
     * likes and ensures data integrity.
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>LikeDTO.postId:</strong> Must reference an existing post in the database.</li>
     *   <li><strong>LikeDTO.userId:</strong> Must reference an existing user in the database.</li>
     *   <li><strong>LikeDTO:</strong> Must contain valid and non-null data for both {@code postId} and {@code userId}.</li>
     * </ul>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>likeDTO</strong> (<em>{@link LikeDTO}</em>): The data transfer object containing details of the like, including {@code postId} and {@code userId}.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If {@code postId} does not correspond to an existing post, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If {@code userId} does not correspond to an existing user, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If the user has already liked the post, an {@link InvalidInputException} may be thrown to prevent duplicate likes.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The user performing the like action must be authenticated and authorized to like the specified post.</li>
     *   <li>The {@link LikeService} must correctly handle the creation logic and interact with the repository layer to persist the like.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully creates a like entry and returns the created {@link LikeDTO} with HTTP 201 (Created) status.</li>
     *   <li><strong>Fail:</strong> Returns appropriate error responses if the post or user does not exist, or if input validation fails.</li>
     * </ul>
     *
     * @param likeDTO The data transfer object containing like details, including {@code postId} and {@code userId}.
     * @return The created {@link LikeDTO} with HTTP 201 (Created) status.
     */
    @PostMapping
    public ResponseEntity<LikeDTO> likePost(@RequestBody LikeDTO likeDTO) {
        LikeDTO createdLike = likeService.likePost(likeDTO);
        return new ResponseEntity<>(createdLike, HttpStatus.CREATED);
    }

    /**
     * Retrieves the number of likes for a specific post.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint fetches the total number of likes associated with a given {@code postId}. It returns a count of likes,
     * allowing clients to display like metrics for posts. If the specified post has no likes, it returns a count of zero.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>postId</strong> (<em>Long</em>): The unique identifier of the post for which the like count is to be retrieved.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>postId:</strong> Must reference an existing post in the database.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If {@code postId} does not correspond to an existing post, the service layer may handle it gracefully by returning a count of zero or throwing a {@link ResourceNotFoundException} based on implementation.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The specified {@code postId} should reference an existing post within the database.</li>
     *   <li>The {@link LikeService} must correctly retrieve and count likes associated with the given post.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully retrieves and returns the total number of likes for the specified post, even if the count is zero.</li>
     *   <li><strong>Fail:</strong> If the {@code postId} does not exist and the service layer chooses to throw a {@link ResourceNotFoundException}, an appropriate error response is returned.</li>
     * </ul>
     *
     * @param postId The ID of the post for which the like count is to be retrieved.
     * @return The total number of likes (Long) with HTTP 200 (OK) status.
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long postId) {
        Long count = likeService.getLikesCount(postId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * Unlikes a post.
     *
     * <p><strong>Description:</strong></p>
     * This endpoint allows authenticated users to remove their like from a specific post. It validates the existence of the
     * like entry before performing the deletion to ensure data integrity and prevent errors.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>id</strong> (<em>Long</em>): The unique identifier of the like to be deleted.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Constraints:</strong></p>
     * <ul>
     *   <li><strong>id:</strong> Must reference an existing like entry in the database.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If the like with the given {@code id} does not exist, a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>If the user attempting to unlike does not own the like or lacks the necessary permissions, an {@link AccessDeniedException} may be thrown.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The like to be deleted must exist in the database.</li>
     *   <li>The user performing the unlike action must be authenticated and authorized to remove the like.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Successfully deletes the like entry and returns HTTP 204 (No Content) status.</li>
     *   <li><strong>Fail:</strong> Returns appropriate error responses if the like does not exist or if the user lacks permissions.</li>
     * </ul>
     *
     * @param id The ID of the like to be deleted.
     * @return HTTP 204 (No Content) status on successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long id) {
        likeService.unlikePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
