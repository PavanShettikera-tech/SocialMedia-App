package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.dto.PostDTO;
import com.socialmedia.exception.ResourceNotFoundException;
import com.socialmedia.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link PostController}.
 *
 * <p>Ensures that Post CRUD endpoints function correctly,
 * verifying constraints like ADMIN vs USER roles.</p>
 */
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostDTO postDTO;

    @BeforeEach
    void setUp() {
        // A sample post object used across tests
        postDTO = new PostDTO(1L, "Test Title", "Test Content", 1L);
    }

    // ----------------------------
    //  Tests for non-admin users
    // ----------------------------

    /**
     * Non-admin user tries to create post => should get 403 Forbidden.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Non-admin user cannot create post - returns 403")
    void testCreatePost_AsUser_ShouldReturn403() throws Exception {
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isForbidden());
    }

    /**
     * Non-admin user tries to update post => should get 403 Forbidden.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Non-admin user cannot update post - returns 403")
    void testUpdatePost_AsUser_ShouldReturn403() throws Exception {
        PostDTO updatedDTO = new PostDTO(1L, "Updated Title", "Updated Content", 1L);
        when(postService.updatePost(eq(1L), any(PostDTO.class))).thenReturn(updatedDTO);

        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isForbidden());
    }

    /**
     * Non-admin user tries to delete post => should get 403 Forbidden.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Non-admin user cannot delete post - returns 403")
    void testDeletePost_AsUser_ShouldReturn403() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isForbidden());
    }

    // ----------------------------
    //  Tests for admin users
    // ----------------------------

    /**
     * Admin user can create post => should get 201 Created.
     */
    @Test
    @WithMockUser(username = "adminuser", roles = {"ADMIN"})
    @DisplayName("ADMIN can create post - returns 201")
    void testCreatePost_AsAdmin_ShouldReturn201() throws Exception {
        when(postService.createPost(any(PostDTO.class))).thenReturn(postDTO);

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(postDTO.getId()))
                .andExpect(jsonPath("$.title").value(postDTO.getTitle()))
                .andExpect(jsonPath("$.content").value(postDTO.getContent()))
                .andExpect(jsonPath("$.userId").value(postDTO.getUserId()));
    }

    /**
     * Admin user can update post => should get 200 OK.
     */
    @Test
    @WithMockUser(username = "adminuser", roles = {"ADMIN"})
    @DisplayName("ADMIN can update post - returns 200")
    void testUpdatePost_AsAdmin_ShouldReturn200() throws Exception {
        PostDTO updatedDTO = new PostDTO(1L, "Updated Title", "Updated Content", 1L);
        when(postService.updatePost(eq(1L), any(PostDTO.class))).thenReturn(updatedDTO);

        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"))
                .andExpect(jsonPath("$.userId").value(1L));
    }

    /**
     * Admin user can delete post => should get 204 No Content.
     */
    @Test
    @WithMockUser(username = "adminuser", roles = {"ADMIN"})
    @DisplayName("ADMIN can delete post - returns 204")
    void testDeletePost_AsAdmin_ShouldReturn204() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());
    }

    // ----------------------------
    //  Tests accessible to any user
    // ----------------------------

    /**
     * Any authenticated user can retrieve all posts => 200 OK.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("User can get all posts - returns 200")
    void testGetAllPosts() throws Exception {
        List<PostDTO> posts = Arrays.asList(postDTO);
        when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(posts.size()))
                .andExpect(jsonPath("$[0].id").value(postDTO.getId()))
                .andExpect(jsonPath("$[0].title").value(postDTO.getTitle()))
                .andExpect(jsonPath("$[0].content").value(postDTO.getContent()))
                .andExpect(jsonPath("$[0].userId").value(postDTO.getUserId()));
    }

    /**
     * Any authenticated user can retrieve a post by ID => 200 if found.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("User can get post by ID if found - returns 200")
    void testGetPostById_Found() throws Exception {
        when(postService.getPostById(1L)).thenReturn(postDTO);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postDTO.getId()))
                .andExpect(jsonPath("$.title").value(postDTO.getTitle()))
                .andExpect(jsonPath("$.content").value(postDTO.getContent()))
                .andExpect(jsonPath("$.userId").value(postDTO.getUserId()));
    }

    /**
     * If post not found => 404 Not Found with error message.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("User gets 404 if post not found")
    void testGetPostById_NotFound() throws Exception {
        when(postService.getPostById(1L)).thenThrow(new ResourceNotFoundException("Post not found with id 1"));

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Post not found with id 1"));
    }
}
