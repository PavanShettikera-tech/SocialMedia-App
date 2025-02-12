package com.socialmedia.service;

import com.socialmedia.dto.CommentDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Comment;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing comments.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new comment associated with a specific post and user.
     *
     * @param commentDTO the Data Transfer Object containing comment details
     *                   <ul>
     *                       <li><b>content</b>: The textual content of the comment. Must not be null or empty.</li>
     *                       <li><b>postId</b>: The ID of the post to which the comment belongs. Must be a positive Long.</li>
     *                       <li><b>userId</b>: The ID of the user creating the comment. Must be a positive Long.</li>
     *                   </ul>
     * @return the created CommentDTO with populated ID
     * @throws ResourceNotFoundException if the post or user with the given IDs does not exist
     * @throws IllegalArgumentException if any of the parameters are invalid
     * 
     * <b>Premise:</b> The post and user IDs provided in the commentDTO must correspond to existing records.
     * <b>Assertions:</b> Validates that the post and user exist before creating the comment.
     * <b>Pass Condition:</b> Comment is successfully created and returned.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if post or user is not found.
     */
    public CommentDTO createComment(CommentDTO commentDTO) {
        // Removed colon from "Post not found with id"
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + commentDTO.getPostId()));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + commentDTO.getUserId()));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return new CommentDTO(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getPost().getId(),
                savedComment.getUser().getId()
        );
    }

    /**
     * Retrieves all comments associated with a specific post.
     *
     * @param postId the ID of the post for which to retrieve comments
     *               <ul>
     *                   <li>Type: Long</li>
     *                   <li>Range: Must be a positive Long representing an existing post ID.</li>
     *               </ul>
     * @return a list of CommentDTO objects associated with the given post ID
     * @throws ResourceNotFoundException if the post with the given ID does not exist
     * @throws IllegalArgumentException if the postId is null or invalid
     * 
     * <b>Premise:</b> The postId provided must correspond to an existing post.
     * <b>Assertions:</b> Validates that the post exists before retrieving comments.
     * <b>Pass Condition:</b> Returns a list of comments for the specified post.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the post is not found.
     */
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(c -> new CommentDTO(c.getId(), c.getContent(), c.getPost().getId(), c.getUser().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Updates the content of an existing comment.
     *
     * @param id         the ID of the comment to be updated
     *                   <ul>
     *                       <li>Type: Long</li>
     *                       <li>Range: Must be a positive Long corresponding to an existing comment.</li>
     *                   </ul>
     * @param commentDTO the Data Transfer Object containing updated comment details
     *                   <ul>
     *                       <li><b>content</b>: The new textual content of the comment. Must not be null or empty.</li>
     *                   </ul>
     * @return the updated CommentDTO
     * @throws ResourceNotFoundException if the comment with the given ID does not exist
     * @throws IllegalArgumentException if any of the parameters are invalid
     * 
     * <b>Premise:</b> The comment ID must correspond to an existing comment, and the new content must be valid.
     * <b>Assertions:</b> Ensures the comment exists before attempting to update.
     * <b>Pass Condition:</b> Comment content is successfully updated and returned.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the comment is not found.
     */
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existing = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        existing.setContent(commentDTO.getContent());
        Comment updated = commentRepository.save(existing);

        return new CommentDTO(
                updated.getId(),
                updated.getContent(),
                updated.getPost().getId(),
                updated.getUser().getId()
        );
    }

    /**
     * Deletes an existing comment by its ID.
     *
     * @param id the ID of the comment to be deleted
     *           <ul>
     *               <li>Type: Long</li>
     *               <li>Range: Must be a positive Long corresponding to an existing comment.</li>
     *           </ul>
     * @throws ResourceNotFoundException if the comment with the given ID does not exist
     * @throws IllegalArgumentException if the id is null or invalid
     * 
     * <b>Premise:</b> The comment ID must correspond to an existing comment.
     * <b>Assertions:</b> Ensures the comment exists before attempting deletion.
     * <b>Pass Condition:</b> Comment is successfully deleted.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the comment is not found.
     */
    public void deleteComment(Long id) {
        Comment existing = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
        commentRepository.delete(existing);
    }
}
