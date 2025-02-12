package com.socialmedia.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link UserDTO}.
 *
 * <p><strong>Premise:</strong>
 * Ensures that getters, setters, constructors, {@code equals()}, {@code hashCode()},
 * {@code toString()}, and {@code canEqual()} methods function correctly for the {@link UserDTO} class.
 * </p>
 *
 * <p><strong>Error Conditions / Acceptable Values:</strong></p>
 * <ul>
 *   <li><code>id</code>: Should be a positive {@code Long} value or {@code null}.</li>
 *   <li><code>username</code>: Should be a non-null {@code String} with a valid format.</li>
 *   <li><code>password</code>: Should be a non-null {@code String} with a secure format.</li>
 *   <li><code>email</code>: Should be a non-null {@code String} following a valid email pattern.</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li><strong>Pass:</strong> All methods behave as expected, correctly handling valid and edge-case inputs.</li>
 *   <li><strong>Fail:</strong> Any method does not handle inputs correctly, leading to incorrect behavior or exceptions.</li>
 * </ul>
 *
 * @version 1.0
 * @since 2025-01-28
 */
class UserDTOTest {

    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;

    /**
     * Initializes sample {@link UserDTO} instances before each test.
     *
     * <p><strong>Premise:</strong> Sets up identical and distinct {@link UserDTO} objects to test equality and hashing.</p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> All objects are initialized correctly with the specified parameters.</li>
     *   <li><strong>Fail:</strong> Objects are not initialized as expected, leading to test failures.</li>
     * </ul>
     * </p>
     */
    @BeforeEach
    void setUp() {
        user1 = new UserDTO(1L, "user1", "pass1", "user1@test.com");
        user2 = new UserDTO(1L, "user1", "pass1", "user1@test.com");
        user3 = new UserDTO(2L, "user2", "pass2", "user2@test.com");
    }

