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

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    private CommentDTO commentDTO;
    private Comment comment;
    private Post post;
    private User user;

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

    @Test
    @DisplayName("Test creating a comment when post is not found")
    void testCreateComment_PostNotFound() {
        when(postRepository.findById(commentDTO.getPostId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commentService.createComment(commentDTO)
        );

        // Must match "Post not found with id 1" (no colon)
        assertEquals("Post not found with id 1", exception.getMessage());
    }

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

    @Test
    @DisplayName("Test retrieving comments by post ID")
    void testGetCommentsByPostId() {
        when(commentRepository.findByPostId(1L)).thenReturn(Collections.singletonList(comment));
        List<CommentDTO> result = commentService.getCommentsByPostId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test comment", result.get(0).getContent());
    }

    @Test
    @DisplayName("Test updating a comment successfully")
    void testUpdateComment() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO result = commentService.updateComment(1L, commentDTO);

        assertNotNull(result);
        assertEquals("Test comment", result.getContent());
    }

    @Test
    @DisplayName("Test updating a comment that does not exist")
    void testUpdateComment_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commentService.updateComment(1L, commentDTO)
        );

        // Must match "Comment not found with id 1"
        assertEquals("Comment not found with id 1", exception.getMessage());
    }

    @Test
    @DisplayName("Test deleting a comment successfully")
    void testDeleteComment() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).delete(comment);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    @DisplayName("Test deleting a comment that does not exist")
    void testDeleteComment_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> commentService.deleteComment(1L)
        );

        // Must match "Comment not found with id 1"
        assertEquals("Comment not found with id 1", exception.getMessage());
    }
}
