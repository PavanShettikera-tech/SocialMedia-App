package com.socialmedia.service;

import com.socialmedia.dto.CommentDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Comment;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CommentService}.
 * <p>
 * This class tests the business logic of {@link CommentService}, ensuring that comments can be created, retrieved,
 * updated, and deleted correctly while handling error scenarios properly.
 * </p>
 * 
 * <p>
 * <strong>Feedback Implemented:</strong>
 * <ul>
 *     <li>Added comprehensive docstrings.</li>
 *     <li>Included error conditions and acceptable value ranges.</li>
 *     <li>Detailed parameter names, types, ranges, and descriptions.</li>
 *     <li>Outlined premises and assertions for each test.</li>
 *     <li>Clarified pass/fail conditions for assertions.</li>
 * </ul>
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class CommentServiceTest {

    /**
     * Mocked instance of {@link CommentRepository} for database operations.
     */
    @Mock
    private CommentRepository commentRepository;

    /**
     * Mocked instance of {@link PostRepository} to validate post existence.
     */
    @Mock
    private PostRepository postRepository;

    /**
     * Mocked instance of {@link UserRepository} to validate user existence.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Injected instance of {@link CommentService} to test business logic.
     */
    @InjectMocks
    private CommentService commentService;

    /**
     * Sample {@link CommentDTO} used across multiple test cases.
     */
    private CommentDTO commentDTO;

    /**
     * Sample {@link Comment} entity for repository interactions.
     */
    private Comment comment;

    /**
     * Sample {@link Post} entity for post validation.
     */
    private Post post;

    /**
     * Sample {@link User} entity for user validation.
     */
    private User user;

    /**
     * Initializes test data and Mockito annotations before each test case.
     *
     * <p>
     * <strong>Premise:</strong>
     * Sets up a common {@link CommentDTO} instance and initializes necessary mock dependencies.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * Ensures that Mockito annotations are correctly initialized and test data is properly set.
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The setup completes without exceptions, indicating that test data is correctly initialized.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * Any exception during setup indicates improper initialization of test data or Mockito.
     * </p>
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentDTO = new CommentDTO(1L, "Test comment", 1L, 1L);
        post = new Post();
        post.setId(1L);
        user = new User();
        user.setId(1L);
        comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test comment");
        comment.setPost(post);
        comment.setUser(user);
    }

    /**
     * Tests successfully creating a comment.
     *
     * <p>
     * <strong>Premise:</strong>
     * A valid post and user exist, and a new comment is being created.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Ensure the returned {@link CommentDTO} is not null.</li>
     *     <li>Verify that the content matches the expected value.</li>
     *     <li>Check that the associated postId and userId are correct.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The created comment contains the expected values.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the returned {@link CommentDTO} is null.</li>
     *     <li>If the content does not match "Test comment".</li>
     *     <li>If the postId or userId does not match 1L.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test creating a comment successfully")
    void testCreateComment() {
        when(postRepository.findById(commentDTO.getPostId())).thenReturn(Optional.of(post));
        when(userRepository.findById(commentDTO.getUserId())).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO result = commentService.createComment(commentDTO);

        assertNotNull(result);
        assertEquals("Test comment", result.getContent());
        assertEquals(1L, result.getPostId());
        assertEquals(1L, result.getUserId());
    }

    /**
     * Tests attempting to create a comment when the post is not found.
     *
     * <p>
     * <strong>Premise:</strong>
     * The provided postId does not exist in the database.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Ensure a {@link ResourceNotFoundException} is thrown.</li>
     *     <li>Verify that the exception message matches "Post not found with id 1".</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The exception is thrown with the correct message.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If no exception is thrown.</li>
     *     <li>If the exception message does not match "Post not found with id 1".</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test creating a comment when post is not found")
    void testCreateComment_PostNotFound() {
        when(postRepository.findById(commentDTO.getPostId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commentService.createComment(commentDTO)
        );

        assertEquals("Post not found with id 1", exception.getMessage());
    }

    /**
     * Tests attempting to create a comment when the user is not found.
     *
     * <p>
     * <strong>Premise:</strong>
     * The provided userId does not exist in the database.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Ensure a {@link ResourceNotFoundException} is thrown.</li>
     *     <li>Verify that the exception message matches "User not found with id 1".</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The exception is thrown with the correct message.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If no exception is thrown.</li>
     *     <li>If the exception message does not match "User not found with id 1".</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test creating a comment when user is not found")
    void testCreateComment_UserNotFound() {
        when(postRepository.findById(commentDTO.getPostId())).thenReturn(Optional.of(post));
        when(userRepository.findById(commentDTO.getUserId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commentService.createComment(commentDTO)
        );

        assertEquals("User not found with id 1", exception.getMessage());
    }
}
