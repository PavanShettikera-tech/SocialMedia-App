package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the {@link Post} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link Post} model,
 * including field assignments, associations, lifecycle callbacks, and utility methods
 * like equals(), hashCode(), and toString().
 * </p>
 */
class PostTest {

    private Post post1;
    private Post post2;
    private User user;

    /**
     * Sets up the test environment before each test case.
     * <p>
     * Initializes a {@link User} instance and two {@link Post} instances with identical
     * attributes to be used across various test methods.
     * </p>
     */
    @BeforeEach
    void setUp() {
        // Prepare a User for association
        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("USER");

        // Construct post1 via all-args constructor
        post1 = new Post(
            1L,
            "Title",
            "Content",
            LocalDateTime.parse("2025-01-01T10:00"),
            LocalDateTime.parse("2025-01-01T10:00"),
            user,
            new ArrayList<>(), // comments
            new ArrayList<>(), // likes
            new ArrayList<>()  // media
        );

        // Make post2 identical to post1
        post2 = new Post(
            1L,
            "Title",
            "Content",
            LocalDateTime.parse("2025-01-01T10:00"),
            LocalDateTime.parse("2025-01-01T10:00"),
            user,
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }

    // -----------------------------------------------------------------------
    // 1) Basic field and association tests
    // -----------------------------------------------------------------------

    /**
     * Tests that all fields of a {@link Post} are correctly set upon creation.
     * <p>
     * Verifies that the title, content, associated user, and initialized lists for
     * comments, likes, and media are correctly assigned.
     * </p>
     * <p><b>Pass Condition:</b> All fields match the expected values and lists are initialized.</p>
     */
    @Test
    @DisplayName("Post creation fields are correctly set")
    void testPostCreationFields() {
        assertEquals("Title", post1.getTitle());
        assertEquals("Content", post1.getContent());
        assertEquals(user, post1.getUser());
        assertNotNull(post1.getComments(), "Comments list should be initialized");
        assertNotNull(post1.getLikes(), "Likes list should be initialized");
        assertNotNull(post1.getMedia(), "Media list should be initialized");
    }

    /**
     * Tests updating the title field of a {@link Post}.
     * <p>
     * Changes the title of the post and verifies that the update is reflected correctly.
     * </p>
     * <p><b>Pass Condition:</b> The title is updated to the new value.</p>
     */
    @Test
    @DisplayName("Post can update fields like title")
    void testPostUpdateFields() {
        post1.setTitle("Updated Title");
        assertEquals("Updated Title", post1.getTitle());
    }

    /**
     * Tests adding a {@link Comment} to a {@link Post}.
     * <p>
     * Creates a new comment, associates it with the post, and verifies that it is added correctly.
     * </p>
     * <p><b>Pass Condition:</b> The comments list size increases and contains the new comment.</p>
     */
    @Test
    @DisplayName("Test adding a comment to Post")
    void testAddComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Nice post");
        comment.setUser(user);
        comment.setPost(post1);

        post1.getComments().add(comment);
        assertEquals(1, post1.getComments().size());
        assertEquals(comment, post1.getComments().get(0));
    }

    /**
     * Tests adding a {@link Like} to a {@link Post}.
     * <p>
     * Creates a new like, associates it with the post, and verifies that it is added correctly.
     * </p>
     * <p><b>Pass Condition:</b> The likes list size increases and contains the new like.</p>
     */
    @Test
    @DisplayName("Test adding a like to Post")
    void testAddLike() {
        Like like = new Like();
        like.setId(1L);
        like.setUser(user);
        like.setPost(post1);

        post1.getLikes().add(like);
        assertEquals(1, post1.getLikes().size());
        assertEquals(like, post1.getLikes().get(0));
    }

    /**
     * Tests adding a {@link Media} item to a {@link Post}.
     * <p>
     * Creates a new media item, associates it with the post, and verifies that it is added correctly.
     * </p>
     * <p><b>Pass Condition:</b> The media list size increases and contains the new media item.</p>
     */
    @Test
    @DisplayName("Test adding media to Post")
    void testAddMedia() {
        Media media = new Media();
        media.setId(1L);
        media.setUrl("http://example.com/image.jpg");
        media.setType("image");
        media.setPost(post1);

        post1.getMedia().add(media);
        assertEquals(1, post1.getMedia().size());
        assertEquals(media, post1.getMedia().get(0));
    }

    // -----------------------------------------------------------------------
    // 2) Lifecycle callback tests (onCreate / onUpdate)
    // -----------------------------------------------------------------------

