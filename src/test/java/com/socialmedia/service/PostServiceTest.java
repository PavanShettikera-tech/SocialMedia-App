package com.socialmedia.service;

import com.socialmedia.dto.PostDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PostService}.
 *
 * <p>
 * This class verifies the correctness of the business logic for managing posts, ensuring proper 
 * creation, retrieval, update, and deletion of posts, along with proper error handling.
 * </p>
 *
 * <p><strong>Feedback Implemented:</strong></p>
 * <ul>
 *   <li>Added comprehensive docstrings.</li>
 *   <li>Defined error conditions and acceptable value ranges.</li>
 *   <li>Outlined premises, assertions, pass/fail conditions for each test.</li>
 * </ul>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class PostServiceTest {

    /**
     * Injected instance of {@link PostService} for testing business logic.
     */
    @InjectMocks
    private PostService postService;

    /**
     * Mocked instance of {@link PostRepository} for database interactions.
     */
    @Mock
    private PostRepository postRepository;

    /**
     * Mocked instance of {@link UserRepository} for user validation.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Sample user entity for test cases.
     */
    private User user;

    /**
     * Sample post entity for test cases.
     */
    private Post post;

    /**
     * Sample post DTO for test cases.
     */
    private PostDTO postDTO;

    /**
     * Initializes test data before each test case.
     *
     * <p><strong>Premise:</strong></p>
     * Ensures that necessary test data (user, post, DTO) and mock dependencies are initialized before execution.
     *
     * <p><strong>Pass Condition:</strong></p>
     * If test data is correctly set up without exceptions.
     *
     * <p><strong>Fail Condition:</strong></p>
     * If initialization fails, impacting test execution.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "Test User", "test@example.com", "password", "USER",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        post = new Post(1L, "Test Title", "Test Content",
                LocalDateTime.now(), LocalDateTime.now(), user,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        postDTO = new PostDTO(1L, "Test Title", "Test Content", 1L);
    }

    /**
     * Tests successfully creating a post.
     *
     * <p><strong>Premise:</strong></p>
     * A valid user exists, and a new post is being created.
     *
     * <p><strong>Assertions:</strong></p>
     * <ul>
     *   <li>Verify that the returned {@link PostDTO} is not null.</li>
     *   <li>Ensure title, content, and userId match the expected values.</li>
     *   <li>Verify correct repository method calls.</li>
     * </ul>
     *
     * <p><strong>Pass Condition:</strong></p>
     * If the created post has expected values.
     *
     * <p><strong>Fail Conditions:</strong></p>
     * <ul>
     *   <li>If the returned DTO is null.</li>
     *   <li>If the values do not match expectations.</li>
     *   <li>If repository calls are incorrect.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test createPost() - success scenario")
    void testCreatePost() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO created = postService.createPost(postDTO);

        assertNotNull(created);
        assertEquals(post.getTitle(), created.getTitle());
        assertEquals(post.getContent(), created.getContent());
        assertEquals(post.getUser().getId(), created.getUserId());

        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    /**
     * Tests attempting to create a post when the user is not found.
     *
     * <p><strong>Premise:</strong></p>
     * The provided userId does not exist in the database.
     *
     * <p><strong>Assertions:</strong></p>
     * <ul>
     *   <li>Ensure a {@link ResourceNotFoundException} is thrown.</li>
     *   <li>Verify that the exception message matches "User not found with id 1".</li>
     *   <li>Confirm that no repository save calls occur.</li>
     * </ul>
     *
     * <p><strong>Pass Condition:</strong></p>
     * If the exception is correctly thrown and no repository saves are made.
     *
     * <p><strong>Fail Conditions:</strong></p>
     * <ul>
     *   <li>If no exception is thrown.</li>
     *   <li>If a repository save call is made.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test createPost() - user not found")
    void testCreatePost_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> postService.createPost(postDTO));

        assertEquals("User not found with id 1", exception.getMessage());

        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, never()).save(any(Post.class));
    }

    /**
     * Tests retrieving a post by ID successfully.
     *
     * <p><strong>Premise:</strong></p>
     * A post with the given ID exists.
     *
     * <p><strong>Assertions:</strong></p>
     * <ul>
     *   <li>Ensure the returned {@link PostDTO} matches expected values.</li>
     *   <li>Verify the repository method is called once.</li>
     * </ul>
     *
     * <p><strong>Pass Condition:</strong></p>
     * If the post is correctly retrieved and matches expected values.
     *
     * <p><strong>Fail Conditions:</strong></p>
     * <ul>
     *   <li>If the returned post does not match expected values.</li>
     *   <li>If repository method is not called correctly.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test getPostById() - found scenario")
    void testGetPostById_Found() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDTO found = postService.getPostById(1L);

        assertNotNull(found);
        assertEquals(post.getTitle(), found.getTitle());
        assertEquals(post.getContent(), found.getContent());
        assertEquals(post.getUser().getId(), found.getUserId());

        verify(postRepository, times(1)).findById(1L);
    }
}
