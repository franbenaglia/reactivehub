package com.fab.reactivehub.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.fab.reactivehub.service.UnicastProcessorHandler;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.UnicastProcessor;

//https://johanzietsman.com/how-to-build-a-chat-app-using-webflux-websockets-react/
//https://github.com/monkey-codes/java-reactive-chat/tree/master/src/main/java/codes/monkey/reactivechat
@Configuration
@SuppressWarnings("deprecation")
public class UnicastProcessorConfig {

    @Bean
    public Many<String> eventPublisherb() {
        // Sinks.Many<String> chatSink =
        // Sinks.many().multicast().onBackpressureBuffer();
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<String> eventsb(Many<String> eventPublisherb) {
        // Sinks.Many<String> sink = Sinks.many().replay().all();
        return eventPublisherb.asFlux();
    }

    @Bean
    public UnicastProcessor<String> eventPublisher() {

        return UnicastProcessor.create();
    }

    @Bean
    public Flux<String> events(UnicastProcessor<String> eventPublisher) {

        return eventPublisher
                .replay()
                .autoConnect();
    }

    @Bean
    public HandlerMapping webSocketMapping(UnicastProcessor<String> eventPublisher, Flux<String> events) {
        Map<String, Object> map = new HashMap<>();
        map.put("/climateData", new UnicastProcessorHandler(eventPublisher, events));
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setUrlMap(map);
        simpleUrlHandlerMapping.setOrder(10);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

}
