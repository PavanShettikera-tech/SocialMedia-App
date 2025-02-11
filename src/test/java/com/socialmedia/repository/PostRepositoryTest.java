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
 * Test class for {@link PostRepository}.
 *
 * <p><strong>Premise:</strong>
 * Validates fundamental CRUD operations on Post entities within an in-memory database context.
 *
 * <p><strong>Error Conditions / Acceptable Values:</strong></p>
 * <ul>
 *   <li>Title/Content can be any string. We test typical creation, retrieval, deletion flows.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li>Pass: If basic JPA operations (save, findById, findAll, delete) work as expected.</li>
 *   <li>Fail: If data is not persisted or incorrectly retrieved.</li>
 * </ul>
 */
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        userRepository.save(user);
    }

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
