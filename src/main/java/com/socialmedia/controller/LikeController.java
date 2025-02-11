package com.socialmedia.controller;


import com.socialmedia.dto.LikeDTO;
import com.socialmedia.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for managing likes in the Social Media Application.
 *
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: Cross-references {@link LikeService}, which interacts with {@code LikeRepository}.</li>
 *   <li>Functions: Consolidated endpoints for like/unlike with doc about pass/fail conditions.</li>
 *   <li>Comments: Parameter constraints (postId and userId must exist), error handling for missing resources.</li>
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
     * <p><strong>Pass/Fail Condition:</strong>  
     * Pass: If postId and userId exist in DB; a new Like is created.  
     * Fail: If postId or userId not found, ResourceNotFoundException thrown.
     * </p>
     *
     * @param likeDTO The data transfer object containing like details.
     * @return The created LikeDTO with HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<LikeDTO> likePost(@RequestBody LikeDTO likeDTO) {
        LikeDTO createdLike = likeService.likePost(likeDTO);
        return new ResponseEntity<>(createdLike, HttpStatus.CREATED);
    }


    /**
     * Retrieves the number of likes for a specific post.
     *
     * <p><strong>Error Conditions:</strong>  
     * - If post does not exist, the service layer typically returns 0 or may handle it differently.
     * </p>
     *
     * @param postId The ID of the post for which the like count is to be retrieved.
     * @return The total number of likes (Long).
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long postId) {
        Long count = likeService.getLikesCount(postId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }


    /**
     * Unlikes a post.
     *
     * <p><strong>Pass/Fail Condition:</strong>  
     * Pass: If the Like ID exists, it is removed.  
     * Fail: If the Like ID does not exist, ResourceNotFoundException is thrown.
     * </p>
     *
     * @param id The ID of the like to be deleted.
     * @return HTTP 204 No Content on success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long id) {
        likeService.unlikePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
