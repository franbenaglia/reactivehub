package com.fab.reactivehub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Service
public class HubWebSocketHandler implements WebSocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(HubWebSocketHandler.class);

    @SuppressWarnings("null")
    @Override
    public Mono<Void> handle(WebSocketSession session) {

        Flux<WebSocketMessage> webSocketMessageFlux = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(mess -> {
                    logger.info("Length: " + Integer.toString(mess.length()));
                    return mess.toUpperCase();
                })
                .map(session::textMessage)
                .log("server sending::");

        return session.send(webSocketMessageFlux);
    }

}
