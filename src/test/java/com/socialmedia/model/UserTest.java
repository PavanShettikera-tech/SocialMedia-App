package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime; // Added import for LocalDateTime

/**
 * Unit tests for the {@link User} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link User} class,
 * including its constructors, getters, setters, {@code equals()}, {@code hashCode()},
 * {@code toString()}, and association management methods for posts, comments, likes, and notifications.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class UserTest {

    private User user;
    private User anotherUser;
    private User differentUser;

    /**
     * Initializes sample {@link User} instances before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Users for testing
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRole("ADMIN");

        anotherUser = new User();
        anotherUser.setId(1L);
        anotherUser.setName("John Doe");
        anotherUser.setEmail("john.doe@example.com");
        anotherUser.setPassword("password123");
        anotherUser.setRole("ADMIN");

        differentUser = new User();
        differentUser.setId(2L);
        differentUser.setName("Jane Smith");
        differentUser.setEmail("jane.smith@example.com");
        differentUser.setPassword("securepass");
        differentUser.setRole("USER");
    }

    /**
     * Tests the no-args constructor and verifies that all fields are initialized to their default values.
     */
    @Test
    @DisplayName("Test no-args constructor and default values")
    void testNoArgsConstructor() {
        User emptyUser = new User();
        assertNull(emptyUser.getId(), "ID should be null");
        assertNull(emptyUser.getName(), "Name should be null");
        assertNull(emptyUser.getEmail(), "Email should be null");
        assertNull(emptyUser.getPassword(), "Password should be null");
        assertEquals("USER", emptyUser.getRole(), "Default role should be USER");
        assertNotNull(emptyUser.getPosts(), "Posts list should not be null");
        assertNotNull(emptyUser.getComments(), "Comments list should not be null");
        assertNotNull(emptyUser.getLikes(), "Likes list should not be null");
        assertNotNull(emptyUser.getNotifications(), "Notifications list should not be null");
    }

    /**
     * Tests the all-arguments constructor by verifying that all fields are correctly initialized.
     */
    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        List<Post> posts = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        List<Like> likes = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();

        User fullUser = new User(
                3L,
                "Alice Wonderland",
                "alice@example.com",
                "alicepass",
                "USER",
                posts,
                comments,
                likes,
                notifications
        );

        assertEquals(3L, fullUser.getId(), "ID should be 3");
        assertEquals("Alice Wonderland", fullUser.getName(), "Name mismatch");
        assertEquals("alice@example.com", fullUser.getEmail(), "Email mismatch");
        assertEquals("alicepass", fullUser.getPassword(), "Password mismatch");
        assertEquals("USER", fullUser.getRole(), "Role mismatch");
        assertEquals(posts, fullUser.getPosts(), "Posts list mismatch");
        assertEquals(comments, fullUser.getComments(), "Comments list mismatch");
        assertEquals(likes, fullUser.getLikes(), "Likes list mismatch");
        assertEquals(notifications, fullUser.getNotifications(), "Notifications list mismatch");
    }

    /**
     * Tests the getters and setters by setting each field and verifying the values.
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        User testUser = new User();

        testUser.setId(4L);
        testUser.setName("Bob Builder");
        testUser.setEmail("bob.builder@example.com");
        testUser.setPassword("buildit");
        testUser.setRole("USER");

        List<Post> posts = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        List<Like> likes = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();

        testUser.setPosts(posts);
        testUser.setComments(comments);
        testUser.setLikes(likes);
        testUser.setNotifications(notifications);

        assertEquals(4L, testUser.getId(), "ID should be 4");
        assertEquals("Bob Builder", testUser.getName(), "Name mismatch");
        assertEquals("bob.builder@example.com", testUser.getEmail(), "Email mismatch");
        assertEquals("buildit", testUser.getPassword(), "Password mismatch");
        assertEquals("USER", testUser.getRole(), "Role mismatch");
        assertEquals(posts, testUser.getPosts(), "Posts list mismatch");
        assertEquals(comments, testUser.getComments(), "Comments list mismatch");
        assertEquals(likes, testUser.getLikes(), "Likes list mismatch");
        assertEquals(notifications, testUser.getNotifications(), "Notifications list mismatch");
    }

    /**
     * Tests the {@code equals()} method for reflexivity, symmetry, transitivity, and null comparison.
     */
    @Test
    @DisplayName("Test equals() method")
    void testEquals() {
        // Reflexive
        assertEquals(user, user, "User should be equal to itself");

        // Symmetric
        assertEquals(user, anotherUser, "Users with same properties should be equal");
        assertEquals(anotherUser, user, "Symmetric equality failed");

        // Transitive
        User thirdUser = new User();
        thirdUser.setId(1L);
        thirdUser.setName("John Doe");
        thirdUser.setEmail("john.doe@example.com");
        thirdUser.setPassword("password123");
        thirdUser.setRole("ADMIN");

        assertEquals(user, anotherUser, "User equals anotherUser");
        assertEquals(anotherUser, thirdUser, "anotherUser equals thirdUser");
        assertEquals(user, thirdUser, "User equals thirdUser (transitive)");

        // Consistent
        assertEquals(user, anotherUser, "Consistency check");
        assertEquals(user, anotherUser, "Consistency check again");

        // Null comparison
        assertNotEquals(user, null, "User should not be equal to null");

        // Different object types
        assertNotEquals(user, "Some String", "User should not be equal to an unrelated object");

        // Unequal users
        assertNotEquals(user, differentUser, "Users with different properties should not be equal");
    }

    /**
     * Tests the {@code hashCode()} method to ensure consistency with {@code equals()}.
     */
    @Test
    @DisplayName("Test hashCode() method")
    void testHashCode() {
        // Equal objects must have the same hash code
        assertEquals(user.hashCode(), anotherUser.hashCode(), "Hash codes should match for equal users");

        // Different objects may have different hash codes
        assertNotEquals(user.hashCode(), differentUser.hashCode(), "Hash codes should differ for different users");
    }

    /**
     * Tests the {@code toString()} method to ensure it includes all relevant fields.
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        String expected = "User(id=1, name=John Doe, email=john.doe@example.com, password=password123, role=ADMIN, posts=[], comments=[], likes=[], notifications=[])";
        assertEquals(expected, user.toString(), "toString output mismatch");
    }

    /**
     * Tests the {@code canEqual()} method to ensure proper equality checks.
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        // canEqual should return true for same class
        assertTrue(user.canEqual(anotherUser), "canEqual should return true for same class");

        // canEqual should return false for different classes
        assertFalse(user.canEqual("Not a User"), "canEqual should return false for different classes");
    }

    /**
     * Tests adding a post to the user.
     */
    @Test
    @DisplayName("Test adding a post to the user")
    void testAddPost() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("User's Post");
        post.setContent("Content of user's post.");
        post.setUser(user);
        post.setComments(new ArrayList<>()); // Initializes an empty list of comments
        post.setLikes(new ArrayList<>());    // Initializes an empty list of likes
        post.setMedia(new ArrayList<>());    // Initializes an empty list of media
        post.setCreatedAt(LocalDateTime.now().minusDays(1));
        post.setUpdatedAt(LocalDateTime.now().minusHours(1));

        user.getPosts().add(post);
        assertTrue(user.getPosts().contains(post), "Posts should contain the added post");
    }

    /**
     * Tests adding a comment to the user.
     */
    @Test
    @DisplayName("Test adding a comment to the user")
    void testAddComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("User's comment.");
        comment.setPost(new Post());
        comment.setUser(user);

        user.getComments().add(comment);
        assertTrue(user.getComments().contains(comment), "Comments should contain the added comment");
    }

    /**
     * Tests adding a like to the user.
     */
    @Test
    @DisplayName("Test adding a like to the user")
    void testAddLike() {
        Like like = new Like();
        like.setId(1L);
        like.setPost(new Post());
        like.setUser(user);

        user.getLikes().add(like);
        assertTrue(user.getLikes().contains(like), "Likes should contain the added like");
    }

    /**
     * Tests adding a notification to the user.
     */
    @Test
    @DisplayName("Test adding a notification to the user")
    void testAddNotification() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setMessage("Welcome to the platform!");
        notification.setUser(user);

        user.getNotifications().add(notification);
        assertTrue(user.getNotifications().contains(notification), "Notifications should contain the added notification");
    }
}
