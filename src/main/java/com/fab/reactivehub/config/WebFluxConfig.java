package com.fab.reactivehub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import com.fab.reactivehub.service.HubWebSocketHandler;

import java.util.Map;

@Configuration
public class WebFluxConfig {

    @Autowired
    private HubWebSocketHandler hubWebSocketHandler;

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, HubWebSocketHandler> handlerMap = Map.of("/climateData", hubWebSocketHandler);
        return new SimpleUrlHandlerMapping(handlerMap, 1);
    }
}