    /**
     * Tests the {@code equals()} and {@code hashCode()} methods of {@link UserDTO}.
     *
     * <p><strong>Premise:</strong>
     * Verifies that two identical DTOs are equal and have the same hash code,
     * while different DTOs are not equal and have different hash codes.
     * </p>
     *
     * <p><strong>Error Conditions / Acceptable Values:</strong>
     * Ensures that equality checks handle {@code null} and different object types correctly.
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> Equal objects are recognized as equal with matching hash codes,
     *       and unequal objects are correctly identified as not equal.</li>
     *   <li><strong>Fail:</strong> If equal objects are not recognized as equal, hash codes differ,
     *       or unequal objects are mistakenly identified as equal.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test equals() and hashCode() methods")
    void testEqualsAndHashCode() {
        // Test equality
        assertTrue(user1.equals(user2), "user1 should be equal to user2");
        assertFalse(user1.equals(user3), "user1 should not be equal to user3");

        // Test null and different class
        assertFalse(user1.equals(null), "user1 should not be equal to null");
        assertFalse(user1.equals(new Object()), "user1 should not be equal to an unrelated object");

        // Test hashCode consistency
        assertEquals(user1.hashCode(), user2.hashCode(), "Hash codes of user1 and user2 should be equal");
        assertNotEquals(user1.hashCode(), user3.hashCode(), "Hash codes of user1 and user3 should not be equal");
    }

    /**
     * Tests field comparisons for {@link UserDTO}.
     *
     * <p><strong>Premise:</strong>
     * Verifies that changing individual fields affects equality as expected.
     * </p>
     *
     * <p><strong>Error Conditions / Acceptable Values:</strong>
     * Ensures that each field is compared correctly and that differences lead to inequality.
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> DTOs with different fields are not equal.</li>
     *   <li><strong>Fail:</strong> DTOs with different fields are incorrectly considered equal.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test field comparisons affecting equality")
    void testFieldComparisonBranches() {
        UserDTO base = new UserDTO(1L, "user", "pass", "user@test.com");

        // Test different ID
        UserDTO differentId = new UserDTO(2L, "user", "pass", "user@test.com");
        assertFalse(base.equals(differentId), "DTOs with different IDs should not be equal");

        // Test different username
        UserDTO differentUsername = new UserDTO(1L, "different", "pass", "user@test.com");
        assertFalse(base.equals(differentUsername), "DTOs with different usernames should not be equal");

        // Test different password
        UserDTO differentPassword = new UserDTO(1L, "user", "different", "user@test.com");
        assertFalse(base.equals(differentPassword), "DTOs with different passwords should not be equal");

        // Test different email
        UserDTO differentEmail = new UserDTO(1L, "user", "pass", "different@test.com");
        assertFalse(base.equals(differentEmail), "DTOs with different emails should not be equal");
    }

    /**
     * Tests handling of null fields in {@link UserDTO}.
     *
     * <p><strong>Premise:</strong>
     * Verifies that DTOs with null fields behave correctly in equality checks.
     * </p>
     *
     * <p><strong>Error Conditions / Acceptable Values:</strong>
     * Checks how {@code equals()} handles {@code null} values in various fields.
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> DTOs with all fields null are equal to each other and not equal to populated DTOs.</li>
     *   <li><strong>Fail:</strong> Equality checks do not correctly handle {@code null} fields.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test handling of null fields in equals()")
    void testNullFieldHandling() {
        UserDTO nullUser = new UserDTO(null, null, null, null);
        UserDTO populatedUser = new UserDTO(1L, "user", "pass", "user@test.com");

        // Null vs non-null comparisons
        assertFalse(nullUser.equals(populatedUser), "DTO with null fields should not be equal to populated DTO");
        assertFalse(populatedUser.equals(nullUser), "Populated DTO should not be equal to DTO with null fields");

        // Both null fields comparison
        UserDTO anotherNullUser = new UserDTO(null, null, null, null);
        assertTrue(nullUser.equals(anotherNullUser), "DTOs with all fields null should be equal");
    }

    /**
     * Tests the {@code toString()} method of {@link UserDTO}.
     *
     * <p><strong>Premise:</strong>
     * Verifies that the string representation contains all relevant fields.
     * </p>
     *
     * <p><strong>Error Conditions / Acceptable Values:</strong>
     * Ensures that the {@code toString()} output includes non-null field values.
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> The {@code toString()} output includes all set field values.</li>
     *   <li><strong>Fail:</strong> Any field value is missing or incorrectly represented in the output.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        UserDTO user = new UserDTO(1L, "testuser", "testpass", "test@email.com");
        String str = user.toString();

        assertTrue(str.contains("id=1"), "toString() should contain the ID");
        assertTrue(str.contains("username=testuser"), "toString() should contain the username");
        assertTrue(str.contains("password=testpass"), "toString() should contain the password");
        assertTrue(str.contains("email=test@email.com"), "toString() should contain the email");
    }

    /**
     * Tests the {@code canEqual()} method of {@link UserDTO}.
     *
     * <p><strong>Premise:</strong>
     * Verifies that {@code canEqual()} behaves correctly when comparing with the same class and different classes.
     * </p>
     *
     * <p><strong>Error Conditions / Acceptable Values:</strong>
     * Ensures that {@code canEqual()} returns {@code true} only for instances of {@link UserDTO} and {@code false} otherwise.
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> {@code canEqual()} returns {@code true} for {@link UserDTO} instances and {@code false} for other types.</li>
     *   <li><strong>Fail:</strong> {@code canEqual()} does not correctly identify compatible or incompatible types.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        UserDTO user = new UserDTO();
        assertTrue(user.canEqual(new UserDTO()), "canEqual() should return true for the same class");
        assertFalse(user.canEqual(new Object()), "canEqual() should return false for different classes");
    }

    /**
     * Tests the getters and setters of {@link UserDTO}.
     *
     * <p><strong>Premise:</strong>
     * Verifies that each field can be set and retrieved correctly.
     * </p>
     *
     * <p><strong>Parameters:</strong>
     * <ul>
     *   <li><code>id</code> - {@code Long} representing the user ID. Acceptable range: positive values or {@code null}.</li>
     *   <li><code>username</code> - {@code String} representing the username. Should be non-null.</li>
     *   <li><code>password</code> - {@code String} representing the password. Should be non-null.</li>
     *   <li><code>email</code> - {@code String} representing the email. Should follow a valid email pattern.</li>
     * </ul>
     *
     * <p><strong>Assertions:</strong>
     * Validates that each setter correctly assigns the value and each getter retrieves the expected value.
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> All getters return values matching those set by the setters.</li>
     *   <li><strong>Fail:</strong> Any getter does not return the expected value.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        UserDTO user = new UserDTO();
        user.setId(5L);
        user.setUsername("newuser");
        user.setPassword("newpass");
        user.setEmail("new@email.com");

        assertEquals(5L, user.getId(), "Getter should return the set ID");
        assertEquals("newuser", user.getUsername(), "Getter should return the set username");
        assertEquals("newpass", user.getPassword(), "Getter should return the set password");
        assertEquals("new@email.com", user.getEmail(), "Getter should return the set email");
    }

    /**
     * Tests the constructors of {@link UserDTO}.
     *
     * <p><strong>Premise:</strong>
     * Verifies that the default and parameterized constructors initialize fields correctly.
     * </p>
     *
     * <p><strong>Parameters:</strong>
     * <ul>
     *   <li><strong>Default Constructor:</strong> Initializes all fields to {@code null}.</li>
     *   <li><strong>Parameterized Constructor:</strong> Initializes fields with provided values.</li>
     * </ul>
     *
     * <p><strong>Assertions:</strong>
     * Ensures that fields are set to expected values after construction.
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> Constructors initialize fields as expected.</li>
     *   <li><strong>Fail:</strong> Fields are not initialized correctly by constructors.</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Test constructors")
    void testConstructors() {
        // Test default constructor
        UserDTO empty = new UserDTO();
        assertNull(empty.getId(), "Default constructor should initialize ID to null");
        assertNull(empty.getUsername(), "Default constructor should initialize username to null");
        assertNull(empty.getPassword(), "Default constructor should initialize password to null");
        assertNull(empty.getEmail(), "Default constructor should initialize email to null");

        // Test parameterized constructor
        UserDTO full = new UserDTO(1L, "user", "pass", "user@test.com");
        assertEquals(1L, full.getId(), "Parameterized constructor should set the ID correctly");
        assertEquals("user", full.getUsername(), "Parameterized constructor should set the username correctly");
        assertEquals("pass", full.getPassword(), "Parameterized constructor should set the password correctly");
        assertEquals("user@test.com", full.getEmail(), "Parameterized constructor should set the email correctly");
    }
}
