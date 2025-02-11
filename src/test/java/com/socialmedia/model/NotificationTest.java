package com.socialmedia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Notification} class.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link Notification} class,
 * including its constructors, getters, setters, {@code equals()}, {@code hashCode()},
 * {@code toString()}, and lifecycle callback method {@code onCreate()}.
 * It ensures that the {@link Notification} class behaves as expected under various scenarios.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class NotificationTest {

    private Notification notification;
    private Notification identicalNotification;
    private Notification differentNotification;

    private User user;

    /**
     * Initializes sample {@link Notification} and {@link User} instances before each test.
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

        // Initialize Notifications
        notification = new Notification();
        notification.setId(1L);
        notification.setMessage("You have a new follower!");
        notification.setUser(user);
        // createdAt will be set by onCreate()
        // read defaults to false

        identicalNotification = new Notification();
        identicalNotification.setId(1L);
        identicalNotification.setMessage("You have a new follower!");
        identicalNotification.setUser(user);
        // createdAt will be set by onCreate()
        // read defaults to false

        differentNotification = new Notification();
        differentNotification.setId(2L);
        differentNotification.setMessage("Your post has been liked!");
        differentNotification.setUser(user);
        // createdAt will be set by onCreate()
        // read defaults to false
    }

    /**
     * Tests the no-args constructor and verifies that all fields are initialized to their default values.
     */
    @Test
    @DisplayName("Test no-args constructor and default values")
    void testNoArgsConstructor() {
        Notification emptyNotification = new Notification();
        assertNull(emptyNotification.getId(), "ID should be null");
        assertNull(emptyNotification.getMessage(), "Message should be null");
        assertNull(emptyNotification.getUser(), "User should be null");
        assertNull(emptyNotification.getCreatedAt(), "createdAt should be null");
        assertFalse(emptyNotification.isRead(), "read should default to false");
    }

    /**
     * Tests the all-arguments constructor by verifying that all fields are correctly initialized.
     */
    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Notification allArgsNotification = new Notification(
                3L,
                "Your profile has been updated.",
                now,
                true,
                user
        );

        assertEquals(3L, allArgsNotification.getId(), "ID should be 3");
        assertEquals("Your profile has been updated.", allArgsNotification.getMessage(), "Message mismatch");
        assertEquals(now, allArgsNotification.getCreatedAt(), "createdAt mismatch");
        assertTrue(allArgsNotification.isRead(), "read should be true");
        assertEquals(user, allArgsNotification.getUser(), "User mismatch");
    }

    /**
     * Tests the getters and setters by setting each field and verifying the values.
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        Notification testNotification = new Notification();

        testNotification.setId(4L);
        testNotification.setMessage("You have a new message.");
        LocalDateTime creationTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        testNotification.setCreatedAt(creationTime);
        testNotification.setRead(true);
        testNotification.setUser(user);

        assertEquals(4L, testNotification.getId(), "ID should be 4");
        assertEquals("You have a new message.", testNotification.getMessage(), "Message mismatch");
        assertEquals(creationTime, testNotification.getCreatedAt(), "createdAt mismatch");
        assertTrue(testNotification.isRead(), "read should be true");
        assertEquals(user, testNotification.getUser(), "User mismatch");
    }

    /**
     * Tests the {@code equals()} method for reflexivity, symmetry, transitivity, and null comparison.
     */
    @Test
    @DisplayName("Test equals() method")
    void testEquals() {
        // Reflexive
        assertEquals(notification, notification, "Notification should be equal to itself");

        // Symmetric
        assertEquals(notification, identicalNotification, "Notifications with identical fields should be equal");
        assertEquals(identicalNotification, notification, "Symmetric equality failed");

        // Transitive
        Notification thirdNotification = new Notification();
        thirdNotification.setId(1L);
        thirdNotification.setMessage("You have a new follower!");
        thirdNotification.setUser(user);
        // createdAt and read are not set, assuming they are handled by lifecycle callbacks
        assertEquals(notification, identicalNotification, "notification equals identicalNotification");
        assertEquals(identicalNotification, thirdNotification, "identicalNotification equals thirdNotification");
        assertEquals(notification, thirdNotification, "notification equals thirdNotification (transitive)");

        // Consistent
        assertEquals(notification, identicalNotification, "Consistency check");
        assertEquals(notification, identicalNotification, "Consistency check again");

        // Null comparison
        assertNotEquals(notification, null, "Notification should not be equal to null");

        // Different object types
        assertNotEquals(notification, "Some String", "Notification should not be equal to an unrelated object");

        // Unequal notifications
        assertNotEquals(notification, differentNotification, "Notifications with different fields should not be equal");
    }

    /**
     * Tests the {@code hashCode()} method to ensure consistency with {@code equals()}.
     */
    @Test
    @DisplayName("Test hashCode() method")
    void testHashCode() {
        // Equal objects must have the same hash code
        assertEquals(notification.hashCode(), identicalNotification.hashCode(), "Hash codes should match for equal notifications");

        // Different objects may have different hash codes
        assertNotEquals(notification.hashCode(), differentNotification.hashCode(), "Hash codes should differ for different notifications");
    }

    /**
     * Tests the {@code toString()} method to ensure it includes all relevant fields.
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        String result = notification.toString();

        // Partial assertions:
        assertTrue(result.contains("id=1"), "Should contain 'id=1'");
        assertTrue(result.contains("message=You have a new follower!"), "Should contain the message");
        assertTrue(result.contains("read=false"), "Should indicate read is false");
        // 'user' is excluded, so we do NOT check it
    }

    /**
     * Tests the {@code canEqual()} method to ensure proper equality checks.
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        // canEqual should return true for another Notification
        assertTrue(notification.canEqual(identicalNotification), "canEqual should return true for another Notification");

        // canEqual should return false for different types
        assertFalse(notification.canEqual("Not a Notification"), "canEqual should return false for different types");
    }

    /**
     * Tests the PrePersist lifecycle callback {@code onCreate()} method.
     * <p>
     * Verifies that {@code onCreate()} correctly sets the {@code createdAt} field.
     * </p>
     */
    @Test
    @DisplayName("Test onCreate() lifecycle callback")
    void testOnCreate() throws Exception {
        Notification prePersistNotification = new Notification();
        prePersistNotification.setMessage("Your account has been activated.");
        prePersistNotification.setUser(user);
        // read defaults to false

        // Use reflection to invoke the protected onCreate() method
        Method onCreateMethod = Notification.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(prePersistNotification);

        assertNotNull(prePersistNotification.getCreatedAt(), "createdAt should be set by onCreate()");

        // Assuming that onCreate() sets createdAt to now, we can check if it's recent
        LocalDateTime now = LocalDateTime.now();
        assertTrue(prePersistNotification.getCreatedAt().isBefore(now.plusSeconds(1)), "createdAt should be set to current time");
        assertTrue(prePersistNotification.getCreatedAt().isAfter(now.minusSeconds(5)), "createdAt should be set to current time");
    }

    /**
     * Tests adding and removing User associations.
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

        // Update user association
        notification.setUser(newUser);
        assertEquals(newUser, notification.getUser(), "User should be updated to newUser");

        // Remove user association
        notification.setUser(null);
        assertNull(notification.getUser(), "User should be null after removal");
    }
}