    /**
     * Tests the {@code onCreate} lifecycle callback of {@link Post}.
     * <p>
     * Invokes the private {@code onCreate} method via reflection and verifies that
     * both {@code createdAt} and {@code updatedAt} timestamps are set to the current time.
     * </p>
     * <p><b>Pass Condition:</b> {@code createdAt} and {@code updatedAt} are not null and equal.</p>
     *
     * @throws Exception if reflection fails to access or invoke the method
     */
    @Test
    @DisplayName("Test onCreate() sets createdAt and updatedAt to same value")
    void testOnCreate() throws Exception {
        Post newPost = new Post();
        Method onCreateMethod = Post.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(newPost);

        assertNotNull(newPost.getCreatedAt(), "createdAt should be set");
        assertNotNull(newPost.getUpdatedAt(), "updatedAt should be set");
        assertEquals(newPost.getCreatedAt(), newPost.getUpdatedAt(),
            "createdAt and updatedAt should be the same on creation");
    }

    /**
     * Tests the {@code onUpdate} lifecycle callback of {@link Post}.
     * <p>
     * Simulates the creation of a post by setting initial timestamps, invokes the private
     * {@code onUpdate} method via reflection, and verifies that {@code updatedAt} is updated
     * to a later time than {@code createdAt}.
     * </p>
     * <p><b>Pass Condition:</b> {@code updatedAt} is not null and is after {@code createdAt}.</p>
     *
     * @throws Exception if reflection fails to access or invoke the method
     */
    @Test
    @DisplayName("Test onUpdate() updates updatedAt after creation")
    void testOnUpdate() throws Exception {
        Post newPost = new Post();
        // Simulate creation
        newPost.setCreatedAt(LocalDateTime.of(2025, 1, 1, 10, 0));
        newPost.setUpdatedAt(LocalDateTime.of(2025, 1, 1, 10, 0));

        // Call onUpdate via reflection
        Method onUpdateMethod = Post.class.getDeclaredMethod("onUpdate");
        onUpdateMethod.setAccessible(true);
        onUpdateMethod.invoke(newPost);

        assertNotNull(newPost.getUpdatedAt(), "updatedAt should be updated by onUpdate()");
        assertTrue(newPost.getUpdatedAt().isAfter(newPost.getCreatedAt()),
            "updatedAt should be after createdAt on update");
    }

    // -----------------------------------------------------------------------
    // 3) equals(), hashCode(), toString(), canEqual() tests
    // -----------------------------------------------------------------------

    /**
     * Verifies that a {@link Post} is equal to itself.
     * <p>
     * Ensures that the equals method returns {@code true} when comparing the object to itself.
     * </p>
     * <p><b>Pass Condition:</b> {@code post1.equals(post1)} is {@code true}.</p>
     */
    @Test
    @DisplayName("Equals verification: same object")
    void testEqualsSameObject() {
        assertTrue(post1.equals(post1));
    }

    /**
     * Verifies that two identical {@link Post} objects are equal.
     * <p>
     * Ensures that the equals method returns {@code true} when comparing two posts with identical attributes.
     * </p>
     * <p><b>Pass Condition:</b> {@code post1} is equal to {@code post2}.</p>
     */
    @Test
    @DisplayName("Equals verification: equal objects")
    void testEqualsEqualObjects() {
        // post1 and post2 are identical
        assertEquals(post1, post2);
    }

    /**
     * Verifies that two {@link Post} objects with different IDs are not equal.
     * <p>
     * Changes the ID of {@code post2} and ensures that the equals method returns {@code false}.
     * </p>
     * <p><b>Pass Condition:</b> {@code post1} is not equal to {@code post2} after ID change.</p>
     */
    @Test
    @DisplayName("Equals verification: different ID")
    void testEqualsDifferentId() {
        post2.setId(2L);
        assertNotEquals(post1, post2, "Posts should differ if IDs differ");
    }

    /**
     * Verifies that a {@link Post} is not equal to {@code null}.
     * <p>
     * Ensures that the equals method returns {@code false} when comparing the post to {@code null}.
     * </p>
     * <p><b>Pass Condition:</b> {@code post1} is not equal to {@code null}.</p>
     */
    @Test
    @DisplayName("Equals verification: null comparison")
    void testEqualsNull() {
        assertNotEquals(null, post1, "A post is never equal to null");
    }

