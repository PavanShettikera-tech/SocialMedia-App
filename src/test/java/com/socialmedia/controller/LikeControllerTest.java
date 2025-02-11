package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.dto.LikeDTO;
import com.socialmedia.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // <-- IMPORTANT
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LikeController.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @Autowired
    private ObjectMapper objectMapper;

    private LikeDTO likeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        likeDTO = new LikeDTO(1L, 1L, 1L);
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testLikePost() throws Exception {
        when(likeService.likePost(any(LikeDTO.class))).thenReturn(likeDTO);

        mockMvc.perform(post("/api/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(likeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetLikesCount() throws Exception {
        Long likesCount = 10L;
        when(likeService.getLikesCount(1L)).thenReturn(likesCount);

        mockMvc.perform(get("/api/likes/post/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(likesCount.toString()));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUnlikePost() throws Exception {
        doNothing().when(likeService).unlikePost(1L);

        mockMvc.perform(delete("/api/likes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(likeService, times(1)).unlikePost(1L);
    }
}
