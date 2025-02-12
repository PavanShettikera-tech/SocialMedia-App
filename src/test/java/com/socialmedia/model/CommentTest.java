package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Comment} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link Comment} class,
 * including its constructors, getters, setters, {@code equals()}, {@code hashCode()},
 * {@code toString()}, and lifecycle callback methods {@code onCreate()} and {@code onUpdate()}.
 * It ensures that the {@link Comment} class behaves as expected under various scenarios.
 * </p>
 * 
 * <p><b>Version:</b> 1.0</p>
 * <p><b>Since:</b> 2025-01-28</p>
 */
class CommentTest {

    private Comment comment;
    private Comment identicalComment;
    private Comment differentComment;

    private Post post;
    private User user;

    /**
     * Initializes sample {@link Comment}, {@link Post}, and {@link User} instances before each test.
     * <p>
     * Sets up three {@link Comment} instances:
     * <ul>
     *   <li>{@code comment} and {@code identicalComment} are identical.</li>
     *   <li>{@code differentComment} has different properties.</li>
     * </ul>
     * </p>
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
        post.setComments(new ArrayList<>());
        post.setLikes(new ArrayList<>());
        post.setMedia(new ArrayList<>());

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

        // Initialize Comments
        comment = new Comment();
        comment.setId(1L);
        comment.setContent("This is a test comment.");
        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(null); // Will be set by onCreate()
        comment.setUpdatedAt(null); // Will be set by onCreate()

        identicalComment = new Comment();
        identicalComment.setId(1L);
        identicalComment.setContent("This is a test comment.");
        identicalComment.setPost(post);
        identicalComment.setUser(user);
        identicalComment.setCreatedAt(null); // Will be set by onCreate()
        identicalComment.setUpdatedAt(null); // Will be set by onCreate()

        differentComment = new Comment();
        differentComment.setId(2L);
        differentComment.setContent("This is a different comment.");
        differentComment.setPost(post);
        differentComment.setUser(user);
        differentComment.setCreatedAt(null); // Will be set by onCreate()
        differentComment.setUpdatedAt(null); // Will be set by onCreate()
    }

    /**
     * Tests the no-args constructor and verifies that all fields are initialized to their default values.
     * <p>
     * Ensures that:
     * <ul>
     *     <li>Numeric fields like {@code id} are {@code null}.</li>
     *     <li>String fields like {@code content} are {@code null}.</li>
     *     <li>Associations like {@code post} and {@code user} are {@code null}.</li>
     *     <li>Timestamps like {@code createdAt} and {@code updatedAt} are {@code null}.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> All assertions hold true, indicating proper initialization.</p>
     */
    @Test
    @DisplayName("Test no-args constructor and default values")
    void testNoArgsConstructor() {
        Comment emptyComment = new Comment();
        assertNull(emptyComment.getId(), "ID should be null");
        assertNull(emptyComment.getContent(), "Content should be null");
        assertNull(emptyComment.getPost(), "Post should be null");
        assertNull(emptyComment.getUser(), "User should be null");
        assertNull(emptyComment.getCreatedAt(), "CreatedAt should be null");
        assertNull(emptyComment.getUpdatedAt(), "UpdatedAt should be null");
    }

    /**
     * Tests the all-arguments constructor by verifying that all fields are correctly initialized.
     * <p>
     * Creates a {@link Comment} instance using the all-args constructor with specific values and ensures that:
     * <ul>
     *     <li>All fields are set to the provided values.</li>
     *     <li>Associations like {@code post} and {@code user} are correctly assigned.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> All fields match the values passed to the constructor.</p>
     */
    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Comment allArgsComment = new Comment(
                3L,
                "All args comment",
                now,
                now,
                post,
                user
        );

        assertEquals(3L, allArgsComment.getId(), "ID should be 3");
        assertEquals("All args comment", allArgsComment.getContent(), "Content mismatch");
        assertEquals(now, allArgsComment.getCreatedAt(), "CreatedAt mismatch");
        assertEquals(now, allArgsComment.getUpdatedAt(), "UpdatedAt mismatch");
        assertEquals(post, allArgsComment.getPost(), "Post mismatch");
        assertEquals(user, allArgsComment.getUser(), "User mismatch");
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
        Comment testComment = new Comment();

        testComment.setId(4L);
        testComment.setContent("Setter and Getter test comment.");
        testComment.setPost(post);
        testComment.setUser(user);
        testComment.setCreatedAt(LocalDateTime.of(2025, 1, 1, 10, 0));
        testComment.setUpdatedAt(LocalDateTime.of(2025, 1, 2, 12, 0));