    /**
     * Verifies that a {@link Post} is not equal to an object of a different class.
     * <p>
     * Ensures that the equals method returns {@code false} when comparing the post to an instance of another class.
     * </p>
     * <p><b>Pass Condition:</b> {@code post1} is not equal to an {@link Object} instance.</p>
     */
    @Test
    @DisplayName("Equals verification: different class")
    void testEqualsDifferentClass() {
        assertNotEquals(post1, new Object(), "Post is not equal to a different class");
    }

    /**
     * Verifies that identical {@link Post} objects have the same hash code.
     * <p>
     * Ensures that the hashCode method returns the same value for two posts with identical attributes.
     * </p>
     * <p><b>Pass Condition:</b> Hash codes of {@code post1} and {@code post2} are equal.</p>
     */
    @Test
    @DisplayName("HashCode consistency for identical objects")
    void testHashCodeConsistency() {
        assertEquals(post1.hashCode(), post2.hashCode(),
            "Hash codes should match for identical posts");
    }

    /**
     * Verifies that {@link Post} objects with different IDs have different hash codes.
     * <p>
     * Changes the ID of {@code post2} and ensures that the hash codes of the two posts are different.
     * </p>
     * <p><b>Pass Condition:</b> Hash codes of {@code post1} and {@code post2} are not equal after ID change.</p>
     */
    @Test
    @DisplayName("HashCode difference for different ID")
    void testHashCodeDifference() {
        post2.setId(999L);
        assertNotEquals(post1.hashCode(), post2.hashCode(),
            "Hash codes should differ if IDs differ");
    }

    /**
     * Verifies that the {@code toString} method of {@link Post} includes essential fields.
     * <p>
     * Ensures that the string representation of a post contains its ID, title, and associated user.
     * </p>
     * <p><b>Pass Condition:</b> {@code toString} output contains specific field values.</p>
     */
    @Test
    @DisplayName("ToString contains essential fields")
    void testToStringContent() {
        String result = post1.toString();
        assertAll(
            () -> assertTrue(result.contains("id=1")),
            () -> assertTrue(result.contains("title=Title")),
            () -> assertTrue(result.contains("user=User(id=1"))
        );
    }

    /**
     * Verifies the {@code canEqual} method of {@link Post}.
     * <p>
     * Ensures that {@code canEqual} returns {@code true} when comparing with another {@link Post}
     * and {@code false} when comparing with an object of a different type.
     * </p>
     * <p><b>Pass Condition:</b> {@code canEqual} behaves correctly based on the object type.</p>
     */
    @Test
    @DisplayName("canEqual verification: same type => true, different type => false")
    void testCanEqual() {
        assertTrue(post1.canEqual(post2), "Post should canEqual another Post");
        // canEqual should return false if we pass an object of different type
        assertFalse(post1.canEqual(new User()), "Post should not canEqual a User");
    }

    /**
     * Validates the all-args constructor of {@link Post}.
     * <p>
     * Creates a new post using the all-arguments constructor and verifies that all fields are set correctly.
     * </p>
     * <p><b>Pass Condition:</b> All fields match the values passed to the constructor.</p>
     */
    @Test
    @DisplayName("All-args constructor validation")
    void testAllArgsConstructor() {
        Post newPost = new Post(
            2L, 
            "NewTitle", 
            "NewContent", 
            LocalDateTime.now(), 
            LocalDateTime.now(), 
            user, 
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        assertEquals(2L, newPost.getId());
        assertEquals("NewTitle", newPost.getTitle());
        assertEquals("NewContent", newPost.getContent());
        assertEquals(user, newPost.getUser());
        assertNotNull(newPost.getComments());
        assertNotNull(newPost.getLikes());
        assertNotNull(newPost.getMedia());
    }

    /**
     * Validates the no-args constructor of {@link Post}.
     * <p>
     * Creates a new post using the no-arguments constructor and verifies that fields are initialized correctly.
     * </p>
     * <p><b>Pass Condition:</b> ID and title are {@code null}, while lists are initialized.</p>
     */
    @Test
    @DisplayName("No-args constructor validation")
    void testNoArgsConstructor() {
        Post emptyPost = new Post();
        assertAll(
            () -> assertNull(emptyPost.getId()),
            () -> assertNull(emptyPost.getTitle()),
            () -> assertNotNull(emptyPost.getComments(), "Comments should be initialized"),
            () -> assertNotNull(emptyPost.getLikes(), "Likes should be initialized"),
            () -> assertNotNull(emptyPost.getMedia(), "Media should be initialized")
        );
    }
}
