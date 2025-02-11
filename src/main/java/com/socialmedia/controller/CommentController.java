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
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: Cross-references {@link CommentService} which references repositories.</li>
 *   <li>Functions: CRUD endpoints with expanded doc comments about pass/fail conditions.</li>
 *   <li>Comments: Input constraints (comment content length?), error conditions, etc.</li>
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
     * <p>
     * <strong>Acceptable Ranges/Constraints:</strong>
     * - CommentDTO.content cannot be null or empty (otherwise an InvalidInputException might be thrown in service).
     * - postId must exist in DB, userId must exist in DB, or we throw ResourceNotFoundException.
     * </p>
     *
     * @param commentDTO The data transfer object containing comment details.
     * @return The created {@link CommentDTO} with HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.createComment(commentDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }


    /**
     * Retrieves all comments associated with a specific post.
     *
     * <p><strong>Pass/Fail Conditions:</strong>  
     * Pass: If the post exists (or even if not, returns an empty list) and fetches comments successfully.  
     * Fail: Not strictly failing if post doesn't exist, but service might handle it gracefully or throw.
     * </p>
     *
     * @param postId The ID of the post for which comments are to be retrieved.
     * @return List of CommentDTO with HTTP 200 status.
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    /**
     * Updates an existing comment.
     *
     * <p><strong>Error Conditions:</strong>  
     * - If the comment with given ID does not exist, ResourceNotFoundException is thrown.
     * - If new content is invalid, an exception may be thrown.
     * </p>
     *
     * @param id         The ID of the comment to be updated.
     * @param commentDTO The data transfer object containing updated comment details.
     * @return The updated CommentDTO with HTTP 200 status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(id, commentDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }


    /**
     * Deletes a comment.
     *
     * <p>
     * <strong>Pass/Fail Condition:</strong>  
     * Pass: If comment exists and is successfully deleted.  
     * Fail: If comment does not exist, ResourceNotFoundException is thrown.
     * </p>
     *
     * @param id The ID of the comment to be deleted.
     * @return HTTP 204 No Content on success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
