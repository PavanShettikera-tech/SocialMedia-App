package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Media} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link Media} class,
 * including its constructors, getters, setters, {@code equals()}, {@code hashCode()},
 * {@code toString()}, and lifecycle callback method {@code onCreate()}.
 * It ensures that the {@link Media} class behaves as expected under various scenarios.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class MediaTest {

    private Media media;
    private Media identicalMedia;
    private Media differentMedia;

    private Post post;
    private User user;

    /**
     * Initializes sample {@link Media}, {@link Post}, and {@link User} instances before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize User for association
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRole("USER");
        user.setPosts(new ArrayList<>());
        user.setComments(new ArrayList<>());
        user.setLikes(new ArrayList<>());
        user.setNotifications(new ArrayList<>());

        // Initialize Post for association
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now().minusDays(1));
        post.setUpdatedAt(LocalDateTime.now().minusHours(1));
        post.setComments(new ArrayList<>());
        post.setLikes(new ArrayList<>());
        post.setMedia(new ArrayList<>());

        // Initialize Media instances
        media = new Media();
        media.setId(1L);
        media.setUrl("http://example.com/image1.png");
        media.setType("image");
        media.setPost(post);
        media.setUploadedAt(null); // Will be set by onCreate()

        identicalMedia = new Media();
        identicalMedia.setId(1L);
        identicalMedia.setUrl("http://example.com/image1.png");
        identicalMedia.setType("image");
        identicalMedia.setPost(post);
        identicalMedia.setUploadedAt(null); // Will be set by onCreate()

        differentMedia = new Media();
        differentMedia.setId(2L);
        differentMedia.setUrl("http://example.com/video1.mp4");
        differentMedia.setType("video");
        differentMedia.setPost(post);
        differentMedia.setUploadedAt(null); // Will be set by onCreate()
    }

    /**
     * Tests the no-args constructor and verifies that all fields are initialized to their default values.
     */
    @Test
    @DisplayName("Test no-args constructor and default values")
    void testNoArgsConstructor() {
        Media emptyMedia = new Media();
        assertNull(emptyMedia.getId(), "ID should be null");
        assertNull(emptyMedia.getUrl(), "URL should be null");
        assertNull(emptyMedia.getType(), "Type should be null");
        assertNull(emptyMedia.getUploadedAt(), "uploadedAt should be null");
        assertNull(emptyMedia.getPost(), "Post should be null");
    }

    /**
     * Tests the all-arguments constructor by verifying that all fields are correctly initialized.
     */
    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Media allArgsMedia = new Media(
                3L,
                "http://example.com/image2.png",
                "image",
                now,
                post
        );

        assertEquals(3L, allArgsMedia.getId(), "ID should be 3");
        assertEquals("http://example.com/image2.png", allArgsMedia.getUrl(), "URL mismatch");
        assertEquals("image", allArgsMedia.getType(), "Type mismatch");
        assertEquals(now, allArgsMedia.getUploadedAt(), "uploadedAt mismatch");
        assertEquals(post, allArgsMedia.getPost(), "Post mismatch");
    }

    /**
     * Tests the getters and setters by setting each field and verifying the values.
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        Media testMedia = new Media();

        testMedia.setId(4L);
        testMedia.setUrl("http://example.com/image3.png");
        testMedia.setType("image");
        LocalDateTime uploadTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        testMedia.setUploadedAt(uploadTime);
        testMedia.setPost(post);

        assertEquals(4L, testMedia.getId(), "ID should be 4");
        assertEquals("http://example.com/image3.png", testMedia.getUrl(), "URL mismatch");
        assertEquals("image", testMedia.getType(), "Type mismatch");
        assertEquals(uploadTime, testMedia.getUploadedAt(), "uploadedAt mismatch");
        assertEquals(post, testMedia.getPost(), "Post mismatch");
    }

    /**
     * Tests the {@code equals()} method for reflexivity, symmetry, transitivity, and null comparison.
     */
    @Test
    @DisplayName("Test equals() method")
    void testEquals() {
        // Reflexive
        assertEquals(media, media, "Media should be equal to itself");

        // Symmetric
        assertEquals(media, identicalMedia, "Media should be equal to identicalMedia");
        assertEquals(identicalMedia, media, "identicalMedia should be equal to media");

        // Transitive
        Media thirdMedia = new Media();
        thirdMedia.setId(1L);
        thirdMedia.setUrl("http://example.com/image1.png");
        thirdMedia.setType("image");
        thirdMedia.setPost(post);
        thirdMedia.setUploadedAt(null); // Will be set by onCreate()
        assertEquals(media, identicalMedia, "media equals identicalMedia");
        assertEquals(identicalMedia, thirdMedia, "identicalMedia equals thirdMedia");
        assertEquals(media, thirdMedia, "media equals thirdMedia (transitive)");

        // Consistent
        assertEquals(media, identicalMedia, "Consistency check");
        assertEquals(media, identicalMedia, "Consistency check again");

        // Null comparison
        assertNotEquals(media, null, "Media should not be equal to null");

        // Different object types
        assertNotEquals(media, "Some String", "Media should not be equal to an unrelated object");

        // Unequal media
        assertNotEquals(media, differentMedia, "Media should not be equal to differentMedia");
    }

    /**
     * Tests the {@code hashCode()} method to ensure consistency with {@code equals()}.
     */
    @Test
    @DisplayName("Test hashCode() method")
    void testHashCode() {
        // Equal objects must have the same hash code
        assertEquals(media.hashCode(), identicalMedia.hashCode(), "Hash codes should match for equal media");

        // Different objects may have different hash codes
        assertNotEquals(media.hashCode(), differentMedia.hashCode(), "Hash codes should differ for different media");
    }

    /**
     * Tests the {@code toString()} method to ensure it includes all relevant fields.
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        // Adjust the expected string to match the actual toString() output after excluding fields
        String expected = "Media(id=1, url=http://example.com/image1.png, type=image, uploadedAt=null)";
        assertEquals(expected, media.toString(), "toString output mismatch");
    }

    /**
     * Tests the {@code canEqual()} method to ensure proper equality checks.
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        // canEqual should return true for another Media
        assertTrue(media.canEqual(identicalMedia), "canEqual should return true for another Media");

        // canEqual should return false for different types
        assertFalse(media.canEqual("Not a Media"), "canEqual should return false for different types");
    }

    /**
     * Tests the PrePersist lifecycle callback {@code onCreate()} method.
     * <p>
     * Verifies that {@code onCreate()} correctly sets the {@code uploadedAt} field.
     * </p>
     */
    @Test
    @DisplayName("Test onCreate() lifecycle callback")
    void testOnCreate() throws Exception {
        Media prePersistMedia = new Media();
        prePersistMedia.setUrl("http://example.com/image4.png");
        prePersistMedia.setType("image");
        prePersistMedia.setPost(post);
        prePersistMedia.setUploadedAt(null); // Will be set by onCreate()

        // Use reflection to invoke the protected onCreate() method
        Method onCreateMethod = Media.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(prePersistMedia);

        assertNotNull(prePersistMedia.getUploadedAt(), "uploadedAt should be set by onCreate()");

        // Assuming that onCreate() sets uploadedAt to now, we can check if it's recent
        LocalDateTime now = LocalDateTime.now();
        assertTrue(prePersistMedia.getUploadedAt().isBefore(now.plusSeconds(1)), "uploadedAt should be set to current time");
        assertTrue(prePersistMedia.getUploadedAt().isAfter(now.minusSeconds(5)), "uploadedAt should be set to current time");
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
        newPost.setComments(new ArrayList<>());
        newPost.setLikes(new ArrayList<>());
        newPost.setMedia(new ArrayList<>());

        // Update post association
        media.setPost(newPost);
        assertEquals(newPost, media.getPost(), "Post should be updated to newPost");

        // Remove post association
        media.setPost(null);
        assertNull(media.getPost(), "Post should be null after removal");
    }
}
