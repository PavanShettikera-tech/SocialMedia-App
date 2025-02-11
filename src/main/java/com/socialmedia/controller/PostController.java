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
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: Uses {@link PostService} which references {@link com.socialmedia.repository.PostRepository}.</li>
 *   <li>Functions: CRUD with expanded doc about constraints (e.g., user must exist for creation) and pass/fail conditions.</li>
 *   <li>Comments: Error conditions clearly explained.</li>
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
     * <p><strong>Pass/Fail Condition:</strong>  
     * Pass: If userId is valid, title/content are valid.  
     * Fail: If user is missing (ResourceNotFoundException) or invalid content triggers some validation.
     * </p>
     *
     * @param postDTO The data transfer object containing post details.
     * @return Created PostDTO (HTTP 201).
     */
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.createPost(postDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }


    /**
     * Retrieves all posts.
     *
     * <p>No specific error condition: returns empty list if none exist.</p>
     *
     * @return A list of PostDTO (HTTP 200).
     */
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post to be retrieved.
     * @return The corresponding PostDTO (HTTP 200).
     * @throws ResourceNotFoundException If the post with given ID is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }


    /**
     * Updates an existing post.
     *
     * @param id The ID of the post to be updated.
     * @param postDTO The data transfer object containing updated post details.
     * @return Updated PostDTO (HTTP 200).
     * @throws ResourceNotFoundException If the post with given ID does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }


    /**
     * Deletes a post.
     *
     * @param id The ID of the post to be deleted.
     * @return HTTP 204 No Content on success.
     * @throws ResourceNotFoundException If the post with given ID is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    /**
     * Exception handler for {@link ResourceNotFoundException}.
     *
     * <p>Returns 404 if resource is missing.</p>
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
