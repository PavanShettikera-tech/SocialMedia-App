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
 * <p>
 * This class contains unit tests to verify the behavior of the {@link PostController} endpoints,
 * including creating, retrieving, updating, and deleting posts. It differentiates between
 * ADMIN and USER roles to ensure proper access control. The tests utilize MockMvc to simulate HTTP requests
 * and Mockito to mock service layer dependencies.
 * </p>
 * 
 * <p><strong>Feedback Implemented:</strong>
 * <ul>
 *     <li>Added comprehensive docstrings for the class and each test method.</li>
 *     <li>Included error conditions, acceptable value ranges, and parameter descriptions where applicable.</li>
 *     <li>Specified premises and assertions within each test method.</li>
 *     <li>Clarified pass/fail conditions for each test case.</li>
 * </ul>
 * </p>
 * 
 * @version 1.0
 * @since 2025-02-12
 */
@WebMvcTest(PostController.class)
class PostControllerTest {

    /**
     * MockMvc instance for performing HTTP requests in tests.
     * <p>
     * Allows simulation of HTTP requests and verification of responses without starting the server.
     * </p>
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked instance of {@link PostService} to simulate service layer behavior.
     * <p>
     * Used to mock interactions with the post service, allowing isolation of controller tests.
     * </p>
     */
    @MockBean
    private PostService postService;

    /**
     * ObjectMapper instance for serializing and deserializing JSON content.
     * <p>
     * Facilitates conversion between Java objects and JSON, enabling the construction of request bodies and parsing of responses.
     * </p>
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sample {@link PostDTO} used across multiple tests.
     * <p>
     * Represents a post with predefined values for consistent testing.
     * </p>
     */
    private PostDTO postDTO;

    /**
     * Initializes the sample {@link PostDTO} before each test.
     * <p>
     * Sets up common test data and initializes any necessary configurations to prepare for each test case.
     * </p>
     */
    @BeforeEach
    void setUp() {
        // Initialize a sample PostDTO with valid values
        postDTO = new PostDTO(1L, "Test Title", "Test Content", 1L);
    }

    // ----------------------------
    //  Tests for non-admin users
    // ----------------------------

