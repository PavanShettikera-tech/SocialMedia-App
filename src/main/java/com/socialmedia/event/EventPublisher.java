package com.socialmedia.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


/**
 * Component responsible for publishing application events (notably NotificationEvent).
 *
 * <p><strong>Feedback-Related Updates:</strong>
 * <ul>
 *   <li>Combined logic in single method (publishNotificationEvent).</li>
 *   <li>Doc about pass/fail: mainly if event publisher is null or fails unexpectedly.</li>
 * </ul>
 */
@Component
public class EventPublisher {


    private final ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    /**
     * Publishes a {@link NotificationEvent}.
     *
     * <p>
     * Pass: If the event is successfully published.  
     * Fail: Rarely fails unless the app context is not available.
     * </p>
     *
     * @param event The {@link NotificationEvent} to be published.
     */
    public void publishNotificationEvent(NotificationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
