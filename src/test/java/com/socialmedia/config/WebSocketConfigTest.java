package com.socialmedia.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link WebSocketConfig}.
 */
@ExtendWith(MockitoExtension.class)
class WebSocketConfigTest {

    @Mock
    private MessageBrokerRegistry messageBrokerRegistry;

    @Mock
    private StompEndpointRegistry stompEndpointRegistry;

    @Mock
    private StompWebSocketEndpointRegistration endpointRegistration;

    /**
     * We need a separate mock for the return type of withSockJS(), 
     * which is SockJsServiceRegistration.
     */
    @Mock
    private SockJsServiceRegistration sockJsServiceRegistration;

    @InjectMocks
    private WebSocketConfig webSocketConfig;

    /**
     * Tests the configuration of the message broker.
     */
    @Test
    @DisplayName("Test configureMessageBroker() sets broker and app prefixes")
    void testConfigureMessageBroker() {
        webSocketConfig.configureMessageBroker(messageBrokerRegistry);

        verify(messageBrokerRegistry, times(1)).enableSimpleBroker("/topic", "/queue");
        verify(messageBrokerRegistry, times(1)).setApplicationDestinationPrefixes("/app");
    }

    /**
     * Tests the registration of STOMP endpoints.
     */
    @Test
    @DisplayName("Test registerStompEndpoints() registers /ws with SockJS")
    void testRegisterStompEndpoints() {
        // Add endpoint returns a StompWebSocketEndpointRegistration
        when(stompEndpointRegistry.addEndpoint("/ws")).thenReturn(endpointRegistration);
        // setAllowedOrigins returns the same StompWebSocketEndpointRegistration for chaining
        when(endpointRegistration.setAllowedOrigins("*")).thenReturn(endpointRegistration);
        // withSockJS() returns a SockJsServiceRegistration, so match that type
        when(endpointRegistration.withSockJS()).thenReturn(sockJsServiceRegistration);

        // Execute the method under test
        webSocketConfig.registerStompEndpoints(stompEndpointRegistry);

        // Verify
        verify(stompEndpointRegistry, times(1)).addEndpoint("/ws");
        verify(endpointRegistration, times(1)).setAllowedOrigins("*");
        verify(endpointRegistration, times(1)).withSockJS(); 
        // We do NOT chain the SockJsServiceRegistration in this test, 
        // so we only verify it's called.
    }

    /**
     * Tests the WebSocketConfig bean instantiation.
     */
    @Test
    @DisplayName("Test WebSocketConfig is loaded successfully")
    void testWebSocketConfigurationLoaded() {
        assertNotNull(webSocketConfig, "WebSocketConfig should be loaded");
    }
}
