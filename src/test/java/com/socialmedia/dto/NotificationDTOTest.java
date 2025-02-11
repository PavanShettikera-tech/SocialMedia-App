package com.socialmedia.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link NotificationDTO}.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link NotificationDTO} class,
 * including its getters, setters, constructors, {@code equals()}, {@code hashCode()},
 * and {@code toString()} methods. It ensures that the DTO behaves as expected under
 * various scenarios.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class NotificationDTOTest {

    private NotificationDTO notif1;
    private NotificationDTO notif2;
    private NotificationDTO notif3;

    /**
     * Initializes sample {@link NotificationDTO} instances before each test.
     */
    @BeforeEach
    void setUp() {
        notif1 = new NotificationDTO(1L, "Message", 10L, false);
        notif2 = new NotificationDTO(1L, "Message", 10L, false);
        notif3 = new NotificationDTO(2L, "Different", 20L, true);
    }

    /**
     * Tests the {@code equals()} and {@code hashCode()} methods of {@link NotificationDTO}.
     * <p>
     * Verifies that two identical DTOs are equal and have the same hash code,
     * while different DTOs are not equal and have different hash codes.
     * </p>
     */
    @Test
    @DisplayName("Test equals() and hashCode() methods")
    void testEqualsAndHashCode() {
        // Equality tests
        assertTrue(notif1.equals(notif2), "notif1 should be equal to notif2");
        assertFalse(notif1.equals(notif3), "notif1 should not be equal to notif3");
        assertFalse(notif1.equals(null), "notif1 should not be equal to null");
        assertFalse(notif1.equals(new Object()), "notif1 should not be equal to an unrelated object");

        // HashCode consistency
        assertEquals(notif1.hashCode(), notif2.hashCode(), "Hash codes of notif1 and notif2 should be equal");
        assertNotEquals(notif1.hashCode(), notif3.hashCode(), "Hash codes of notif1 and notif3 should not be equal");
    }

    /**
     * Tests field comparisons for {@link NotificationDTO}.
     * <p>
     * Verifies that changing individual fields affects equality as expected.
     * </p>
     */
    @Test
    @DisplayName("Test field comparisons affecting equality")
    void testFieldComparisonBranches() {
        NotificationDTO base = new NotificationDTO(1L, "Test", 5L, true);

        // Test different ID
        NotificationDTO differentId = new NotificationDTO(2L, "Test", 5L, true);
        assertFalse(base.equals(differentId), "DTOs with different IDs should not be equal");

        // Test different message
        NotificationDTO differentMessage = new NotificationDTO(1L, "Different", 5L, true);
        assertFalse(base.equals(differentMessage), "DTOs with different messages should not be equal");

        // Test different userId
        NotificationDTO differentUserId = new NotificationDTO(1L, "Test", 6L, true);
        assertFalse(base.equals(differentUserId), "DTOs with different userIds should not be equal");

        // Test different read status
        NotificationDTO differentRead = new NotificationDTO(1L, "Test", 5L, false);
        assertFalse(base.equals(differentRead), "DTOs with different read statuses should not be equal");
    }

    /**
     * Tests handling of null fields in {@link NotificationDTO}.
     * <p>
     * Verifies that DTOs with null fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of null fields in equals()")
    void testNullFieldHandling() {
        NotificationDTO nullNotif = new NotificationDTO(null, null, null, null);
        NotificationDTO populated = new NotificationDTO(1L, "Msg", 10L, true);

        // Null vs non-null comparisons
        assertFalse(nullNotif.equals(populated), "DTO with null fields should not be equal to populated DTO");
        assertFalse(populated.equals(nullNotif), "Populated DTO should not be equal to DTO with null fields");

        // Both null fields comparison
        NotificationDTO anotherNullNotif = new NotificationDTO(null, null, null, null);
        assertTrue(nullNotif.equals(anotherNullNotif), "DTOs with all fields null should be equal");
    }

    /**
     * Tests the {@code toString()} method of {@link NotificationDTO}.
     * <p>
     * Verifies that the string representation contains all relevant fields.
     * </p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        NotificationDTO notif = new NotificationDTO(1L, "Hello", 5L, false);
        String str = notif.toString();

        assertTrue(str.contains("id=1"), "toString() should contain the ID");
        assertTrue(str.contains("message=Hello"), "toString() should contain the message");
        assertTrue(str.contains("userId=5"), "toString() should contain the userId");
        assertTrue(str.contains("read=false"), "toString() should contain the read status");
    }

    /**
     * Tests the {@code canEqual()} method of {@link NotificationDTO}.
     * <p>
     * Verifies that {@code canEqual()} behaves correctly when comparing with the same class and different classes.
     * </p>
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        NotificationDTO notif = new NotificationDTO();
        assertTrue(notif.canEqual(new NotificationDTO()), "canEqual() should return true for the same class");
        assertFalse(notif.canEqual(new Object()), "canEqual() should return false for different classes");
    }

    /**
     * Tests the getters and setters of {@link NotificationDTO}.
     * <p>
     * Verifies that each field can be set and retrieved correctly.
     * </p>
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        NotificationDTO notif = new NotificationDTO();
        notif.setId(5L);
        notif.setMessage("New Message");
        notif.setUserId(15L);
        notif.setRead(true);

        assertEquals(5L, notif.getId(), "Getter should return the set ID");
        assertEquals("New Message", notif.getMessage(), "Getter should return the set message");
        assertEquals(15L, notif.getUserId(), "Getter should return the set userId");
        assertTrue(notif.getRead(), "Getter should return the set read status");
    }

    /**
     * Tests the constructors of {@link NotificationDTO}.
     * <p>
     * Verifies that the default and parameterized constructors initialize fields correctly.
     * </p>
     */
    @Test
    @DisplayName("Test constructors")
    void testConstructors() {
        // Test default constructor
        NotificationDTO empty = new NotificationDTO();
        assertNull(empty.getId(), "Default constructor should initialize ID to null");
        assertNull(empty.getMessage(), "Default constructor should initialize message to null");
        assertNull(empty.getUserId(), "Default constructor should initialize userId to null");
        assertNull(empty.getRead(), "Default constructor should initialize read status to null");

        // Test parameterized constructor
        NotificationDTO full = new NotificationDTO(1L, "Test", 5L, true);
        assertEquals(1L, full.getId(), "Parameterized constructor should set the ID correctly");
        assertEquals("Test", full.getMessage(), "Parameterized constructor should set the message correctly");
        assertEquals(5L, full.getUserId(), "Parameterized constructor should set the userId correctly");
        assertTrue(full.getRead(), "Parameterized constructor should set the read status correctly");
    }

    /**
     * Tests handling of boolean field edge cases in {@link NotificationDTO}.
     * <p>
     * Verifies that DTOs with different boolean field values behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test boolean field edge cases")
    void testBooleanFieldEdgeCases() {
        NotificationDTO notifTrue = new NotificationDTO(1L, "Msg", 5L, true);
        NotificationDTO notifFalse = new NotificationDTO(1L, "Msg", 5L, false);
        NotificationDTO notifNull = new NotificationDTO(1L, "Msg", 5L, null);

        assertFalse(notifTrue.equals(notifFalse), "DTOs with different read statuses should not be equal");
        assertFalse(notifTrue.equals(notifNull), "DTO with true read status should not equal DTO with null read status");
        assertFalse(notifNull.equals(notifTrue), "DTO with null read status should not equal DTO with true read status");
    }
}
