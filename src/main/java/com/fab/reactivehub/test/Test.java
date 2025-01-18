package com.fab.reactivehub.test;

import java.net.URI;
import java.time.Duration;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Flux;

public class Test {

    public static void main(String[] args) {

        WebSocketClient client = new ReactorNettyWebSocketClient();
        URI uri = URI.create("ws://localhost:8088/climateData");

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1));

        client.execute(uri, webSocketSession -> webSocketSession
                .send(longFlux.map(i -> webSocketSession.textMessage("Laxmi " + i)))
                .and(webSocketSession.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .doOnNext(System.out::println)));

                        for (;;)
                        ;

    }

}
