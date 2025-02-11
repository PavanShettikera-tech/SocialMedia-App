package com.socialmedia.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link LikeDTO}.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link LikeDTO} class,
 * including its getters, setters, constructors, {@code equals()}, {@code hashCode()},
 * and {@code toString()} methods. It ensures that the DTO behaves as expected under
 * various scenarios.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class LikeDTOTest {

    private LikeDTO like1;
    private LikeDTO like2;
    private LikeDTO like3;

    /**
     * Initializes sample {@link LikeDTO} instances before each test.
     */
    @BeforeEach
    void setUp() {
        like1 = new LikeDTO(1L, 10L, 100L);
        like2 = new LikeDTO(1L, 10L, 100L);
        like3 = new LikeDTO(2L, 20L, 200L);
    }

    /**
     * Tests the {@code equals()} and {@code hashCode()} methods of {@link LikeDTO}.
     * <p>
     * Verifies that two identical DTOs are equal and have the same hash code,
     * while different DTOs are not equal and have different hash codes.
     * </p>
     */
    @Test
    @DisplayName("Test equals() and hashCode() methods")
    void testEqualsAndHashCode() {
        // Equality tests
        assertTrue(like1.equals(like2), "like1 should be equal to like2");
        assertFalse(like1.equals(like3), "like1 should not be equal to like3");
        assertFalse(like1.equals(null), "like1 should not be equal to null");
        assertFalse(like1.equals(new Object()), "like1 should not be equal to an unrelated object");

        // HashCode consistency
        assertEquals(like1.hashCode(), like2.hashCode(), "Hash codes of like1 and like2 should be equal");
        assertNotEquals(like1.hashCode(), like3.hashCode(), "Hash codes of like1 and like3 should not be equal");
    }

    /**
     * Tests field comparisons for {@link LikeDTO}.
     * <p>
     * Verifies that changing individual fields affects equality as expected.
     * </p>
     */
    @Test
    @DisplayName("Test field comparisons affecting equality")
    void testFieldComparisonBranches() {
        LikeDTO base = new LikeDTO(1L, 5L, 10L);

        // Test different ID
        LikeDTO differentId = new LikeDTO(2L, 5L, 10L);
        assertFalse(base.equals(differentId), "DTOs with different IDs should not be equal");

        // Test different postId
        LikeDTO differentPostId = new LikeDTO(1L, 6L, 10L);
        assertFalse(base.equals(differentPostId), "DTOs with different postIds should not be equal");

        // Test different userId
        LikeDTO differentUserId = new LikeDTO(1L, 5L, 11L);
        assertFalse(base.equals(differentUserId), "DTOs with different userIds should not be equal");
    }

    /**
     * Tests handling of null fields in {@link LikeDTO}.
     * <p>
     * Verifies that DTOs with null fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of null fields in equals()")
    void testNullFieldHandling() {
        LikeDTO nullLike = new LikeDTO(null, null, null);
        LikeDTO populated = new LikeDTO(1L, 5L, 10L);

        // Null vs non-null comparisons
        assertFalse(nullLike.equals(populated), "DTO with null fields should not be equal to populated DTO");
        assertFalse(populated.equals(nullLike), "Populated DTO should not be equal to DTO with null fields");

        // Both null fields comparison
        LikeDTO anotherNullLike = new LikeDTO(null, null, null);
        assertTrue(nullLike.equals(anotherNullLike), "DTOs with all fields null should be equal");
    }

    /**
     * Tests the {@code toString()} method of {@link LikeDTO}.
     * <p>
     * Verifies that the string representation contains all relevant fields.
     * </p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        LikeDTO like = new LikeDTO(1L, 5L, 10L);
        String str = like.toString();

        assertTrue(str.contains("id=1"), "toString() should contain the ID");
        assertTrue(str.contains("postId=5"), "toString() should contain the postId");
        assertTrue(str.contains("userId=10"), "toString() should contain the userId");
    }

    /**
     * Tests the {@code canEqual()} method of {@link LikeDTO}.
     * <p>
     * Verifies that {@code canEqual()} behaves correctly when comparing with the same class and different classes.
     * </p>
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        LikeDTO like = new LikeDTO();
        assertTrue(like.canEqual(new LikeDTO()), "canEqual() should return true for the same class");
        assertFalse(like.canEqual(new Object()), "canEqual() should return false for different classes");
    }

    /**
     * Tests the getters and setters of {@link LikeDTO}.
     * <p>
     * Verifies that each field can be set and retrieved correctly.
     * </p>
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        LikeDTO like = new LikeDTO();
        like.setId(5L);
        like.setPostId(15L);
        like.setUserId(20L);

        assertEquals(5L, like.getId(), "Getter should return the set ID");
        assertEquals(15L, like.getPostId(), "Getter should return the set postId");
        assertEquals(20L, like.getUserId(), "Getter should return the set userId");
    }

    /**
     * Tests the constructors of {@link LikeDTO}.
     * <p>
     * Verifies that the default and parameterized constructors initialize fields correctly.
     * </p>
     */
    @Test
    @DisplayName("Test constructors")
    void testConstructors() {
        // Test default constructor
        LikeDTO empty = new LikeDTO();
        assertNull(empty.getId(), "Default constructor should initialize ID to null");
        assertNull(empty.getPostId(), "Default constructor should initialize postId to null");
        assertNull(empty.getUserId(), "Default constructor should initialize userId to null");

        // Test parameterized constructor
        LikeDTO full = new LikeDTO(1L, 5L, 10L);
        assertEquals(1L, full.getId(), "Parameterized constructor should set the ID correctly");
        assertEquals(5L, full.getPostId(), "Parameterized constructor should set the postId correctly");
        assertEquals(10L, full.getUserId(), "Parameterized constructor should set the userId correctly");
    }

    /**
     * Tests handling of partial null fields in {@link LikeDTO}.
     * <p>
     * Verifies that DTOs with some null fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of partial null fields")
    void testPartialNullFields() {
        LikeDTO partialNull1 = new LikeDTO(null, 5L, 10L);
        LikeDTO partialNull2 = new LikeDTO(1L, null, 10L);
        LikeDTO partialNull3 = new LikeDTO(1L, 5L, null);

        assertFalse(partialNull1.equals(partialNull2), "DTOs with different partial nulls should not be equal");
        assertFalse(partialNull2.equals(partialNull3), "DTOs with different partial nulls should not be equal");
        assertFalse(partialNull3.equals(partialNull1), "DTOs with different partial nulls should not be equal");
    }
}
