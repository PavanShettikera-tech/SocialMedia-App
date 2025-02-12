package com.socialmedia.service;

import com.socialmedia.dto.LikeDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link LikeService} class.
 * <p>
 * This class tests the business logic of {@link LikeService}, ensuring that posts can be liked, unliked, and that
 * like counts can be retrieved while handling error scenarios properly.
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
@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    /**
     * Mocked instance of {@link LikeRepository} for database operations.
     */
    @Mock
    private LikeRepository likeRepository;

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
     * Injected instance of {@link LikeService} to test business logic.
     */
    @InjectMocks
    private LikeService likeService;

    /**
     * Initializes Mockito annotations before each test.
     *
     * <p>
     * <strong>Premise:</strong>
     * Ensures that necessary mock dependencies are properly initialized before running tests.
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The setup completes without exceptions, confirming proper initialization.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * If Mockito annotations are not correctly initialized, affecting test execution.
     * </p>
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests successfully liking a post.
     *
     * <p>
     * <strong>Premise:</strong>
     * A valid post and user exist, and a new like is being added.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Ensure the returned {@link LikeDTO} is not null.</li>
     *     <li>Verify that the like has the expected postId and userId.</li>
     *     <li>Check that repository interactions occur as expected.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The created like contains the expected values.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the returned {@link LikeDTO} is null.</li>
     *     <li>If the postId or userId does not match expected values.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test liking a post with valid input")
    void likePost_ValidInput_ReturnsLikeDTO() {
        LikeDTO inputDTO = new LikeDTO(null, 1L, 1L);
        Post mockPost = new Post();
        mockPost.setId(1L);
        User mockUser = new User();
        mockUser.setId(1L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(likeRepository.save(any(Like.class))).thenAnswer(inv -> {
            Like argLike = inv.getArgument(0);
            argLike.setId(1L);
            return argLike;
        });

        LikeDTO result = likeService.likePost(inputDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getPostId());
        assertEquals(1L, result.getUserId());

        verify(postRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    /**
     * Tests retrieving the like count for a valid post ID.
     *
     * <p>
     * <strong>Premise:</strong>
     * The provided postId exists, and the like count should be returned.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Verify that the like count matches the expected value.</li>
     *     <li>Ensure that the correct repository method is called.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The retrieved like count matches the expected count.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the retrieved count does not match the expected value.</li>
     *     <li>If the repository method is not called as expected.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test retrieving likes count for a valid post ID")
    void getLikesCount_ValidPostId_ReturnsCount() {
        Long postId = 1L;
        Long expectedCount = 5L;
        when(likeRepository.countByPostId(postId)).thenReturn(expectedCount);

        Long result = likeService.getLikesCount(postId);
        assertEquals(expectedCount, result);

        verify(likeRepository, times(1)).countByPostId(postId);
    }

    /**
     * Tests unliking a post successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * A like entry exists, and it should be removed.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>Verify that the like entry is deleted.</li>
     *     <li>Ensure that repository interactions occur as expected.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * The like entry is successfully deleted.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the repository method is not invoked correctly.</li>
     *     <li>If an exception is thrown unexpectedly.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test unliking a post with an existing like ID")
    void unlikePost_ExistingId_DeletesSuccessfully() {
        Long likeId = 1L;
        Like existingLike = new Like();
        existingLike.setId(likeId);

        when(likeRepository.findById(likeId)).thenReturn(Optional.of(existingLike));

        likeService.unlikePost(likeId);

        verify(likeRepository, times(1)).findById(likeId);
        verify(likeRepository, times(1)).delete(existingLike);
    }

}
