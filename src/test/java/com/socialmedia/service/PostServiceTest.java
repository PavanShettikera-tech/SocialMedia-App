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
 * Test class for {@link PostService}.
 *
 * <p><strong>Premise:</strong>
 * Verifies the business logic for creating, retrieving, updating, and deleting posts.
 *
 * <p><strong>Error Conditions / Acceptable Values:</strong></p>
 * <ul>
 *   <li>Valid user must exist to create a post. If not, ResourceNotFoundException thrown.</li>
 *   <li>IDs must be valid or the service throws ResourceNotFoundException on retrieval/update/delete.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li>Pass: If we get the correct final PostDTO, or correct exceptions for invalid scenarios.</li>
 *   <li>Fail: If the logic does not call the right repository methods or fails to handle exceptions properly.</li>
 * </ul>
 */
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Post post;
    private PostDTO postDTO;

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

    @Test
    @DisplayName("Test getAllPosts() - returns list")
    void testGetAllPosts() {
        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        List<PostDTO> posts = postService.getAllPosts();

        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals(post.getTitle(), posts.get(0).getTitle());
        assertEquals(post.getContent(), posts.get(0).getContent());
        assertEquals(post.getUser().getId(), posts.get(0).getUserId());

        verify(postRepository, times(1)).findAll();
    }

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

    @Test
    @DisplayName("Test getPostById() - not found scenario")
    void testGetPostById_NotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(1L));

        assertEquals("Post not found with id 1", exception.getMessage());

        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test updatePost() - success scenario")
    void testUpdatePost() {
        PostDTO updatedDTO = new PostDTO(1L, "Updated Title", "Updated Content", 1L);
        Post updatedPost = new Post(1L, "Updated Title", "Updated Content",
                LocalDateTime.now(), LocalDateTime.now(), user,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        PostDTO result = postService.updatePost(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        assertEquals(1L, result.getUserId());

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Test updatePost() - post not found")
    void testUpdatePost_NotFound() {
        PostDTO updatedDTO = new PostDTO(1L, "Updated", "Updated", 1L);
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(1L, updatedDTO));

        assertEquals("Post not found with id 1", exception.getMessage());

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    @DisplayName("Test deletePost() - success scenario")
    void testDeletePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).delete(post);

        assertDoesNotThrow(() -> postService.deletePost(1L));

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    @DisplayName("Test deletePost() - post not found")
    void testDeletePost_NotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> postService.deletePost(1L));

        assertEquals("Post not found with id 1", exception.getMessage());

        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, never()).delete(any(Post.class));
    }
}
