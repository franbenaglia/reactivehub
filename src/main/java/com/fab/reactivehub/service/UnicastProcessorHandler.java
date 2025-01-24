package com.fab.reactivehub.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

//https://johanzietsman.com/how-to-build-a-chat-app-using-webflux-websockets-react/
//https://github.com/monkey-codes/java-reactive-chat/blob/master/src/main/java/codes/monkey/reactivechat/ChatSocketHandler.java

@SuppressWarnings("deprecation")
public class UnicastProcessorHandler implements WebSocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(UnicastProcessorHandler.class);

    private UnicastProcessor<String> eventPublisher;
    private Flux<String> outputEvents;

    public UnicastProcessorHandler(UnicastProcessor<String> eventPublisher, Flux<String> events) {
        this.eventPublisher = eventPublisher;
        this.outputEvents = Flux.from(events);
    }

    @SuppressWarnings("null")
    public Mono<Void> handle(WebSocketSession session) {
        WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(eventPublisher);
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .log()
                .subscribe(subscriber::onNext, subscriber::onError, subscriber::onComplete);
        return session.send(outputEvents.map(session::textMessage));

    }

    private static class WebSocketMessageSubscriber {

        private UnicastProcessor<String> eventPublisher;
        private Optional<String> lastReceivedEvent = Optional.empty();

        public WebSocketMessageSubscriber(UnicastProcessor<String> eventPublisher) {
            this.eventPublisher = eventPublisher;
        }

        public void onNext(String event) {
            lastReceivedEvent = Optional.of(event);
            logger.info("onnext subscriber" + event);
            eventPublisher.onNext(event); //
        }

        public void onError(Throwable error) {
            logger.info("onerror subscriber" + error);
            error.printStackTrace();
        }

        public void onComplete() {
            logger.info("oncomplete subscriber");
            lastReceivedEvent.ifPresent(event -> eventPublisher.onNext("LAST RECEIVED EVENT"));
        }

    }

}
