package nl.quintor.pokedex.configuration;

import lombok.RequiredArgsConstructor;
import nl.quintor.pokedex.controllers.SubscriptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.AbstractHandshakeHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebsocketConfiguration implements WebSocketConfigurer {
    private final SubscriptionHandler subscriptionHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(subscriptionHandler, "/subscriptions")
                .setAllowedOrigins("*")
                .setHandshakeHandler(new AbstractHandshakeHandler() {});
    }
}
