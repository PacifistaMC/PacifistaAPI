package fr.pacifista.api.support.tickets.service.configs;

import fr.pacifista.api.support.tickets.service.services.PacifistaSupportWebSocketTicketMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class PacifistaSupportWebsocketsConfig implements WebSocketConfigurer {

    private final PacifistaSupportWebSocketTicketMessageService webSocketTicketMessageService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketTicketMessageService, "/support/ticket/message/web/ws")
                .setAllowedOrigins("*");
    }
}
