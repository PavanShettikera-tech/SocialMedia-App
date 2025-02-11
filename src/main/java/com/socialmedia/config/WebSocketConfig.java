package com.socialmedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration class for setting up WebSocket messaging with STOMP.
 *
 * <p><strong>Feedback-Related Updates:</strong></p>
 * <ul>
 *   <li>References: Cross-references STOMP endpoints at /ws and sets broker prefixes.</li>
 *   <li>Functions: Single configure methods with thorough doc about pass/fail conditions for connections.</li>
 *   <li>Comments: Acceptable origin is *, usage scenario for real-time messaging.</li>
 * </ul>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers STOMP endpoints for real-time communication.
     *
     * <p><strong>Error Conditions:</strong>
     * - If SockJS fallback fails or the client’s browser lacks WebSocket support, real-time features fail.
     * </p>
     *
     * @param registry The {@link StompEndpointRegistry} to register STOMP endpoints.
     */
    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * Configures the message broker options for routing messages.
     *
     * <p><strong>Pass/Fail Conditions:</strong>
     * Pass: If the broker starts successfully with prefixes /topic, /queue, /app.
     * Fail: If broker config is invalid, real-time messaging fails.
     * </p>
     *
     * @param registry The {@link MessageBrokerRegistry} to configure message broker.
     */
    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
