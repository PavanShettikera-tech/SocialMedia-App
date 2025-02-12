package com.socialmedia.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Component responsible for publishing application events, specifically {@link NotificationEvent}.
 *
 * <p><strong>Functionality:</strong>
 * This component centralizes the event publishing mechanism within the application, ensuring that
 * {@link NotificationEvent} instances are consistently dispatched to all registered listeners.
 * </p>
 *
 * <p><strong>Error Conditions:</strong>
 * <ul>
 *   <li>If {@link ApplicationEventPublisher} is not properly initialized, event publishing may fail.</li>
 *   <li>Unexpected runtime exceptions during event publishing are possible but rare.</li>
 * </ul>
 * </p>
 *
 * <p><strong>Pass/Fail Conditions:</strong>
 * <ul>
 *   <li><strong>Pass:</strong> Event is successfully published to the application context.</li>
 *   <li><strong>Fail:</strong> Publishing fails due to a null publisher or unexpected exceptions.</li>
 * </ul>
 * </p>
 */
@Component
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructs an {@code EventPublisher} with the provided {@link ApplicationEventPublisher}.
     *
     * <p><strong>Description:</strong>
     * Initializes the {@code EventPublisher} with a specific instance of {@link ApplicationEventPublisher}
     * which is used to dispatch events within the application context.
     * </p>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>applicationEventPublisher</strong> - The publisher used to dispatch events. Must not be null.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values/Range:</strong>
     * <ul>
     *   <li>The {@code applicationEventPublisher} parameter must be a non-null instance of {@link ApplicationEventPublisher}.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Error Conditions:</strong>
     * <ul>
     *   <li>If {@code applicationEventPublisher} is null, an {@link IllegalArgumentException} is thrown.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Premises and Assertions:</strong>
     * <ul>
     *   <li>Assumes that a valid {@link ApplicationEventPublisher} instance is provided.</li>
     *   <li>Asserts that the {@code applicationEventPublisher} parameter is not null before assignment.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> The {@code EventPublisher} is successfully initialized with a valid publisher.</li>
     *   <li><strong>Fail:</strong> Initialization fails due to a null publisher.</li>
     * </ul>
     * </p>
     *
     * @param applicationEventPublisher the publisher used to dispatch events. Must not be null.
     * @throws IllegalArgumentException if {@code applicationEventPublisher} is null.
     */
    @Autowired
    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        if (applicationEventPublisher == null) {
            throw new IllegalArgumentException("ApplicationEventPublisher must not be null");
        }
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Publishes a {@link NotificationEvent} to the application context.
     *
     * <p><strong>Description:</strong>
     * This method dispatches the provided {@link NotificationEvent} to all registered listeners within
     * the application context, enabling decoupled communication between different parts of the application.
     * </p>
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>event</strong> - The {@link NotificationEvent} instance to be published. Must not be null.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values/Range:</strong>
     * <ul>
     *   <li>The {@code event} parameter must be a non-null instance of {@link NotificationEvent}.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Error Conditions:</strong>
     * <ul>
     *   <li>If {@code event} is null, the method throws an {@link IllegalArgumentException}.</li>
     *   <li>If the event publisher fails to publish the event due to internal issues, a runtime exception may occur.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Premises and Assertions:</strong>
     * <ul>
     *   <li>Assumes that {@link ApplicationEventPublisher} has been correctly initialized and injected.</li>
     *   <li>Asserts that the {@code event} parameter is not null before attempting to publish.</li>
     * </ul>
     * </p>
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * <ul>
     *   <li><strong>Pass:</strong> The event is successfully published to the application context.</li>
     *   <li><strong>Fail:</strong> The event is not published due to a null event or unexpected failure.</li>
     * </ul>
     * </p>
     *
     * @param event The {@link NotificationEvent} to be published. Must not be null.
     * @throws IllegalArgumentException if {@code event} is null.
     * @throws RuntimeException if publishing the event fails unexpectedly.
     */
    public void publishNotificationEvent(NotificationEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("NotificationEvent must not be null");
        }
        applicationEventPublisher.publishEvent(event);
    }
}
