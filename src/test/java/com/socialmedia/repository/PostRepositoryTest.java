package com.socialmedia.repository;

import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link PostRepository}.
 * <p>
 * This class verifies the core CRUD operations for the {@link Post} entity
 * using an in-memory database configured via {@code @DataJpaTest}.
 * </p>
 *
 * <p><strong>Premise:</strong></p>
 * <ul>
 *     <li>Tests basic persistence, retrieval, and deletion functionalities of the repository.</li>
 *     <li>Ensures that associations between {@link Post} and {@link User} are properly handled.</li>
 * </ul>
 *
 * <p><strong>Error Conditions / Acceptable Values:</strong></p>
 * <ul>
 *     <li>Title and Content can be any valid string. Tests ensure that persistence and retrieval work correctly.</li>
 *     <li>Post must have an associated {@link User} to be valid.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong></p>
 * <ul>
 *     <li><strong>Pass:</strong> If basic JPA operations (save, findById, findAll, delete) work as expected.</li>
 *     <li><strong>Fail:</strong> If data is not persisted correctly or retrieval returns incorrect results.</li>
 * </ul>
 *
 * @version 1.1
 * @since 2025-01-28
 */
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    /**
     * Sets up the test environment before each test case.
     * <p>
     * Creates and persists a test {@link User} that will be associated with all test posts.
     * </p>
     */
    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        userRepository.save(user);
    }

    /**
     * Tests saving a {@link Post} entity to the repository.
     * <p>
     * Ensures that a post can be successfully persisted and retrieved with all its properties intact.
     * </p>
     *
     * <p><strong>Pass Condition:</strong> The saved post has a generated ID and matches the provided attributes.</p>
     */
    @Test
    @DisplayName("Test saving a post")
    void testSavePost() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("Test Post");
        assertThat(savedPost.getContent()).isEqualTo("This is a test post.");
        assertThat(savedPost.getUser()).isEqualTo(user);
    }

    /**
     * Tests retrieving a {@link Post} entity by its ID.
     * <p>
     * Saves a post and verifies that it can be correctly retrieved using {@code findById()}.
     * </p>
     *
     * <p><strong>Pass Condition:</strong> The retrieved post must be present and match the expected attributes.</p>
     */
    @Test
    @DisplayName("Test finding a post by ID")
    void testFindById() {
        Post post = new Post();
        post.setTitle("Find Post");
        post.setContent("Content for find post.");
        post.setUser(user);
        postRepository.save(post);

        Optional<Post> foundPost = postRepository.findById(post.getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("Find Post");
    }

    /**
     * Tests retrieving all posts in the repository.
     * <p>
     * Persists multiple posts and ensures that they can be retrieved correctly via {@code findAll()}.
     * </p>
     *
     * <p><strong>Pass Condition:</strong> The repository returns the expected number of posts, and their attributes match.</p>
     */
    @Test
    @DisplayName("Test retrieving all posts")
    void testFindAll() {
        Post post1 = new Post();
        post1.setTitle("Post 1");
        post1.setContent("Content 1");
        post1.setUser(user);

        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setContent("Content 2");
        post2.setUser(user);

        postRepository.saveAll(Arrays.asList(post1, post2));

        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(2);
        assertThat(posts).extracting(Post::getTitle).containsExactlyInAnyOrder("Post 1", "Post 2");
    }

    /**
     * Tests deleting a {@link Post} entity from the repository.
     * <p>
     * Ensures that a saved post can be successfully deleted and is no longer retrievable.
     * </p>
     *
     * <p><strong>Pass Condition:</strong> After deletion, {@code findById()} should return an empty result.</p>
     */
    @Test
    @DisplayName("Test deleting a post")
    void testDeletePost() {
        Post post = new Post();
        post.setTitle("Delete Post");
        post.setContent("Content to delete.");
        post.setUser(user);
        postRepository.save(post);

        postRepository.delete(post);

        Optional<Post> deletedPost = postRepository.findById(post.getId());
        assertThat(deletedPost).isNotPresent();
    }
}
