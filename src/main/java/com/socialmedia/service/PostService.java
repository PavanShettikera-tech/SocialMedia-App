package com.socialmedia.service;

import com.socialmedia.dto.PostDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to Posts.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new post for a specific user.
     *
     * @param postDTO The data transfer object containing post details.
     *                - userId: ID of the user creating the post. Must be a positive Long.
     *                - title: The title of the post. Must not be null or empty.
     *                - content: The content of the post. Must not be null.
     * @return PostDTO The DTO representing the created Post entity.
     * @throws ResourceNotFoundException If the user with the given ID does not exist.
     *
     * <b>Pass Condition:</b> A Post is successfully created and saved, returning the corresponding PostDTO.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the user is not found.
     */
    public PostDTO createPost(PostDTO postDTO) {
        // Retrieve the user by ID; throw exception if not found
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + postDTO.getUserId()));

        // Create a new Post entity and set its properties
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setUser(user);

        // Save the Post entity to the repository
        Post saved = postRepository.save(post);

        // Convert the saved Post entity to a PostDTO and return
        return new PostDTO(saved.getId(), saved.getTitle(), saved.getContent(), saved.getUser().getId());
    }

    /**
     * Retrieves all posts in the system.
     *
     * @return List&lt;PostDTO&gt; A list of all PostDTOs.
     *
     * <b>Pass Condition:</b> Returns a list of all posts. If no posts exist, returns an empty list.
     * <b>Fail Condition:</b> N/A as the method handles empty repositories gracefully.
     */
    public List<PostDTO> getAllPosts() {
        // Retrieve all Post entities and map them to DTOs
        return postRepository.findAll().stream()
                .map(p -> new PostDTO(p.getId(), p.getTitle(), p.getContent(), p.getUser().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param id The ID of the post to retrieve. Must be a positive Long.
     * @return PostDTO The DTO representing the retrieved Post entity.
     * @throws ResourceNotFoundException If the post with the given ID does not exist.
     *
     * <b>Pass Condition:</b> Returns the PostDTO for the specified ID.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the post is not found.
     */
    public PostDTO getPostById(Long id) {
        // Retrieve the Post entity by ID; throw exception if not found
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        // Convert the Post entity to a PostDTO and return
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }

    /**
     * Updates an existing post's title and content.
     *
     * @param id      The ID of the post to update. Must be a positive Long.
     * @param postDTO The data transfer object containing updated post details.
     *                - title: The new title of the post. Must not be null or empty.
     *                - content: The new content of the post. Must not be null.
     * @return PostDTO The DTO representing the updated Post entity.
     * @throws ResourceNotFoundException If the post with the given ID does not exist.
     *
     * <b>Pass Condition:</b> The specified Post is successfully updated and saved, returning the corresponding PostDTO.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the post is not found.
     */
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        // Retrieve the existing Post entity by ID; throw exception if not found
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        // Update the title and content of the Post
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        // Save the updated Post entity to the repository
        Post updated = postRepository.save(post);

        // Convert the updated Post entity to a PostDTO and return
        return new PostDTO(updated.getId(), updated.getTitle(), updated.getContent(), updated.getUser().getId());
    }

    /**
     * Deletes a post based on its ID.
     *
     * @param id The ID of the post to delete. Must be a positive Long.
     * @throws ResourceNotFoundException If the post with the given ID does not exist.
     *
     * <b>Pass Condition:</b> The specified Post is successfully deleted from the repository.
     * <b>Fail Condition:</b> Throws ResourceNotFoundException if the post is not found.
     */
    public void deletePost(Long id) {
        // Retrieve the Post entity by ID; throw exception if not found
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));

        // Delete the retrieved Post entity from the repository
        postRepository.delete(post);
    }
}
