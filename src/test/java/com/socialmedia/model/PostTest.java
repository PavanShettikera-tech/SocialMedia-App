package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    private Post post1;
    private Post post2;
    private User user;

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

    @Test
    @DisplayName("Post can update fields like title")
    void testPostUpdateFields() {
        post1.setTitle("Updated Title");
        assertEquals("Updated Title", post1.getTitle());
    }

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
    @Test
    @DisplayName("Equals verification: same object")
    void testEqualsSameObject() {
        assertTrue(post1.equals(post1));
    }

    @Test
    @DisplayName("Equals verification: equal objects")
    void testEqualsEqualObjects() {
        // post1 and post2 are identical
        assertEquals(post1, post2);
    }

    @Test
    @DisplayName("Equals verification: different ID")
    void testEqualsDifferentId() {
        post2.setId(2L);
        assertNotEquals(post1, post2, "Posts should differ if IDs differ");
    }

   

    @Test
    @DisplayName("Equals verification: null comparison")
    void testEqualsNull() {
        assertNotEquals(null, post1, "A post is never equal to null");
    }

    @Test
    @DisplayName("Equals verification: different class")
    void testEqualsDifferentClass() {
        assertNotEquals(post1, new Object(), "Post is not equal to a different class");
    }

    @Test
    @DisplayName("HashCode consistency for identical objects")
    void testHashCodeConsistency() {
        assertEquals(post1.hashCode(), post2.hashCode(),
            "Hash codes should match for identical posts");
    }

    @Test
    @DisplayName("HashCode difference for different ID")
    void testHashCodeDifference() {
        post2.setId(999L);
        assertNotEquals(post1.hashCode(), post2.hashCode(),
            "Hash codes should differ if IDs differ");
    }

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

    @Test
    @DisplayName("canEqual verification: same type => true, different type => false")
    void testCanEqual() {
        assertTrue(post1.canEqual(post2), "Post should canEqual another Post");
        // canEqual should return false if we pass an object of different type
        assertFalse(post1.canEqual(new User()), "Post should not canEqual a User");
    }

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
