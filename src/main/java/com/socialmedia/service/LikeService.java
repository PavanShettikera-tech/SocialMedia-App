package com.socialmedia.service;

import com.socialmedia.dto.LikeDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling operations related to Likes on posts.
 */
@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a like to a specific post by a user.
     *
     * @param likeDTO The data transfer object containing the post ID and user ID.
     *                - postId: ID of the post to be liked. Must be a positive Long.
     *                - userId: ID of the user who is liking the post. Must be a positive Long.
     * @return LikeDTO The DTO representing the saved Like entity.
     * @throws ResourceNotFoundException If the post or user with the given IDs does not exist.
     *
     * <b>Pass Condition:</b> A Like is successfully created and saved, returning the corresponding LikeDTO.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the post or user is not found.
     */
    public LikeDTO likePost(LikeDTO likeDTO) {
        // Retrieve the post by ID; throw exception if not found
        Post post = postRepository.findById(likeDTO.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + likeDTO.getPostId()));
        
        // Retrieve the user by ID; throw exception if not found
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + likeDTO.getUserId()));

        // Create a new Like entity and associate it with the retrieved post and user
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        
        // Save the Like entity to the repository
        Like saved = likeRepository.save(like);

        // Convert the saved Like entity to a LikeDTO and return
        return mapToDTO(saved);
    }

    /**
     * Retrieves the total number of likes for a specific post.
     *
     * @param postId The ID of the post for which to count likes. Must be a positive Long.
     * @return Long The count of likes associated with the given post ID.
     *
     * <b>Pass Condition:</b> Returns the correct count of likes for the specified post.
     * <b>Fail Condition:</b> If the post ID does not exist, it returns a count of zero.
     */
    public Long getLikesCount(Long postId) {
        // Count the number of likes associated with the given post ID
        return likeRepository.countByPostId(postId);
    }

    /**
     * Removes a like from a post based on the like ID.
     *
     * @param id The ID of the like to be removed. Must be a positive Long.
     * @throws ResourceNotFoundException If the like with the given ID does not exist.
     *
     * <b>Pass Condition:</b> The specified Like entity is successfully deleted from the repository.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the like is not found.
     */
    public void unlikePost(Long id) {
        // Retrieve the Like entity by ID; throw exception if not found
        Like existingLike = likeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Like not found with id " + id));
        
        // Delete the retrieved Like entity from the repository
        likeRepository.delete(existingLike);
    }

    /**
     * Maps a Like entity to its corresponding LikeDTO.
     *
     * @param like The Like entity to be converted. Must not be null.
     * @return LikeDTO The data transfer object representing the Like entity.
     *
     * <b>Premise:</b> The Like entity contains valid references to a Post and a User.
     * <b>Assertion:</b> The returned LikeDTO accurately reflects the data in the Like entity.
     * <b>Pass Condition:</b> Successfully maps all relevant fields from Like to LikeDTO.
     */
    public LikeDTO mapToDTO(Like like) {
        LikeDTO dto = new LikeDTO();
        dto.setId(like.getId());
        dto.setPostId(like.getPost().getId());
        dto.setUserId(like.getUser().getId());
        return dto;
    }
}
