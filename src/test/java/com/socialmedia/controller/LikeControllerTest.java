package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.dto.LikeDTO;
import com.socialmedia.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

/**
 * Test class for {@link LikeController}.
 * <p>
 * This class contains unit tests to verify the behavior of the {@link LikeController} endpoints,
 * including liking a post, retrieving the count of likes for a post, and unliking a post.
 * It utilizes {@link MockMvc} to simulate HTTP requests and Mockito to mock service layer dependencies.
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
@WebMvcTest(controllers = LikeController.class)
class LikeControllerTest {

    /**
     * The {@link MockMvc} instance used to perform HTTP requests in tests.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked instance of {@link LikeService} to simulate service layer behavior.
     */
    @MockBean
    private LikeService likeService;

    /**
     * The {@link ObjectMapper} used for serializing and deserializing JSON content.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sample {@link LikeDTO} used across multiple test cases.
     */
    private LikeDTO likeDTO;

    /**
     * Initializes test data and Mockito annotations before each test case.
     *
     * <p>
     * <strong>Premise:</strong>
     * Sets up a common {@link LikeDTO} instance and initializes Mockito annotations to prepare for mocking.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * Ensures that Mockito annotations are correctly initialized and the sample {@link LikeDTO} is properly set.
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
        // Initialize a sample LikeDTO with valid values
        likeDTO = new LikeDTO(1L, 1L, 1L);
    }

    /**
     * Tests that a user with the "USER" role can like a post successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * An authenticated user with the "USER" role attempts to like a post via the POST /api/likes endpoint.
     * </p>
     *
     * <p>
     * <strong>Test Steps:</strong>
     * <ol>
     *     <li>Mock the {@link LikeService#likePost(LikeDTO)} method to return a predefined {@link LikeDTO}.</li>
     *     <li>Perform a POST request to "/api/likes" with the sample {@link LikeDTO} as JSON content.</li>
     *     <li>Expect the HTTP status to be 201 (Created).</li>
     *     <li>Verify that the response JSON matches the sample {@link LikeDTO} fields.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 201 (Created).</li>
     *     <li>Response JSON contains correct "id", "postId", and "userId".</li>
     *     <li>{@link LikeService#likePost(LikeDTO)} is called exactly once with any {@link LikeDTO}.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * All assertions pass, indicating that a post is successfully liked and the service method is correctly invoked.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the HTTP status is not 201.</li>
     *     <li>If the response JSON does not match the expected values.</li>
     *     <li>If the service method is not invoked exactly once.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Error Conditions:</strong>
     * <ul>
     *     <li>Invalid JSON payload leading to a 400 Bad Request.</li>
     *     <li>Service layer exceptions resulting in 500 Internal Server Error.</li>
     * </ul>
     * </p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("USER can like a post and receives 201")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testLikePost() throws Exception {
        // Arrange: Mock the service layer to return the sample likeDTO when likePost is called
        when(likeService.likePost(any(LikeDTO.class))).thenReturn(likeDTO);

        // Act: Perform a POST request to like a post
        mockMvc.perform(post("/api/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(likeDTO)))
                // Assert: Expect HTTP 201 Created and verify the response content
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.userId").value(1));

        // Verify that the likePost method was called exactly once with any LikeDTO
        verify(likeService, times(1)).likePost(any(LikeDTO.class));
    }

    /**
     * Tests that a user with the "USER" role can retrieve the count of likes for a specific post successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * An authenticated user with the "USER" role requests the number of likes for a specific post via the GET /api/likes/post/{postId} endpoint.
     * </p>
     *
     * <p>
     * <strong>Test Steps:</strong>
     * <ol>
     *     <li>Mock the {@link LikeService#getLikesCount(Long)} method to return a predefined likes count.</li>
     *     <li>Perform a GET request to "/api/likes/post/1".</li>
     *     <li>Expect the HTTP status to be 200 (OK).</li>
     *     <li>Verify that the response content matches the predefined likes count.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response content matches the expected likes count.</li>
     *     <li>{@link LikeService#getLikesCount(Long)} is called exactly once with postId=1.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * All assertions pass, indicating that the likes count is successfully retrieved for the specified post.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the HTTP status is not 200.</li>
     *     <li>If the response content does not match the expected likes count.</li>
     *     <li>If the service method is not invoked exactly once with the correct postId.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Error Conditions:</strong>
     * <ul>
     *     <li>If the postId does not exist, resulting in a 404 Not Found or a likes count of 0.</li>
     *     <li>Service layer exceptions leading to 500 Internal Server Error.</li>
     * </ul>
     * </p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("USER can retrieve likes count for a post and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetLikesCount() throws Exception {
        // Arrange: Mock the service layer to return a predefined likes count
        Long likesCount = 10L;
        when(likeService.getLikesCount(1L)).thenReturn(likesCount);

        // Act: Perform a GET request to retrieve likes count for postId=1
        mockMvc.perform(get("/api/likes/post/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Assert: Expect HTTP 200 OK and verify the response content
                .andExpect(status().isOk())
                .andExpect(content().string(likesCount.toString()));

        // Verify that the getLikesCount method was called exactly once with postId=1
        verify(likeService, times(1)).getLikesCount(1L);
    }

    /**
     * Tests that a user with the "USER" role can unlike a post successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * An authenticated user with the "USER" role attempts to unlike a post via the DELETE /api/likes/{likeId} endpoint.
     * </p>
     *
     * <p>
     * <strong>Test Steps:</strong>
     * <ol>
     *     <li>Mock the {@link LikeService#unlikePost(Long)} method to perform no action when called with a specific likeId.</li>
     *     <li>Perform a DELETE request to "/api/likes/1".</li>
     *     <li>Expect the HTTP status to be 204 (No Content).</li>
     *     <li>Verify that the {@link LikeService#unlikePost(Long)} method is invoked exactly once with likeId=1.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 204 (No Content).</li>
     *     <li>{@link LikeService#unlikePost(Long)} is called exactly once with likeId=1.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * All assertions pass, indicating that the post is successfully unliked and the service method is correctly invoked.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the HTTP status is not 204.</li>
     *     <li>If the service method is not invoked exactly once with the correct likeId.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Error Conditions:</strong>
     * <ul>
     *     <li>If the likeId does not exist, resulting in a 404 Not Found.</li>
     *     <li>Service layer exceptions causing a 500 Internal Server Error.</li>
     * </ul>
     * </p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("USER can unlike a post and receives 204")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUnlikePost() throws Exception {
        // Arrange: Mock the service layer to do nothing when unlikePost is called with likeId=1
        doNothing().when(likeService).unlikePost(1L);

        // Act: Perform a DELETE request to unlike the post
        mockMvc.perform(delete("/api/likes/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Assert: Expect HTTP 204 No Content
                .andExpect(status().isNoContent());

        // Verify that the unlikePost method was called exactly once with likeId=1
        verify(likeService, times(1)).unlikePost(1L);
    }
}
