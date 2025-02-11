package com.socialmedia.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the {@link EventPublisher} class.
 * <p>
 * This class verifies the functionality of the {@link EventPublisher}, ensuring that events are published correctly.
 * It utilizes Mockito to mock dependencies and JUnit 5 for testing.
 * </p>
 *
 * @version 1.0
 * @since 2025-01-28
 */
@ExtendWith(MockitoExtension.class)
class EventPublisherTest {

    @Mock
    private ApplicationEventPublisher mockAppEventPublisher;

    @InjectMocks
    private EventPublisher eventPublisher;

    /**
     * Initializes mock objects and injects them into the EventPublisher before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Inject the mock ApplicationEventPublisher into the EventPublisher using ReflectionTestUtils
        ReflectionTestUtils.setField(eventPublisher, 
                                     "applicationEventPublisher", 
                                     mockAppEventPublisher);
    }

    /**
     * Verifies that the EventPublisher's constructor creates a non-null instance.
     * This test ensures that the no-argument constructor functions correctly.
     */
    @Test
    @DisplayName("Test EventPublisher constructor creates a non-null instance")
    void testConstructor() {
        // Assert that the eventPublisher instance is not null
        assertNotNull(eventPublisher, "EventPublisher instance should not be null");
    }

    /**
     * Tests the publishNotificationEvent(NotificationEvent) method.
     * Verifies that the ApplicationEventPublisher's publishEvent method is called with the correct event.
     */
    @Test
    @DisplayName("Test publishNotificationEvent publishes the correct event")
    void testPublishNotificationEvent() {
        // Arrange: Create a sample NotificationEvent
        NotificationEvent event = new NotificationEvent();

        // Act: Publish the NotificationEvent using EventPublisher
        eventPublisher.publishNotificationEvent(event);

        // Assert: Verify that publishEvent was called once with the correct event
        verify(mockAppEventPublisher, times(1)).publishEvent(event);
    }
}