        assertEquals(4L, testComment.getId(), "ID should be 4");
        assertEquals("Setter and Getter test comment.", testComment.getContent(), "Content mismatch");
        assertEquals(post, testComment.getPost(), "Post mismatch");
        assertEquals(user, testComment.getUser(), "User mismatch");
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), testComment.getCreatedAt(), "CreatedAt mismatch");
        assertEquals(LocalDateTime.of(2025, 1, 2, 12, 0), testComment.getUpdatedAt(), "UpdatedAt mismatch");
    }

    /**
     * Tests the {@code equals()} method for reflexivity, symmetry, transitivity, and null comparison.
     * <p>
     * Validates that:
     * <ul>
     *     <li>A comment is equal to itself (reflexive).</li>
     *     <li>Two comments with identical attributes are equal (symmetric).</li>
     *     <li>Equality is transitive across multiple identical comments.</li>
     *     <li>Consistency is maintained across multiple invocations.</li>
     *     <li>A comment is not equal to {@code null} or an object of a different type.</li>
     *     <li>Comments with differing attributes are not equal.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> All equality properties hold as expected.</p>
     */
    @Test
    @DisplayName("Test equals() method")
    void testEquals() {
        // Reflexive
        assertEquals(comment, comment, "Comment should be equal to itself");

        // Symmetric
        assertEquals(comment, identicalComment, "Comments with identical fields should be equal");
        assertEquals(identicalComment, comment, "Symmetric equality failed");

        // Transitive
        Comment thirdComment = new Comment();
        thirdComment.setId(1L);
        thirdComment.setContent("This is a test comment.");
        thirdComment.setPost(post);
        thirdComment.setUser(user);
        thirdComment.setCreatedAt(null); // Will be set by onCreate()
        thirdComment.setUpdatedAt(null); // Will be set by onCreate()
        assertEquals(comment, identicalComment, "Comment equals identicalComment");
        assertEquals(identicalComment, thirdComment, "identicalComment equals thirdComment");
        assertEquals(comment, thirdComment, "Transitive equality failed");

        // Consistent
        assertEquals(comment, identicalComment, "Consistency check");
        assertEquals(comment, identicalComment, "Consistency check again");

        // Null comparison
        assertNotEquals(comment, null, "Comment should not be equal to null");

        // Different object types
        assertNotEquals(comment, "Some String", "Comment should not be equal to an unrelated object");

        // Unequal comments
        assertNotEquals(comment, differentComment, "Comments with different fields should not be equal");
    }

    /**
     * Tests the {@code hashCode()} method to ensure consistency with {@code equals()}.
     * <p>
     * Validates that:
     * <ul>
     *     <li>Equal comments have identical hash codes.</li>
     *     <li>Different comments have differing hash codes.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> Hash codes are consistent with equality results.</p>
     */
    @Test
    @DisplayName("Test hashCode() method")
    void testHashCode() {
        // Equal objects must have the same hash code
        assertEquals(comment.hashCode(), identicalComment.hashCode(), "Hash codes should match for equal comments");

        // Different objects may have different hash codes
        assertNotEquals(comment.hashCode(), differentComment.hashCode(), "Hash codes should differ for different comments");
    }

    /**
     * Tests the {@code toString()} method to ensure it includes all relevant fields.
     * <p>
     * Ensures that the string representation of a comment contains:
     * <ul>
     *     <li>Comment's ID</li>
     *     <li>Content</li>
     *     <li>Timestamps like {@code createdAt} and {@code updatedAt}</li>
     *     <li>Associations like {@code post} and {@code user}</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> {@code toString} output matches the expected format and contains all necessary fields.</p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        // Since 'post' and 'user' are included in toString(), ensure they are represented correctly
        String expected = "Comment(id=1, content=This is a test comment., createdAt=null, updatedAt=null, post=Post(id=1, title=Test Post), user=User(id=1, name=John Doe))";
        assertEquals(expected, comment.toString(), "toString output mismatch");
    }

    /**
     * Tests the {@code canEqual()} method to ensure proper equality checks.
     * <p>
     * Validates that:
     * <ul>
     *     <li>{@code canEqual} returns {@code true} when comparing with another {@link Comment} instance.</li>
     *     <li>{@code canEqual} returns {@code false} when comparing with an object of a different type.</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> {@code canEqual} behaves correctly based on the object type.</p>
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        // canEqual should return true for another Comment
        assertTrue(comment.canEqual(identicalComment), "canEqual should return true for another Comment");

        // canEqual should return false for different types
        assertFalse(comment.canEqual("Not a Comment"), "canEqual should return false for different types");
    }

    /**
     * Tests the PrePersist lifecycle callback {@code onCreate()} method.
     * <p>
     * Verifies that {@code onCreate()} correctly sets the {@code createdAt} and {@code updatedAt} fields.
     * </p>
     * 
     * <p><b>Pass Condition:</b> Both {@code createdAt} and {@code updatedAt} are set and equal.</p>
     * 
     * @throws Exception if reflection fails to access or invoke the method
     */
    @Test
    @DisplayName("Test onCreate() lifecycle callback")
    void testOnCreate() throws Exception {
        Comment prePersistComment = new Comment();
        prePersistComment.setContent("PrePersist test comment.");
        prePersistComment.setPost(post);
        prePersistComment.setUser(user);

        // Use reflection to invoke the protected onCreate() method
        Method onCreateMethod = Comment.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(prePersistComment);

        assertNotNull(prePersistComment.getCreatedAt(), "CreatedAt should be set by onCreate()");
        assertNotNull(prePersistComment.getUpdatedAt(), "UpdatedAt should be set by onCreate()");
        assertEquals(prePersistComment.getCreatedAt(), prePersistComment.getUpdatedAt(), "createdAt and updatedAt should be equal on creation");
    }

    /**
     * Tests the PreUpdate lifecycle callback {@code onUpdate()} method.
     * <p>
     * Verifies that {@code onUpdate()} correctly updates the {@code updatedAt} field.
     * </p>
     * 
     * <p><b>Pass Condition:</b> {@code updatedAt} is set and is after {@code createdAt}.</p>
     * 
     * @throws Exception if reflection fails to access or invoke the method
     */
    @Test
    @DisplayName("Test onUpdate() lifecycle callback")
    void testOnUpdate() throws Exception {
        Comment preUpdateComment = new Comment();
        preUpdateComment.setContent("PreUpdate test comment.");
        preUpdateComment.setPost(post);
        preUpdateComment.setUser(user);
        preUpdateComment.setCreatedAt(LocalDateTime.of(2025, 1, 1, 10, 0));
        preUpdateComment.setUpdatedAt(LocalDateTime.of(2025, 1, 1, 10, 0));

        // Simulate an update by invoking onUpdate()
        Method onUpdateMethod = Comment.class.getDeclaredMethod("onUpdate");
        onUpdateMethod.setAccessible(true);
        onUpdateMethod.invoke(preUpdateComment);

        assertNotNull(preUpdateComment.getUpdatedAt(), "UpdatedAt should be updated by onUpdate()");
        assertTrue(preUpdateComment.getUpdatedAt().isAfter(preUpdateComment.getCreatedAt()), "updatedAt should be after createdAt after update");
    }

    /**
     * Tests adding and removing Post associations.
     * <p>
     * Ensures that:
     * <ul>
     *     <li>A {@link Comment} can be associated with a {@link Post}.</li>
     *     <li>The association can be updated to a different {@link Post}.</li>
     *     <li>The association can be removed (set to {@code null}).</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> The {@code post} field reflects the changes correctly.</p>
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
        comment.setPost(newPost);
        assertEquals(newPost, comment.getPost(), "Post should be updated to newPost");

        // Remove post association
        comment.setPost(null);
        assertNull(comment.getPost(), "Post should be null after removal");
    }

    /**
     * Tests adding and removing User associations.
     * <p>
     * Ensures that:
     * <ul>
     *     <li>A {@link Comment} can be associated with a {@link User}.</li>
     *     <li>The association can be updated to a different {@link User}.</li>
     *     <li>The association can be removed (set to {@code null}).</li>
     * </ul>
     * </p>
     * 
     * <p><b>Pass Condition:</b> The {@code user} field reflects the changes correctly.</p>
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
        newUser.setPosts(new ArrayList<>());
        newUser.setComments(new ArrayList<>());
        newUser.setLikes(new ArrayList<>());
        newUser.setNotifications(new ArrayList<>());

        // Update user association
        comment.setUser(newUser);
        assertEquals(newUser, comment.getUser(), "User should be updated to newUser");

        // Remove user association
        comment.setUser(null);
        assertNull(comment.getUser(), "User should be null after removal");
    }
}
