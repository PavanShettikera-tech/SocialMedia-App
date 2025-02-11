package com.socialmedia.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link PostDTO}.
 *
 * <p><strong>Premise:</strong>
 * Ensures that getters, setters, equals(), hashCode(), etc., function properly for a typical Post DTO usage.
 *
 * <p><strong>Error Conditions / Acceptable Values:</strong></p>
 * <ul>
 *   <li>Title and content generally non-null, but these tests mainly check POJO functionality, not validation logic.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li>Pass: If all standard Lombok or constructor behaviors match expectations.</li>
 *   <li>Fail: If any property is incorrectly set or methods fail to align with the same field values.</li>
 * </ul>
 */
class PostDTOTest {

    private PostDTO postDTO;

    @BeforeEach
    void setUp() {
        postDTO = new PostDTO();
    }

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

    @Test
    @DisplayName("Test all-args constructor in PostDTO")
    void testAllArgsConstructor() {
        PostDTO dto = new PostDTO(2L, "Another Title", "Another Content", 2L);
        assertEquals(2L, dto.getId());
        assertEquals("Another Title", dto.getTitle());
        assertEquals("Another Content", dto.getContent());
        assertEquals(2L, dto.getUserId());
    }

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

    @Test
    @DisplayName("Test canEqual() method in PostDTO")
    void testCanEqual() {
        PostDTO dto1 = new PostDTO();
        PostDTO dto2 = new PostDTO();
        Object obj = new Object();

        assertTrue(dto1.canEqual(dto2));
        assertFalse(dto1.canEqual(obj));
    }

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
