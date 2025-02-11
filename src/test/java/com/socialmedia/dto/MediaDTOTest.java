package com.socialmedia.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link MediaDTO}.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link MediaDTO} class,
 * including its getters, setters, constructors, {@code equals()}, {@code hashCode()},
 * and {@code toString()} methods. It ensures that the DTO behaves as expected under
 * various scenarios.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class MediaDTOTest {

    private MediaDTO media1;
    private MediaDTO media2;
    private MediaDTO media3;

    /**
     * Initializes sample {@link MediaDTO} instances before each test.
     * <p>
     * Sets up three instances of {@link MediaDTO}:
     * <ul>
     *   <li>{@code media1} and {@code media2} are identical.</li>
     *   <li>{@code media3} is different from {@code media1} and {@code media2}.</li>
     * </ul>
     * </p>
     */
    @BeforeEach
    void setUp() {
        media1 = new MediaDTO(1L, "url1", "image", 10L);
        media2 = new MediaDTO(1L, "url1", "image", 10L);
        media3 = new MediaDTO(2L, "url2", "video", 20L);
    }

    /**
     * Tests the {@code equals()} and {@code hashCode()} methods of {@link MediaDTO}.
     * <p>
     * Verifies that two identical DTOs are equal and have the same hash code,
     * while different DTOs are not equal and have different hash codes.
     * </p>
     */
    @Test
    @DisplayName("Test equals() and hashCode() methods")
    void testEqualsAndHashCode() {
        // Equality tests
        assertTrue(media1.equals(media2), "media1 should be equal to media2");
        assertFalse(media1.equals(media3), "media1 should not be equal to media3");
        assertFalse(media1.equals(null), "media1 should not be equal to null");
        assertFalse(media1.equals(new Object()), "media1 should not be equal to an unrelated object");

        // HashCode consistency
        assertEquals(media1.hashCode(), media2.hashCode(), "Hash codes of media1 and media2 should be equal");
        assertNotEquals(media1.hashCode(), media3.hashCode(), "Hash codes of media1 and media3 should not be equal");
    }

    /**
     * Tests field comparisons for {@link MediaDTO}.
     * <p>
     * Verifies that changing individual fields affects equality as expected.
     * </p>
     */
    @Test
    @DisplayName("Test field comparisons affecting equality")
    void testFieldComparisonBranches() {
        MediaDTO base = new MediaDTO(1L, "url", "image", 10L);

        // Test different ID
        MediaDTO differentId = new MediaDTO(2L, "url", "image", 10L);
        assertFalse(base.equals(differentId), "DTOs with different IDs should not be equal");

        // Test different URL
        MediaDTO differentUrl = new MediaDTO(1L, "different", "image", 10L);
        assertFalse(base.equals(differentUrl), "DTOs with different URLs should not be equal");

        // Test different type
        MediaDTO differentType = new MediaDTO(1L, "url", "video", 10L);
        assertFalse(base.equals(differentType), "DTOs with different types should not be equal");

        // Test different postId
        MediaDTO differentPostId = new MediaDTO(1L, "url", "image", 20L);
        assertFalse(base.equals(differentPostId), "DTOs with different postIds should not be equal");
    }

    /**
     * Tests handling of null fields in {@link MediaDTO}.
     * <p>
     * Verifies that DTOs with null fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of null fields in equals()")
    void testNullFieldHandling() {
        MediaDTO nullMedia = new MediaDTO(null, null, null, null);
        MediaDTO populated = new MediaDTO(1L, "url", "image", 10L);

        // Null vs non-null comparisons
        assertFalse(nullMedia.equals(populated), "DTO with null fields should not be equal to populated DTO");
        assertFalse(populated.equals(nullMedia), "Populated DTO should not be equal to DTO with null fields");

        // Both null fields comparison
        MediaDTO anotherNullMedia = new MediaDTO(null, null, null, null);
        assertTrue(nullMedia.equals(anotherNullMedia), "DTOs with all fields null should be equal");
    }

    /**
     * Tests the {@code toString()} method of {@link MediaDTO}.
     * <p>
     * Verifies that the string representation contains all relevant fields.
     * </p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        MediaDTO media = new MediaDTO(1L, "test.url", "image", 10L);
        String str = media.toString();

        assertTrue(str.contains("id=1"), "toString() should contain the ID");
        assertTrue(str.contains("url=test.url"), "toString() should contain the URL");
        assertTrue(str.contains("type=image"), "toString() should contain the type");
        assertTrue(str.contains("postId=10"), "toString() should contain the postId");
    }

    /**
     * Tests the {@code canEqual()} method of {@link MediaDTO}.
     * <p>
     * Verifies that {@code canEqual()} behaves correctly when comparing with the same class and different classes.
     * </p>
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        MediaDTO media = new MediaDTO();
        assertTrue(media.canEqual(new MediaDTO()), "canEqual() should return true for the same class");
        assertFalse(media.canEqual(new Object()), "canEqual() should return false for different classes");
    }

    /**
     * Tests the getters and setters of {@link MediaDTO}.
     * <p>
     * Verifies that each field can be set and retrieved correctly.
     * </p>
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        MediaDTO media = new MediaDTO();
        media.setId(5L);
        media.setUrl("new.url");
        media.setType("video");
        media.setPostId(15L);

        assertEquals(5L, media.getId(), "Getter should return the set ID");
        assertEquals("new.url", media.getUrl(), "Getter should return the set URL");
        assertEquals("video", media.getType(), "Getter should return the set type");
        assertEquals(15L, media.getPostId(), "Getter should return the set postId");
    }

    /**
     * Tests the constructors of {@link MediaDTO}.
     * <p>
     * Verifies that the default and parameterized constructors initialize fields correctly.
     * </p>
     */
    @Test
    @DisplayName("Test constructors")
    void testConstructors() {
        // Test default constructor
        MediaDTO empty = new MediaDTO();
        assertNull(empty.getId(), "Default constructor should initialize ID to null");
        assertNull(empty.getUrl(), "Default constructor should initialize URL to null");
        assertNull(empty.getType(), "Default constructor should initialize type to null");
        assertNull(empty.getPostId(), "Default constructor should initialize postId to null");

        // Test parameterized constructor
        MediaDTO full = new MediaDTO(1L, "url", "image", 10L);
        assertEquals(1L, full.getId(), "Parameterized constructor should set the ID correctly");
        assertEquals("url", full.getUrl(), "Parameterized constructor should set the URL correctly");
        assertEquals("image", full.getType(), "Parameterized constructor should set the type correctly");
        assertEquals(10L, full.getPostId(), "Parameterized constructor should set the postId correctly");
    }

    /**
     * Tests handling of partial null fields in {@link MediaDTO}.
     * <p>
     * Verifies that DTOs with some null fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of partial null fields")
    void testPartialNullFields() {
        // Correct constructor usage with null fields where appropriate
        MediaDTO partialNull1 = new MediaDTO(null, "url", "image", 10L); // Null ID
        MediaDTO partialNull2 = new MediaDTO(1L, null, "image", 10L);     // Null URL
        MediaDTO partialNull3 = new MediaDTO(1L, "url", null, 10L);       // Null Type
        MediaDTO partialNull4 = new MediaDTO(1L, "url", "image", null);   // Null postId

        // Assertions to verify that DTOs with different null fields are not equal
        assertFalse(partialNull1.equals(partialNull2), "DTOs with different partial nulls should not be equal");
        assertFalse(partialNull2.equals(partialNull3), "DTOs with different partial nulls should not be equal");
        assertFalse(partialNull3.equals(partialNull4), "DTOs with different partial nulls should not be equal");
        assertFalse(partialNull4.equals(partialNull1), "DTOs with different partial nulls should not be equal");
    }
}
