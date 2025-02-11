package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList; // Added import for ArrayList

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Like} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link Like} class,
 * including its constructors, getters, setters, {@code equals()}, {@code hashCode()},
 * {@code toString()}, and lifecycle callback method {@code onCreate()}.
 * It ensures that the {@link Like} class behaves as expected under various scenarios.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class LikeTest {

    private Like like;
    private Like identicalLike;
    private Like differentLike;

    private Post post;
    private User user;

    /**
     * Initializes sample {@link Like}, {@link Post}, and {@link User} instances before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Post and User for association
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setCreatedAt(LocalDateTime.now().minusDays(1));
        post.setUpdatedAt(LocalDateTime.now().minusHours(1));
        post.setComments(new ArrayList<>()); // Initializes an empty list of comments
        post.setLikes(new ArrayList<>());    // Initializes an empty list of likes
        post.setMedia(new ArrayList<>());    // Initializes an empty list of media

        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRole("USER");
        user.setPosts(new ArrayList<>());           // Initializes an empty list of posts
        user.setComments(new ArrayList<>());        // Initializes an empty list of comments
        user.setLikes(new ArrayList<>());           // Initializes an empty list of likes
        user.setNotifications(new ArrayList<>());   // Initializes an empty list of notifications

        // Initialize Likes
        like = new Like();
        like.setId(1L);
        like.setPost(post);
        like.setUser(user);
        like.setLikedAt(null); // Will be set by onCreate()

        identicalLike = new Like();
        identicalLike.setId(1L);
        identicalLike.setPost(post);
        identicalLike.setUser(user);
        identicalLike.setLikedAt(null); // Will be set by onCreate()

        differentLike = new Like();
        differentLike.setId(2L);
        differentLike.setPost(post);
        differentLike.setUser(user);
        differentLike.setLikedAt(null); // Will be set by onCreate()
    }

    /**
     * Tests the no-args constructor and verifies that all fields are initialized to their default values.
     */
    @Test
    @DisplayName("Test no-args constructor and default values")
    void testNoArgsConstructor() {
        Like emptyLike = new Like();
        assertNull(emptyLike.getId(), "ID should be null");
        assertNull(emptyLike.getLikedAt(), "likedAt should be null");
        assertNull(emptyLike.getPost(), "Post should be null");
        assertNull(emptyLike.getUser(), "User should be null");
    }

    /**
     * Tests the all-arguments constructor by verifying that all fields are correctly initialized.
     */
    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Like allArgsLike = new Like(
                3L,
                now,
                post,
                user
        );

        assertEquals(3L, allArgsLike.getId(), "ID should be 3");
        assertEquals(now, allArgsLike.getLikedAt(), "likedAt mismatch");
        assertEquals(post, allArgsLike.getPost(), "Post mismatch");
        assertEquals(user, allArgsLike.getUser(), "User mismatch");
    }

    /**
     * Tests the getters and setters by setting each field and verifying the values.
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        Like testLike = new Like();

        testLike.setId(4L);
        testLike.setLikedAt(LocalDateTime.of(2025, 1, 1, 10, 0));
        testLike.setPost(post);
        testLike.setUser(user);

        assertEquals(4L, testLike.getId(), "ID should be 4");
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), testLike.getLikedAt(), "likedAt mismatch");
        assertEquals(post, testLike.getPost(), "Post mismatch");
        assertEquals(user, testLike.getUser(), "User mismatch");
    }

    /**
     * Tests the {@code equals()} method for reflexivity, symmetry, transitivity, and null comparison.
     */
    @Test
    @DisplayName("Test equals() method")
    void testEquals() {
        // Reflexive
        assertEquals(like, like, "Like should be equal to itself");

        // Symmetric
        assertEquals(like, identicalLike, "Likes with identical fields should be equal");
        assertEquals(identicalLike, like, "Symmetric equality failed");

        // Transitive
        Like thirdLike = new Like();
        thirdLike.setId(1L);
        thirdLike.setPost(post);
        thirdLike.setUser(user);
        thirdLike.setLikedAt(null); // Will be set by onCreate()
        assertEquals(like, identicalLike, "Like equals identicalLike");
        assertEquals(identicalLike, thirdLike, "identicalLike equals thirdLike");
        assertEquals(like, thirdLike, "Transitive equality failed");

        // Consistent
        assertEquals(like, identicalLike, "Consistency check");
        assertEquals(like, identicalLike, "Consistency check again");

        // Null comparison
        assertNotEquals(like, null, "Like should not be equal to null");

        // Different object types
        assertNotEquals(like, "Some String", "Like should not be equal to an unrelated object");

        // Unequal likes
        assertNotEquals(like, differentLike, "Likes with different fields should not be equal");
    }

    /**
     * Tests the {@code hashCode()} method to ensure consistency with {@code equals()}.
     */
    @Test
    @DisplayName("Test hashCode() method")
    void testHashCode() {
        // Equal objects must have the same hash code
        assertEquals(like.hashCode(), identicalLike.hashCode(), "Hash codes should match for equal likes");

        // Different objects may have different hash codes
        assertNotEquals(like.hashCode(), differentLike.hashCode(), "Hash codes should differ for different likes");
    }

    /**
     * Tests the {@code toString()} method to ensure it includes all relevant fields.
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        // Since 'post' and 'user' are excluded from toString(), adjust the expected string accordingly
        String expected = "Like(id=1, likedAt=null)";
        assertEquals(expected, like.toString(), "toString output mismatch");
    }

    /**
     * Tests the {@code canEqual()} method to ensure proper equality checks.
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        // canEqual should return true for another Like
        assertTrue(like.canEqual(identicalLike), "canEqual should return true for another Like");

        // canEqual should return false for different types
        assertFalse(like.canEqual("Not a Like"), "canEqual should return false for different types");
    }

    /**
     * Tests the PrePersist lifecycle callback {@code onCreate()} method.
     * <p>
     * Verifies that {@code onCreate()} correctly sets the {@code likedAt} field.
     * </p>
     */
    @Test
    @DisplayName("Test onCreate() lifecycle callback")
    void testOnCreate() throws Exception {
        Like prePersistLike = new Like();
        prePersistLike.setPost(post);
        prePersistLike.setUser(user);

        // Use reflection to invoke the protected onCreate() method
        Method onCreateMethod = Like.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(prePersistLike);

        assertNotNull(prePersistLike.getLikedAt(), "likedAt should be set by onCreate()");

        // Assuming that onCreate() sets likedAt to now, we can check if it's recent
        LocalDateTime now = LocalDateTime.now();
        assertTrue(prePersistLike.getLikedAt().isBefore(now.plusSeconds(1)), "likedAt should be set to current time");
        assertTrue(prePersistLike.getLikedAt().isAfter(now.minusSeconds(5)), "likedAt should be set to current time");
    }

    /**
     * Tests adding and removing Post associations.
     */
    @Test
    @DisplayName("Test adding and removing Post associations")
    void testAddAndRemovePost() {
        Post newPost = new Post();
        newPost.setId(2L);
        newPost.setTitle("Another Test Post");
        newPost.setContent("Content of another test post.");
        newPost.setCreatedAt(LocalDateTime.now().minusDays(1));
        newPost.setUpdatedAt(LocalDateTime.now().minusHours(1));
        newPost.setComments(new ArrayList<>()); // Initializes an empty list of comments
        newPost.setLikes(new ArrayList<>());    // Initializes an empty list of likes
        newPost.setMedia(new ArrayList<>());    // Initializes an empty list of media

        // Update post association
        like.setPost(newPost);
        assertEquals(newPost, like.getPost(), "Post should be updated to newPost");

        // Remove post association
        like.setPost(null);
        assertNull(like.getPost(), "Post should be null after removal");
    }

    /**
     * Tests adding and removing User associations.
     */
    @Test
    @DisplayName("Test adding and removing User associations")
    void testAddAndRemoveUser() {
        User newUser = new User();
        newUser.setId(2L);
        newUser.setName("Jane Smith");
        newUser.setEmail("jane.smith@example.com");
        newUser.setPassword("securepass");
        newUser.setRole("ADMIN");
        newUser.setPosts(new ArrayList<>());           // Initializes an empty list of posts
        newUser.setComments(new ArrayList<>());        // Initializes an empty list of comments
        newUser.setLikes(new ArrayList<>());           // Initializes an empty list of likes
        newUser.setNotifications(new ArrayList<>());   // Initializes an empty list of notifications

        // Update user association
        like.setUser(newUser);
        assertEquals(newUser, like.getUser(), "User should be updated to newUser");

        // Remove user association
        like.setUser(null);
        assertNull(like.getUser(), "User should be null after removal");
    }
}
