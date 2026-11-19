package com.geosieben.gsbworkday.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enables a simple in-memory broker with a /topic prefix for public 
        // and /queue or /user prefix for private messaging
        registry.enableSimpleBroker("/topic", "/queue");
        
        // Prefix for messages bound for @MessageMapping methods in controllers
        registry.setApplicationDestinationPrefixes("/app");
        
        // Prefix used to designate user-specific destinations
        registry.setUserDestinationPrefix("/user");
    }
@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The endpoint clients will connect to
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Fallback option if WebSockets aren't supported
    }
}
