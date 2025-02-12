package com.socialmedia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * Configuration class for setting up WebSocket messaging with STOMP.
 *
 * <p><strong>Overview:</strong></p>
 * This class configures WebSocket messaging using the STOMP protocol, enabling real-time communication
 * between clients and the server. It defines STOMP endpoints, configures message broker options, and
 * sets allowed origins for cross-origin requests.
 *
 * <p><strong>References:</strong></p>
 * <ul>
 *   <li>{@link StompEndpointRegistry} from Spring WebSocket is used to register STOMP endpoints.</li>
 *   <li>{@link MessageBrokerRegistry} is utilized to configure message broker options such as prefixes.</li>
 *   <li>{@link WebSocketMessageBrokerConfigurer} interface is implemented to customize WebSocket configurations.</li>
 * </ul>
 *
 * <p><strong>Functionality:</strong></p>
 * <ul>
 *   <li>Registers the "/ws" STOMP endpoint with SockJS fallback options.</li>
 *   <li>Configures a simple in-memory message broker with "/topic" and "/queue" prefixes.</li>
 *   <li>Sets the application destination prefix to "/app" for routing messages to message-handling methods.</li>
 *   <li>Allows cross-origin requests from any origin by setting allowed origins to "*".</li>
 * </ul>
 *
 * <p><strong>Pass/Fail Conditions:</strong></p>
 * <ul>
 *   <li><strong>Pass:</strong> Successfully establishes WebSocket connections and routes messages using the configured broker.</li>
 *   <li><strong>Fail:</strong> Fails to establish WebSocket connections if STOMP endpoints are not reachable or broker configuration is invalid.</li>
 * </ul>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers STOMP endpoints for real-time communication.
     *
     * <p><strong>Description:</strong></p>
     * This method registers the "/ws" endpoint for WebSocket connections and enables SockJS as a fallback
     * option for browsers that do not support native WebSocket. It allows cross-origin requests from any origin,
     * facilitating real-time messaging between clients and the server.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>registry</strong> (<em>{@link StompEndpointRegistry}</em>): The registry to which STOMP
     *       endpoints are added.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>If SockJS fallback fails or the client’s browser lacks WebSocket support, real-time features will not function properly.</li>
     *   <li>Incorrect endpoint registration may lead to clients being unable to establish WebSocket connections.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *   <li><strong>Endpoint Path:</strong> Should be a valid URI path (e.g., "/ws").</li>
     *   <li><strong>Allowed Origins:</strong> Can be set to specific origins or "*" to allow all origins.</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The STOMP endpoint "/ws" must be accessible to clients for establishing WebSocket connections.</li>
     *   <li>SockJS fallback should be enabled to support clients that do not support native WebSockets.</li>
     *   <li>Cross-origin requests must be appropriately configured to prevent unauthorized access.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> Clients can successfully connect to the "/ws" endpoint and establish WebSocket sessions.</li>
     *   <li><strong>Fail:</strong> Clients receive connection errors if the endpoint is unreachable or if SockJS fails to fallback.</li>
     * </ul>
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
     * <p><strong>Description:</strong></p>
     * This method sets up a simple in-memory message broker with the prefixes "/topic" and "/queue" for
     * broadcasting messages to subscribed clients. It also defines "/app" as the application destination
     * prefix for routing messages to server-side message-handling methods.
     *
     * <p><strong>Parameters:</strong></p>
     * <ul>
     *   <li><strong>registry</strong> (<em>{@link MessageBrokerRegistry}</em>): The registry to configure the message broker.</li>
     * </ul>
     *
     * <p><strong>Pass/Fail Conditions:</strong></p>
     * <ul>
     *   <li><strong>Pass:</strong> If the broker starts successfully with the specified prefixes, enabling proper message routing.</li>
     *   <li><strong>Fail:</strong> If the broker configuration is invalid, real-time messaging features will fail to operate correctly.</li>
     * </ul>
     *
     * <p><strong>Acceptable Values / Range:</strong></p>
     * <ul>
     *   <li><strong>Broker Prefixes:</strong> Should be valid URI prefixes (e.g., "/topic", "/queue").</li>
     *   <li><strong>Application Destination Prefix:</strong> Should be a valid URI prefix (e.g., "/app").</li>
     * </ul>
     *
     * <p><strong>Premise and Assertions:</strong></p>
     * <ul>
     *   <li>The message broker must be correctly configured to handle and route messages to the appropriate destinations.</li>
     *   <li>Prefixes must be consistently used across client and server configurations to ensure seamless message delivery.</li>
     * </ul>
     *
     * <p><strong>Error Conditions:</strong></p>
     * <ul>
     *   <li>Invalid broker prefixes can lead to messages not being routed correctly.</li>
     *   <li>Misconfigured application destination prefixes may cause messages to fail routing to server-side handlers.</li>
     * </ul>
     *
     * @param registry The {@link MessageBrokerRegistry} to configure message broker.
     */
    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