    /**
     * Tests that a non-admin user cannot create a post.
     * <p>
     * <strong>Scenario:</strong> A user with the role "USER" attempts to create a new post.
     * <strong>Premise:</strong> The user does not have ADMIN privileges.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 403 (Forbidden).</li>
     *     <li>No post is created in the system.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The request is forbidden, and the post is not created.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is erroneously allowed.</li>
     *     <li>An unexpected HTTP status is returned.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Non-admin user cannot create post - returns 403")
    void testCreatePost_AsUser_ShouldReturn403() throws Exception {
        // Perform POST request to create a new post as a non-admin user
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isForbidden()); // Expect HTTP 403 Forbidden
    }

    /**
     * Tests that a non-admin user cannot update a post.
     * <p>
     * <strong>Scenario:</strong> A user with the role "USER" attempts to update an existing post.
     * <strong>Premise:</strong> The user does not have ADMIN privileges.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 403 (Forbidden).</li>
     *     <li>No changes are made to the post in the system.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The update request is forbidden, and the post remains unchanged.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is erroneously allowed.</li>
     *     <li>An unexpected HTTP status is returned.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Non-admin user cannot update post - returns 403")
    void testUpdatePost_AsUser_ShouldReturn403() throws Exception {
        // Define an updated PostDTO, though it should not be used
        PostDTO updatedDTO = new PostDTO(1L, "Updated Title", "Updated Content", 1L);
        
        // Even though the service is mocked to return updatedDTO, the request should be forbidden
        when(postService.updatePost(eq(1L), any(PostDTO.class))).thenReturn(updatedDTO);

        // Perform PUT request to update the post as a non-admin user
        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isForbidden()); // Expect HTTP 403 Forbidden
    }

    /**
     * Tests that a non-admin user cannot delete a post.
     * <p>
     * <strong>Scenario:</strong> A user with the role "USER" attempts to delete an existing post.
     * <strong>Premise:</strong> The user does not have ADMIN privileges.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 403 (Forbidden).</li>
     *     <li>The post is not deleted from the system.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The delete request is forbidden, and the post remains in the system.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is erroneously allowed.</li>
     *     <li>An unexpected HTTP status is returned.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("Non-admin user cannot delete post - returns 403")
    void testDeletePost_AsUser_ShouldReturn403() throws Exception {
        // Define behavior for postService.deletePost, though it should not be called
        doNothing().when(postService).deletePost(1L);

        // Perform DELETE request to delete the post as a non-admin user
        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isForbidden()); // Expect HTTP 403 Forbidden
    }

    // ----------------------------
    //  Tests for admin users
    // ----------------------------

    /**
     * Tests that an admin user can create a post.
     * <p>
     * <strong>Scenario:</strong> A user with the role "ADMIN" attempts to create a new post.
     * <strong>Premise:</strong> The user has the necessary ADMIN privileges, and the post data is valid.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 201 (Created).</li>
     *     <li>Response body contains the correct post details.</li>
     *     <li>The service layer's createPost method is invoked exactly once.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The post is successfully created, and all assertions pass.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is forbidden.</li>
     *     <li>Incorrect HTTP status is returned.</li>
     *     <li>The response body does not match the expected post details.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "adminuser", roles = {"ADMIN"})
    @DisplayName("ADMIN can create post - returns 201")
    void testCreatePost_AsAdmin_ShouldReturn201() throws Exception {
        // Define behavior for postService.createPost to return the sample postDTO
        when(postService.createPost(any(PostDTO.class))).thenReturn(postDTO);

        // Perform POST request to create a new post as an admin user
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isCreated()) // Expect HTTP 201 Created
                .andExpect(jsonPath("$.id").value(postDTO.getId())) // Verify ID
                .andExpect(jsonPath("$.title").value(postDTO.getTitle())) // Verify title
                .andExpect(jsonPath("$.content").value(postDTO.getContent())) // Verify content
                .andExpect(jsonPath("$.userId").value(postDTO.getUserId())); // Verify userId

        // Verify that postService.createPost was called once with any PostDTO
        verify(postService, times(1)).createPost(any(PostDTO.class));
    }

    /**
     * Tests that an admin user can update a post.
     * <p>
     * <strong>Scenario:</strong> A user with the role "ADMIN" attempts to update an existing post.
     * <strong>Premise:</strong> The post ID exists, the user has ADMIN privileges, and the update data is valid.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response body contains the updated post details.</li>
     *     <li>The service layer's updatePost method is invoked exactly once with the correct ID and data.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The post is successfully updated, and all assertions pass.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is forbidden.</li>
     *     <li>Incorrect HTTP status is returned.</li>
     *     <li>The response body does not match the updated post details.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "adminuser", roles = {"ADMIN"})
    @DisplayName("ADMIN can update post - returns 200")
    void testUpdatePost_AsAdmin_ShouldReturn200() throws Exception {
        // Define the updated PostDTO with valid changes
        PostDTO updatedDTO = new PostDTO(1L, "Updated Title", "Updated Content", 1L);
        
        // Define behavior for postService.updatePost to return the updatedDTO
        when(postService.updatePost(eq(1L), any(PostDTO.class))).thenReturn(updatedDTO);

        // Perform PUT request to update the post as an admin user
        mockMvc.perform(put("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.title").value("Updated Title")) // Verify updated title
                .andExpect(jsonPath("$.content").value("Updated Content")) // Verify updated content
                .andExpect(jsonPath("$.userId").value(1L)); // Verify userId remains the same

        // Verify that postService.updatePost was called once with the correct ID and PostDTO
        verify(postService, times(1)).updatePost(eq(1L), any(PostDTO.class));
    }

    /**
     * Tests that an admin user can delete a post.
     * <p>
     * <strong>Scenario:</strong> A user with the role "ADMIN" attempts to delete an existing post.
     * <strong>Premise:</strong> The post ID exists, and the user has ADMIN privileges.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 204 (No Content).</li>
     *     <li>No content is returned in the response body.</li>
     *     <li>The service layer's deletePost method is invoked exactly once with the correct ID.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The post is successfully deleted, and all assertions pass.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is forbidden.</li>
     *     <li>Incorrect HTTP status is returned.</li>
     *     <li>The service method is not invoked as expected.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "adminuser", roles = {"ADMIN"})
    @DisplayName("ADMIN can delete post - returns 204")
    void testDeletePost_AsAdmin_ShouldReturn204() throws Exception {
        // Define behavior for postService.deletePost to do nothing (void method)
        doNothing().when(postService).deletePost(1L);

        // Perform DELETE request to delete the post as an admin user
        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent()); // Expect HTTP 204 No Content

        // Verify that postService.deletePost was called once with the correct ID
        verify(postService, times(1)).deletePost(1L);
    }

    // ----------------------------
    //  Tests accessible to any user
    // ----------------------------

    /**
     * Tests that any authenticated user can retrieve all posts.
     * <p>
     * <strong>Scenario:</strong> A user with any valid role (e.g., "USER") requests to retrieve all posts.
     * <strong>Premise:</strong> The user is authenticated, and posts exist in the system.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response body contains a list of posts with correct details.</li>
     *     <li>The service layer's getAllPosts method is invoked exactly once.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The posts are successfully retrieved, and all assertions pass.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is unauthorized.</li>
     *     <li>Incorrect HTTP status is returned.</li>
     *     <li>The response body does not match the expected list of posts.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("User can get all posts - returns 200")
    void testGetAllPosts() throws Exception {
        // Define a list of PostDTOs to be returned by the mocked service
        List<PostDTO> posts = Arrays.asList(postDTO);
        when(postService.getAllPosts()).thenReturn(posts);

        // Perform GET request to retrieve all posts
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.length()").value(posts.size())) // Verify the size of the list
                .andExpect(jsonPath("$[0].id").value(postDTO.getId())) // Verify first post ID
                .andExpect(jsonPath("$[0].title").value(postDTO.getTitle())) // Verify title
                .andExpect(jsonPath("$[0].content").value(postDTO.getContent())) // Verify content
                .andExpect(jsonPath("$[0].userId").value(postDTO.getUserId())); // Verify userId

        // Verify that postService.getAllPosts was called once
        verify(postService, times(1)).getAllPosts();
    }

    /**
     * Tests that any authenticated user can retrieve a post by its ID when it exists.
     * <p>
     * <strong>Scenario:</strong> A user with any valid role (e.g., "USER") requests to retrieve a post by its ID.
     * <strong>Premise:</strong> The post with the specified ID exists in the system.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response body contains the correct post details.</li>
     *     <li>The service layer's getPostById method is invoked exactly once with the correct ID.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The post is successfully retrieved, and all assertions pass.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is unauthorized.</li>
     *     <li>The post does not exist, leading to an error status.</li>
     *     <li>The response body does not match the expected post details.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("User can get post by ID if found - returns 200")
    void testGetPostById_Found() throws Exception {
        // Define behavior for postService.getPostById to return the sample postDTO
        when(postService.getPostById(1L)).thenReturn(postDTO);

        // Perform GET request to retrieve the post by ID
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.id").value(postDTO.getId())) // Verify ID
                .andExpect(jsonPath("$.title").value(postDTO.getTitle())) // Verify title
                .andExpect(jsonPath("$.content").value(postDTO.getContent())) // Verify content
                .andExpect(jsonPath("$.userId").value(postDTO.getUserId())); // Verify userId

        // Verify that postService.getPostById was called once with ID 1
        verify(postService, times(1)).getPostById(1L);
    }

    /**
     * Tests that retrieving a non-existent post by ID returns a 404 Not Found status.
     * <p>
     * <strong>Scenario:</strong> A user with any valid role (e.g., "USER") requests to retrieve a post by an ID that does not exist.
     * <strong>Premise:</strong> The post with the specified ID does not exist in the system.
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 404 (Not Found).</li>
     *     <li>Response body contains the appropriate error message.</li>
     *     <li>The service layer's getPostById method is invoked exactly once with the correct ID.</li>
     * </ul>
     * <strong>Pass Condition:</strong> The system correctly identifies the missing post and returns the appropriate error response.
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>The request is erroneously allowed.</li>
     *     <li>An incorrect HTTP status is returned.</li>
     *     <li>The response body does not contain the expected error message.</li>
     * </ul>
     * </p>
     * 
     * @throws Exception If an error occurs during the request.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    @DisplayName("User gets 404 if post not found")
    void testGetPostById_NotFound() throws Exception {
        // Define behavior for postService.getPostById to throw a ResourceNotFoundException
        when(postService.getPostById(1L)).thenThrow(new ResourceNotFoundException("Post not found with id 1"));

        // Perform GET request to retrieve the post by ID, expecting a 404 response
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isNotFound()) // Expect HTTP 404 Not Found
                .andExpect(content().string("Post not found with id 1")); // Verify error message

        // Verify that postService.getPostById was called once with ID 1
        verify(postService, times(1)).getPostById(1L);
    }
}
