package com.socialmedia.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link PostDTO}.
 *
 * <p><strong>Premise:</strong>
 * Ensures that getters, setters, equals(), hashCode(), and other standard methods function properly for a typical PostDTO usage.
 *
 * <p><strong>Error Conditions / Acceptable Values:</strong></p>
 * <ul>
 *   <li>Title and content are generally non-null; however, these tests focus on POJO functionality rather than validation logic.</li>
 *   <li>ID and userId should be positive long values.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li><strong>Pass:</strong> All standard Lombok-generated methods or constructor behaviors behave as expected.</li>
 *   <li><strong>Fail:</strong> Any property is incorrectly set, or methods do not align with the expected field values.</li>
 * </ul>
 */
class PostDTOTest {

    private PostDTO postDTO;

    /**
     * Sets up a new instance of {@link PostDTO} before each test.
     *
     * <p><strong>Premise:</strong> Initializes the PostDTO object to ensure a fresh state for each test case.
     */
    @BeforeEach
    void setUp() {
        postDTO = new PostDTO();
    }

    /**
     * Tests the getters and setters of {@link PostDTO}.
     *
     * <p><strong>Parameters:</strong>
     * <ul>
     *   <li><code>id</code> - {@code Long} representing the post ID. Acceptable range: positive values.</li>
     *   <li><code>title</code> - {@code String} representing the post title. Should be non-null.</li>
     *   <li><code>content</code> - {@code String} representing the post content. Should be non-null.</li>
     *   <li><code>userId</code> - {@code Long} representing the user ID. Acceptable range: positive values.</li>
     * </ul>
     *
     * <p><strong>Assertions:</strong>
     * Validates that each setter correctly assigns the value and each getter retrieves the expected value.
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> If all getters return values matching those set by the setters.</li>
     *   <li><strong>Fail:</strong> If any getter does not return the expected value.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters in PostDTO")
    void testSettersAndGetters() {
        postDTO.setId(1L);
        postDTO.setTitle("Test Title");
        postDTO.setContent("Test Content");
        postDTO.setUserId(1L);

        assertEquals(1L, postDTO.getId());
        assertEquals("Test Title", postDTO.getTitle());
        assertEquals("Test Content", postDTO.getContent());
        assertEquals(1L, postDTO.getUserId());
    }

    /**
     * Tests the all-arguments constructor of {@link PostDTO}.
     *
     * <p><strong>Parameters:</strong>
     * <ul>
     *   <li><code>id</code> - {@code Long} representing the post ID. Acceptable range: positive values.</li>
     *   <li><code>title</code> - {@code String} representing the post title. Should be non-null.</li>
     *   <li><code>content</code> - {@code String} representing the post content. Should be non-null.</li>
     *   <li><code>userId</code> - {@code Long} representing the user ID. Acceptable range: positive values.</li>
     * </ul>
     *
     * <p><strong>Assertions:</strong>
     * Ensures that the constructor correctly assigns all fields.
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> If all fields are initialized with the provided values.</li>
     *   <li><strong>Fail:</strong> If any field does not match the provided constructor arguments.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test all-args constructor in PostDTO")
    void testAllArgsConstructor() {
        PostDTO dto = new PostDTO(2L, "Another Title", "Another Content", 2L);
        assertEquals(2L, dto.getId());
        assertEquals("Another Title", dto.getTitle());
        assertEquals("Another Content", dto.getContent());
        assertEquals(2L, dto.getUserId());
    }

    /**
     * Tests the {@code toString()} method of {@link PostDTO}.
     *
     * <p><strong>Assertions:</strong>
     * Verifies that the string representation contains all field values.
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> If the {@code toString()} output includes all set field values.</li>
     *   <li><strong>Fail:</strong> If any field value is missing or incorrectly represented in the output.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test toString() in PostDTO")
    void testToString() {
        postDTO.setId(1L);
        postDTO.setTitle("Test Title");
        postDTO.setContent("Test Content");
        postDTO.setUserId(1L);

        String toString = postDTO.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("title=Test Title"));
        assertTrue(toString.contains("content=Test Content"));
        assertTrue(toString.contains("userId=1"));
    }

    /**
     * Tests the {@code equals()} and {@code hashCode()} methods of {@link PostDTO}.
     *
     * <p><strong>Assertions:</strong>
     * Ensures that two objects with identical fields are equal and have the same hash code,
     * and that objects with differing fields are not equal.
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> If equal objects are recognized as equal and have matching hash codes,
     *       and unequal objects are correctly identified as not equal.</li>
     *   <li><strong>Fail:</strong> If equal objects are not recognized as equal, hash codes differ,
     *       or unequal objects are mistakenly identified as equal.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test equals() and hashCode() in PostDTO")
    void testEqualsAndHashCode() {
        PostDTO dto1 = new PostDTO(1L, "Title", "Content", 1L);
        PostDTO dto2 = new PostDTO(1L, "Title", "Content", 1L);
        PostDTO dto3 = new PostDTO(2L, "Diff", "Diff", 2L);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    /**
     * Tests the {@code canEqual()} method of {@link PostDTO}.
     *
     * <p><strong>Parameters:</strong>
     * <ul>
     *   <li><code>other</code> - {@link Object} to compare with for equality.</li>
     * </ul>
     *
     * <p><strong>Assertions:</strong>
     * Checks if the current instance can be equal to another instance of {@link PostDTO} and rejects comparison with unrelated types.
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> If {@code canEqual} returns {@code true} for another {@link PostDTO} instance
     *       and {@code false} for objects of different types.</li>
     *   <li><strong>Fail:</strong> If {@code canEqual} does not behave as expected based on the object's type.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test canEqual() method in PostDTO")
    void testCanEqual() {
        PostDTO dto1 = new PostDTO();
        PostDTO dto2 = new PostDTO();
        Object obj = new Object();

        assertTrue(dto1.canEqual(dto2));
        assertFalse(dto1.canEqual(obj));
    }

    /**
     * Tests the {@code equals()} method of {@link PostDTO} when fields are {@code null}.
     *
     * <p><strong>Assertions:</strong>
     * Ensures that two {@link PostDTO} instances with all fields set to {@code null} are equal,
     * and that a {@link PostDTO} with {@code null} fields is not equal to one with non-null fields.
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> If two DTOs with all fields as {@code null} are considered equal
     *       and a DTO with non-null fields is not equal to one with {@code null} fields.</li>
     *   <li><strong>Fail:</strong> If equality checks do not correctly handle {@code null} fields.</li>
     * </ul>
     */
    @Test
    @DisplayName("Test equals() with null fields in PostDTO")
    void testEqualsWithNullFields() {
        PostDTO dto1 = new PostDTO(null, null, null, null);
        PostDTO dto2 = new PostDTO(null, null, null, null);
        PostDTO dto3 = new PostDTO(1L, "Title", "Content", 1L);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
    }
}
