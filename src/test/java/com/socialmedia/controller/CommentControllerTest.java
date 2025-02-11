package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.dto.CommentDTO;
import com.socialmedia.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link CommentController}.
 * <p>
 * This class contains unit tests to verify the behavior of the {@link CommentController} endpoints,
 * including creating, retrieving, updating, and deleting comments. It utilizes MockMvc to simulate HTTP requests
 * and Mockito to mock service layer dependencies.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize a sample CommentDTO
        commentDTO = new CommentDTO(1L, "This is a comment", 1L, 1L);
    }

    @Test
    @DisplayName("USER can create a comment and receives 201")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateComment() throws Exception {
        when(commentService.createComment(any(CommentDTO.class))).thenReturn(commentDTO);

        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("This is a comment"))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.userId").value(1));

        verify(commentService, times(1)).createComment(any(CommentDTO.class));
    }

    @Test
    @DisplayName("USER can retrieve comments by post ID and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetCommentsByPostId() throws Exception {
        List<CommentDTO> comments = Arrays.asList(commentDTO);
        when(commentService.getCommentsByPostId(1L)).thenReturn(comments);

        mockMvc.perform(get("/api/comments/post/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].content").value("This is a comment"))
                .andExpect(jsonPath("$[0].postId").value(1))
                // Fixed JSON path here:
                .andExpect(jsonPath("$[0].userId").value(1));

        verify(commentService, times(1)).getCommentsByPostId(1L);
    }

    @Test
    @DisplayName("USER can update a comment and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUpdateComment() throws Exception {
        CommentDTO updatedCommentDTO = new CommentDTO(1L, "Updated comment", 1L, 1L);
        
        when(commentService.updateComment(eq(1L), any(CommentDTO.class))).thenReturn(updatedCommentDTO);

        mockMvc.perform(put("/api/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCommentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Updated comment"))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.userId").value(1));

        verify(commentService, times(1)).updateComment(eq(1L), any(CommentDTO.class));
    }

    @Test
    @DisplayName("USER can delete a comment and receives 204")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteComment() throws Exception {
        doNothing().when(commentService).deleteComment(1L);

        mockMvc.perform(delete("/api/comments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).deleteComment(1L);
    }
}
