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
 * including creating, retrieving, updating, and deleting comments. It utilizes {@link MockMvc} to simulate HTTP requests
 * and Mockito to mock service layer dependencies.
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
@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    /**
     * The {@link MockMvc} instance used to perform HTTP requests in tests.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked instance of {@link CommentService} to simulate service layer behavior.
     */
    @MockBean
    private CommentService commentService;

    /**
     * The {@link ObjectMapper} used for serializing and deserializing JSON content.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sample {@link CommentDTO} used across multiple test cases.
     */
    private CommentDTO commentDTO;

    /**
     * Initializes test data and Mockito annotations before each test case.
     *
     * <p>
     * <strong>Premise:</strong>
     * Sets up a common {@link CommentDTO} instance and initializes Mockito annotations to prepare for mocking.
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * Ensures that Mockito annotations are correctly initialized and the sample {@link CommentDTO} is properly set.
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
        // Initialize a sample CommentDTO with valid values
        commentDTO = new CommentDTO(1L, "This is a comment", 1L, 1L);
    }

    /**
     * Tests that a user with the "USER" role can create a comment successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * A user authenticated with the "USER" role attempts to create a new comment via the POST /api/comments endpoint.
     * </p>
     *
     * <p>
     * <strong>Test Steps:</strong>
     * <ol>
     *     <li>Mock the {@link CommentService#createComment(CommentDTO)} method to return a predefined {@link CommentDTO}.</li>
     *     <li>Perform a POST request to "/api/comments" with the sample {@link CommentDTO} as JSON content.</li>
     *     <li>Expect the HTTP status to be 201 (Created).</li>
     *     <li>Verify that the response JSON matches the sample {@link CommentDTO} fields.</li>
     *     <li>Ensure that {@link CommentService#createComment(CommentDTO)} is invoked exactly once.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 201 (Created).</li>
     *     <li>Response JSON contains correct "id", "content", "postId", and "userId".</li>
     *     <li>{@link CommentService#createComment(CommentDTO)} is called once with any {@link CommentDTO}.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * All assertions pass, indicating that a comment is successfully created and the service method is correctly invoked.
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
    @DisplayName("USER can create a comment and receives 201")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testCreateComment() throws Exception {
        // Arrange: Mock the service layer to return the sample commentDTO when createComment is called
        when(commentService.createComment(any(CommentDTO.class))).thenReturn(commentDTO);

        // Act: Perform a POST request to create a new comment
        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO)))
                // Assert: Expect HTTP 201 Created and verify the response content
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("This is a comment"))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.userId").value(1));

        // Verify that the createComment method was called exactly once with any CommentDTO
        verify(commentService, times(1)).createComment(any(CommentDTO.class));
    }

    /**
     * Tests that a user with the "USER" role can retrieve comments by post ID successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * A user authenticated with the "USER" role requests comments associated with a specific post ID via the GET /api/comments/post/{postId} endpoint.
     * </p>
     *
     * <p>
     * <strong>Test Steps:</strong>
     * <ol>
     *     <li>Mock the {@link CommentService#getCommentsByPostId(Long)} method to return a list containing the sample {@link CommentDTO}.</li>
     *     <li>Perform a GET request to "/api/comments/post/1".</li>
     *     <li>Expect the HTTP status to be 200 (OK).</li>
     *     <li>Verify that the response JSON array contains the correct comment data.</li>
     *     <li>Ensure that {@link CommentService#getCommentsByPostId(Long)} is invoked exactly once with postId=1.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response JSON array contains a comment with correct "id", "content", "postId", and "userId".</li>
     *     <li>{@link CommentService#getCommentsByPostId(Long)} is called once with postId=1.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * All assertions pass, indicating that comments are successfully retrieved for the specified post ID.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the HTTP status is not 200.</li>
     *     <li>If the response JSON does not contain the expected comment data.</li>
     *     <li>If the service method is not invoked exactly once with the correct postId.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Error Conditions:</strong>
     * <ul>
     *     <li>If the postId does not exist, resulting in an empty list or 404 Not Found.</li>
     *     <li>Service layer exceptions leading to 500 Internal Server Error.</li>
     * </ul>
     * </p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("USER can retrieve comments by post ID and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testGetCommentsByPostId() throws Exception {
        // Arrange: Mock the service layer to return a list containing the sample commentDTO
        List<CommentDTO> comments = Arrays.asList(commentDTO);
        when(commentService.getCommentsByPostId(1L)).thenReturn(comments);

        // Act: Perform a GET request to retrieve comments by post ID
        mockMvc.perform(get("/api/comments/post/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Assert: Expect HTTP 200 OK and verify the response content
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].content").value("This is a comment"))
                .andExpect(jsonPath("$[0].postId").value(1))
                // Fixed JSON path to verify userId
                .andExpect(jsonPath("$[0].userId").value(1));

        // Verify that the getCommentsByPostId method was called exactly once with postId=1
        verify(commentService, times(1)).getCommentsByPostId(1L);
    }

    /**
     * Tests that a user with the "USER" role can update a comment successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * A user authenticated with the "USER" role attempts to update an existing comment via the PUT /api/comments/{commentId} endpoint.
     * </p>
     *
     * <p>
     * <strong>Test Steps:</strong>
     * <ol>
     *     <li>Create an updated {@link CommentDTO} with new content.</li>
     *     <li>Mock the {@link CommentService#updateComment(Long, CommentDTO)} method to return the updated {@link CommentDTO} when called with commentId=1.</li>
     *     <li>Perform a PUT request to "/api/comments/1" with the updated {@link CommentDTO} as JSON content.</li>
     *     <li>Expect the HTTP status to be 200 (OK).</li>
     *     <li>Verify that the response JSON matches the updated {@link CommentDTO} fields.</li>
     *     <li>Ensure that {@link CommentService#updateComment(Long, CommentDTO)} is invoked exactly once with commentId=1 and any {@link CommentDTO}.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 200 (OK).</li>
     *     <li>Response JSON contains updated "id", "content", "postId", and "userId".</li>
     *     <li>{@link CommentService#updateComment(Long, CommentDTO)} is called once with commentId=1 and any {@link CommentDTO}.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * All assertions pass, indicating that the comment is successfully updated and the service method is correctly invoked.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the HTTP status is not 200.</li>
     *     <li>If the response JSON does not reflect the updated comment data.</li>
     *     <li>If the service method is not invoked exactly once with the correct parameters.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Error Conditions:</strong>
     * <ul>
     *     <li>If the commentId does not exist, resulting in a 404 Not Found.</li>
     *     <li>Validation errors leading to a 400 Bad Request.</li>
     *     <li>Service layer exceptions causing a 500 Internal Server Error.</li>
     * </ul>
     * </p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("USER can update a comment and receives 200")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testUpdateComment() throws Exception {
        // Arrange: Create an updated CommentDTO and mock the service layer to return it when updateComment is called
        CommentDTO updatedCommentDTO = new CommentDTO(1L, "Updated comment", 1L, 1L);
        when(commentService.updateComment(eq(1L), any(CommentDTO.class))).thenReturn(updatedCommentDTO);

        // Act: Perform a PUT request to update the comment
        mockMvc.perform(put("/api/comments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCommentDTO)))
                // Assert: Expect HTTP 200 OK and verify the response content
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Updated comment"))
                .andExpect(jsonPath("$.postId").value(1))
                .andExpect(jsonPath("$.userId").value(1));

        // Verify that the updateComment method was called exactly once with commentId=1 and any CommentDTO
        verify(commentService, times(1)).updateComment(eq(1L), any(CommentDTO.class));
    }

    /**
     * Tests that a user with the "USER" role can delete a comment successfully.
     *
     * <p>
     * <strong>Premise:</strong>
     * A user authenticated with the "USER" role attempts to delete an existing comment via the DELETE /api/comments/{commentId} endpoint.
     * </p>
     *
     * <p>
     * <strong>Test Steps:</strong>
     * <ol>
     *     <li>Mock the {@link CommentService#deleteComment(Long)} method to do nothing when called with commentId=1.</li>
     *     <li>Perform a DELETE request to "/api/comments/1".</li>
     *     <li>Expect the HTTP status to be 204 (No Content).</li>
     *     <li>Ensure that {@link CommentService#deleteComment(Long)} is invoked exactly once with commentId=1.</li>
     * </ol>
     * </p>
     *
     * <p>
     * <strong>Assertions:</strong>
     * <ul>
     *     <li>HTTP status is 204 (No Content).</li>
     *     <li>{@link CommentService#deleteComment(Long)} is called once with commentId=1.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Pass Condition:</strong>
     * All assertions pass, indicating that the comment is successfully deleted and the service method is correctly invoked.
     * </p>
     *
     * <p>
     * <strong>Fail Conditions:</strong>
     * <ul>
     *     <li>If the HTTP status is not 204.</li>
     *     <li>If the service method is not invoked exactly once with the correct commentId.</li>
     * </ul>
     * </p>
     *
     * <p>
     * <strong>Error Conditions:</strong>
     * <ul>
     *     <li>If the commentId does not exist, resulting in a 404 Not Found.</li>
     *     <li>Service layer exceptions causing a 500 Internal Server Error.</li>
     * </ul>
     * </p>
     *
     * @throws Exception if the MockMvc request fails
     */
    @Test
    @DisplayName("USER can delete a comment and receives 204")
    @WithMockUser(username = "testuser", roles = {"USER"})
    void testDeleteComment() throws Exception {
        // Arrange: Mock the service layer to do nothing when deleteComment is called with commentId=1
        doNothing().when(commentService).deleteComment(1L);

        // Act: Perform a DELETE request to remove the comment
        mockMvc.perform(delete("/api/comments/1")
                .contentType(MediaType.APPLICATION_JSON))
                // Assert: Expect HTTP 204 No Content
                .andExpect(status().isNoContent());

        // Verify that the deleteComment method was called exactly once with commentId=1
        verify(commentService, times(1)).deleteComment(1L);
    }
}
