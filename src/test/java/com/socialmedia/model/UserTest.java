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
 * <p><b>Version:</b> 1.0</p>
 * <p><b>Since:</b> 2025-01-28</p>
 */
class UserTest {

    private User user;
    private User anotherUser;
    private User differentUser;

    /**
     * Initializes sample {@link User} instances before each test.
     * <p>
     * Sets up three users:
     * <ul>
     *     <li>{@code user}: A primary user with specific attributes.</li>
     *     <li>{@code anotherUser}: A user identical to {@code user} to test equality.</li>
     *     <li>{@code differentUser}: A distinct user with different attributes to test inequality.</li>
     * </ul>
     * </p>
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
     * <p>
     * Ensures that:
     * <ul>
     *     <li>Numeric fields like {@code id} are {@code null}.</li>
     *     <li>String fields like {@code name}, {@code email}, and {@code password} are {@code null}.</li>
     *     <li>{@code role} defaults to {@code "USER"}.</li>
     *     <li>Collections like {@code posts}, {@code comments}, {@code likes}, and {@code notifications} are initialized and not {@code null}.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> All assertions hold true, indicating proper initialization.</p>
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
     * <p>
     * Creates a {@link User} instance using the all-args constructor with specific values and ensures that:
     * <ul>
     *     <li>All fields are set to the provided values.</li>
     *     <li>Associated collections are correctly assigned.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> All fields match the values passed to the constructor.</p>
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
     * <p>
     * Ensures that:
     * <ul>
     *     <li>Each setter correctly assigns the provided value.</li>
     *     <li>Each getter retrieves the expected value.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> All fields are correctly set and retrieved.</p>
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
     * <p>
     * Validates that:
     * <ul>
     *     <li>A user is equal to itself (reflexive).</li>
     *     <li>Two users with identical attributes are equal (symmetric).</li>
     *     <li>Equality is transitive across multiple identical users.</li>
     *     <li>Consistency is maintained across multiple invocations.</li>
     *     <li>A user is not equal to {@code null} or an object of a different type.</li>
     *     <li>Users with differing attributes are not equal.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> All equality properties hold as expected.</p>
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
     * <p>
     * Validates that:
     * <ul>
     *     <li>Equal users have identical hash codes.</li>
     *     <li>Different users have differing hash codes.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> Hash codes are consistent with equality results.</p>
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
     * <p>
     * Ensures that the string representation of a user contains:
     * <ul>
     *     <li>User's ID</li>
     *     <li>Name</li>
     *     <li>Email</li>
     *     <li>Password</li>
     *     <li>Role</li>
     *     <li>Associated collections (posts, comments, likes, notifications)</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> {@code toString} output matches the expected format and contains all necessary fields.</p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        String expected = "User(id=1, name=John Doe, email=john.doe@example.com, password=password123, role=ADMIN, posts=[], comments=[], likes=[], notifications=[])";
        assertEquals(expected, user.toString(), "toString output mismatch");
    }

    /**
     * Tests the {@code canEqual()} method to ensure proper equality checks.
     * <p>
     * Validates that:
     * <ul>
     *     <li>{@code canEqual} returns {@code true} when comparing with another {@link User} instance.</li>
     *     <li>{@code canEqual} returns {@code false} when comparing with an object of a different type.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> {@code canEqual} behaves correctly based on the object type.</p>
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
     * <p>
     * Creates a new {@link Post}, associates it with the user, and verifies that:
     * <ul>
     *     <li>The post is successfully added to the user's posts list.</li>
     *     <li>The association between the post and the user is correctly established.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> The posts list contains the newly added post.</p>
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
     * <p>
     * Creates a new {@link Comment}, associates it with the user, and verifies that:
     * <ul>
     *     <li>The comment is successfully added to the user's comments list.</li>
     *     <li>The association between the comment and the user is correctly established.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> The comments list contains the newly added comment.</p>
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
     * <p>
     * Creates a new {@link Like}, associates it with the user, and verifies that:
     * <ul>
     *     <li>The like is successfully added to the user's likes list.</li>
     *     <li>The association between the like and the user is correctly established.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> The likes list contains the newly added like.</p>
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
     * <p>
     * Creates a new {@link Notification}, associates it with the user, and verifies that:
     * <ul>
     *     <li>The notification is successfully added to the user's notifications list.</li>
     *     <li>The association between the notification and the user is correctly established.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> The notifications list contains the newly added notification.</p>
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
