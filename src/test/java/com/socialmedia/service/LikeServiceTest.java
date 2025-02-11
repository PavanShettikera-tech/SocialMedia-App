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
 * This class contains unit tests to verify the correctness of the {@link LikeService} class,
 * including liking a post, unliking a post, and retrieving like counts. It utilizes Mockito
 * to mock dependencies and JUnit 5 for testing.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test liking a post with valid input")
    void likePost_ValidInput_ReturnsLikeDTO() {
        LikeDTO inputDTO = new LikeDTO(null, 1L, 1L);

        Post mockPost = new Post();
        mockPost.setId(1L);

        User mockUser = new User();
        mockUser.setId(1L);

        when(likeRepository.save(any(Like.class))).thenAnswer(inv -> {
            Like argLike = inv.getArgument(0);
            argLike.setId(1L);
            return argLike;
        });
        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        LikeDTO result = likeService.likePost(inputDTO);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getPostId());
        assertEquals(1L, result.getUserId());

        verify(postRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(likeRepository, times(1)).save(any(Like.class));
    }

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

    @Test
    @DisplayName("Test unliking a post with a non-existing like ID")
    void unlikePost_NonExistingId_ThrowsException() {
        Long likeId = 99L;
        when(likeRepository.findById(likeId)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> likeService.unlikePost(likeId)
        );

        assertEquals("Like not found with id 99", ex.getMessage());

        verify(likeRepository, times(1)).findById(likeId);
        verify(likeRepository, never()).delete(any(Like.class));
    }

    @Test
    @DisplayName("Test mapping LikeDTO to Like entity when post is not found")
    void mapToEntity_PostNotFound_ThrowsException() {
        LikeDTO inputDTO = new LikeDTO(null, 1L, 1L);
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> likeService.likePost(inputDTO)
        );

        assertEquals("Post not found with id 1", ex.getMessage());
        verify(postRepository, times(1)).findById(1L);
        verify(userRepository, never()).findById(anyLong());
        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    @DisplayName("Test mapping LikeDTO to Like entity when user is not found")
    void mapToEntity_UserNotFound_ThrowsException() {
        LikeDTO inputDTO = new LikeDTO(null, 1L, 1L);
        Post mockPost = new Post();
        mockPost.setId(1L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(mockPost));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> likeService.likePost(inputDTO)
        );

        assertEquals("User not found with id 1", ex.getMessage());
        verify(postRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(likeRepository, never()).save(any(Like.class));
    }

    @Test
    @DisplayName("Test mapping Like entity to LikeDTO")
    void mapToDTO_ValidEntity_ReturnsCorrectDTO() {
        Post post = new Post();
        post.setId(1L);

        User user = new User();
        user.setId(1L);

        Like like = new Like();
        like.setId(1L);
        like.setPost(post);
        like.setUser(user);

        // Now that we have mapToDTO(...) method, the call should compile:
        LikeDTO dto = likeService.mapToDTO(like);

        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getPostId());
        assertEquals(1L, dto.getUserId());
    }
}
