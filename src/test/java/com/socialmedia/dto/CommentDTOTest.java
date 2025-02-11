package com.socialmedia.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link CommentDTO}.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link CommentDTO} class,
 * including its getters, setters, constructors, {@code equals()}, {@code hashCode()},
 * and {@code toString()} methods. It ensures that the DTO behaves as expected under
 * various scenarios.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class CommentDTOTest {

    private CommentDTO comment1;
    private CommentDTO comment2;
    private CommentDTO comment3;

    /**
     * Initializes sample {@link CommentDTO} instances before each test.
     */
    @BeforeEach
    void setUp() {
        comment1 = new CommentDTO(1L, "Great post!", 10L, 100L);
        comment2 = new CommentDTO(1L, "Great post!", 10L, 100L);
        comment3 = new CommentDTO(2L, "Nice!", 20L, 200L);
    }

    /**
     * Tests the {@code equals()} and {@code hashCode()} methods of {@link CommentDTO}.
     * <p>
     * Verifies that two identical DTOs are equal and have the same hash code,
     * while different DTOs are not equal and have different hash codes.
     * </p>
     */
    @Test
    @DisplayName("Test equals() and hashCode() methods")
    void testEqualsAndHashCode() {
        // Equality tests
        assertTrue(comment1.equals(comment2), "comment1 should be equal to comment2");
        assertFalse(comment1.equals(comment3), "comment1 should not be equal to comment3");
        assertFalse(comment1.equals(null), "comment1 should not be equal to null");
        assertFalse(comment1.equals(new Object()), "comment1 should not be equal to an unrelated object");

        // HashCode consistency
        assertEquals(comment1.hashCode(), comment2.hashCode(), "Hash codes of comment1 and comment2 should be equal");
        assertNotEquals(comment1.hashCode(), comment3.hashCode(), "Hash codes of comment1 and comment3 should not be equal");
    }

    /**
     * Tests field comparisons for {@link CommentDTO}.
     * <p>
     * Verifies that changing individual fields affects equality as expected.
     * </p>
     */
    @Test
    @DisplayName("Test field comparisons affecting equality")
    void testFieldComparisonBranches() {
        CommentDTO base = new CommentDTO(1L, "Base comment", 5L, 50L);

        // Test different ID
        CommentDTO differentId = new CommentDTO(2L, "Base comment", 5L, 50L);
        assertFalse(base.equals(differentId), "DTOs with different IDs should not be equal");

        // Test different content
        CommentDTO differentContent = new CommentDTO(1L, "Different", 5L, 50L);
        assertFalse(base.equals(differentContent), "DTOs with different content should not be equal");

        // Test different postId
        CommentDTO differentPostId = new CommentDTO(1L, "Base comment", 6L, 50L);
        assertFalse(base.equals(differentPostId), "DTOs with different postIds should not be equal");

        // Test different userId
        CommentDTO differentUserId = new CommentDTO(1L, "Base comment", 5L, 60L);
        assertFalse(base.equals(differentUserId), "DTOs with different userIds should not be equal");
    }

    /**
     * Tests handling of null fields in {@link CommentDTO}.
     * <p>
     * Verifies that DTOs with null fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of null fields in equals()")
    void testNullFieldHandling() {
        CommentDTO nullComment = new CommentDTO(null, null, null, null);
        CommentDTO populated = new CommentDTO(1L, "Test", 5L, 10L);

        // Null vs non-null comparisons
        assertFalse(nullComment.equals(populated), "DTO with null fields should not be equal to populated DTO");
        assertFalse(populated.equals(nullComment), "Populated DTO should not be equal to DTO with null fields");

        // Both null fields comparison
        CommentDTO anotherNullComment = new CommentDTO(null, null, null, null);
        assertTrue(nullComment.equals(anotherNullComment), "DTOs with all fields null should be equal");
    }

    /**
     * Tests the {@code toString()} method of {@link CommentDTO}.
     * <p>
     * Verifies that the string representation contains all relevant fields.
     * </p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        CommentDTO comment = new CommentDTO(1L, "Test content", 5L, 10L);
        String str = comment.toString();

        assertTrue(str.contains("id=1"), "toString() should contain the ID");
        assertTrue(str.contains("content=Test content"), "toString() should contain the content");
        assertTrue(str.contains("postId=5"), "toString() should contain the postId");
        assertTrue(str.contains("userId=10"), "toString() should contain the userId");
    }

    /**
     * Tests the {@code canEqual()} method of {@link CommentDTO}.
     * <p>
     * Verifies that {@code canEqual()} behaves correctly when comparing with the same class and different classes.
     * </p>
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        CommentDTO comment = new CommentDTO();
        assertTrue(comment.canEqual(new CommentDTO()), "canEqual() should return true for the same class");
        assertFalse(comment.canEqual(new Object()), "canEqual() should return false for different classes");
    }

    /**
     * Tests the getters and setters of {@link CommentDTO}.
     * <p>
     * Verifies that each field can be set and retrieved correctly.
     * </p>
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        CommentDTO comment = new CommentDTO();
        comment.setId(5L);
        comment.setContent("New content");
        comment.setPostId(15L);
        comment.setUserId(20L);

        assertEquals(5L, comment.getId(), "Getter should return the set ID");
        assertEquals("New content", comment.getContent(), "Getter should return the set content");
        assertEquals(15L, comment.getPostId(), "Getter should return the set postId");
        assertEquals(20L, comment.getUserId(), "Getter should return the set userId");
    }

    /**
     * Tests the constructors of {@link CommentDTO}.
     * <p>
     * Verifies that the default and parameterized constructors initialize fields correctly.
     * </p>
     */
    @Test
    @DisplayName("Test constructors")
    void testConstructors() {
        // Test default constructor
        CommentDTO empty = new CommentDTO();
        assertNull(empty.getId(), "Default constructor should initialize ID to null");

        // Test parameterized constructor
        CommentDTO full = new CommentDTO(1L, "Test", 5L, 10L);
        assertEquals(1L, full.getId(), "Parameterized constructor should set the ID correctly");
        assertEquals("Test", full.getContent(), "Parameterized constructor should set the content correctly");
        assertEquals(5L, full.getPostId(), "Parameterized constructor should set the postId correctly");
        assertEquals(10L, full.getUserId(), "Parameterized constructor should set the userId correctly");
    }

    /**
     * Tests handling of edge cases with string fields in {@link CommentDTO}.
     * <p>
     * Verifies that DTOs with empty or null string fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of empty strings in fields")
    void testStringFieldEdgeCases() {
        CommentDTO emptyContent = new CommentDTO(1L, "", 5L, 10L);
        CommentDTO nullContent = new CommentDTO(1L, null, 5L, 10L);

        assertFalse(emptyContent.equals(nullContent), "DTO with empty content should not equal DTO with null content");
        assertFalse(nullContent.equals(emptyContent), "DTO with null content should not equal DTO with empty content");
    }
}
