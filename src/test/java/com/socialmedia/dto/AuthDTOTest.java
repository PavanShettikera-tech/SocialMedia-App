package com.socialmedia.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link AuthDTO}.
 * <p>
 * This class contains unit tests to verify the correctness of the {@link AuthDTO} class,
 * including its getters, setters, constructors, {@code equals()}, {@code hashCode()},
 * and {@code toString()} methods. It ensures that the DTO behaves as expected under
 * various scenarios.
 * </p>
 * 
 * @version 1.0
 * @since 2025-01-28
 */
class AuthDTOTest {

    private AuthDTO auth1;
    private AuthDTO auth2;
    private AuthDTO auth3;

    /**
     * Initializes sample {@link AuthDTO} instances before each test.
     */
    @BeforeEach
    void setUp() {
        auth1 = new AuthDTO("user@test.com", "password");
        auth2 = new AuthDTO("user@test.com", "password");
        auth3 = new AuthDTO("different@test.com", "wrongpass");
    }

    /**
     * Tests the {@code equals()} and {@code hashCode()} methods of {@link AuthDTO}.
     * <p>
     * Verifies that two identical DTOs are equal and have the same hash code,
     * while different DTOs are not equal and have different hash codes.
     * </p>
     */
    @Test
    @DisplayName("Test equals() and hashCode() methods")
    void testEqualsAndHashCode() {
        // Equality tests
        assertTrue(auth1.equals(auth2), "auth1 should be equal to auth2");
        assertFalse(auth1.equals(auth3), "auth1 should not be equal to auth3");
        assertFalse(auth1.equals(null), "auth1 should not be equal to null");
        assertFalse(auth1.equals(new Object()), "auth1 should not be equal to an unrelated object");

        // HashCode consistency
        assertEquals(auth1.hashCode(), auth2.hashCode(), "Hash codes of auth1 and auth2 should be equal");
        assertNotEquals(auth1.hashCode(), auth3.hashCode(), "Hash codes of auth1 and auth3 should not be equal");
    }

    /**
     * Tests field comparisons for {@link AuthDTO}.
     * <p>
     * Verifies that changing individual fields affects equality as expected.
     * </p>
     */
    @Test
    @DisplayName("Test field comparisons affecting equality")
    void testFieldComparisonBranches() {
        AuthDTO base = new AuthDTO("base@test.com", "basepass");

        // Test different email
        AuthDTO differentEmail = new AuthDTO("different@test.com", "basepass");
        assertFalse(base.equals(differentEmail), "DTOs with different emails should not be equal");

        // Test different password
        AuthDTO differentPassword = new AuthDTO("base@test.com", "different");
        assertFalse(base.equals(differentPassword), "DTOs with different passwords should not be equal");

        // Test both fields different
        AuthDTO bothDifferent = new AuthDTO("diff@test.com", "diffpass");
        assertFalse(base.equals(bothDifferent), "DTOs with different emails and passwords should not be equal");
    }

    /**
     * Tests handling of null fields in {@link AuthDTO}.
     * <p>
     * Verifies that DTOs with null fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of null fields in equals()")
    void testNullHandling() {
        AuthDTO nullAuth = new AuthDTO(null, null);
        AuthDTO partialNull1 = new AuthDTO(null, "pass");
        AuthDTO partialNull2 = new AuthDTO("email", null);
        AuthDTO populated = new AuthDTO("test@test.com", "testpass");

        // Full null comparison
        AuthDTO anotherNullAuth = new AuthDTO(null, null);
        assertTrue(nullAuth.equals(anotherNullAuth), "DTOs with all fields null should be equal");

        // Mixed null comparisons
        assertFalse(nullAuth.equals(partialNull1), "DTO with all fields null should not equal DTO with partial nulls");
        assertFalse(partialNull1.equals(partialNull2), "DTOs with different partial nulls should not be equal");
        assertFalse(populated.equals(nullAuth), "Populated DTO should not equal DTO with all fields null");
    }

    /**
     * Tests the {@code toString()} method of {@link AuthDTO}.
     * <p>
     * Verifies that the string representation contains all relevant fields.
     * </p>
     */
    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        AuthDTO auth = new AuthDTO("test@example.com", "secret");
        String str = auth.toString();

        assertTrue(str.contains("email=test@example.com"), "toString() should contain the email");
        assertTrue(str.contains("password=secret"), "toString() should contain the password");
    }

    /**
     * Tests the {@code canEqual()} method of {@link AuthDTO}.
     * <p>
     * Verifies that {@code canEqual()} behaves correctly when comparing with the same class and different classes.
     * </p>
     */
    @Test
    @DisplayName("Test canEqual() method")
    void testCanEqual() {
        AuthDTO auth = new AuthDTO();
        assertTrue(auth.canEqual(new AuthDTO()), "canEqual() should return true for the same class");
        assertFalse(auth.canEqual(new Object()), "canEqual() should return false for different classes");
    }

    /**
     * Tests the getters and setters of {@link AuthDTO}.
     * <p>
     * Verifies that each field can be set and retrieved correctly.
     * </p>
     */
    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        AuthDTO auth = new AuthDTO();
        auth.setEmail("new@email.com");
        auth.setPassword("newpassword");

        assertEquals("new@email.com", auth.getEmail(), "Getter should return the set email");
        assertEquals("newpassword", auth.getPassword(), "Getter should return the set password");
    }

    /**
     * Tests the constructors of {@link AuthDTO}.
     * <p>
     * Verifies that the default and parameterized constructors initialize fields correctly.
     * </p>
     */
    @Test
    @DisplayName("Test constructors")
    void testConstructors() {
        // Test default constructor
        AuthDTO empty = new AuthDTO();
        assertNull(empty.getEmail(), "Default constructor should initialize email to null");
        assertNull(empty.getPassword(), "Default constructor should initialize password to null");

        // Test parameterized constructor
        AuthDTO full = new AuthDTO("constructor@test.com", "constructpass");
        assertEquals("constructor@test.com", full.getEmail(), "Parameterized constructor should set the email correctly");
        assertEquals("constructpass", full.getPassword(), "Parameterized constructor should set the password correctly");
    }

    /**
     * Tests handling of empty strings in {@link AuthDTO}.
     * <p>
     * Verifies that DTOs with empty string fields behave correctly in equality checks.
     * </p>
     */
    @Test
    @DisplayName("Test handling of empty strings in fields")
    void testEmptyStringHandling() {
        AuthDTO emptyEmail = new AuthDTO("", "pass");
        AuthDTO emptyPass = new AuthDTO("email", "");

        assertFalse(emptyEmail.equals(emptyPass), "DTOs with different empty fields should not be equal");
        assertFalse(emptyEmail.equals(new AuthDTO(null, "pass")), "DTO with empty email should not equal DTO with null email");
    }
}
