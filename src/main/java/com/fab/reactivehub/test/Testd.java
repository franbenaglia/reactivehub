package com.fab.reactivehub.test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

//https://medium.com/@ranjeetk.developer/sinks-java-reactive-programming-75eedebef261 ver 12. Assignment Solution


//https://medium.com/@varunvj.se/understanding-sink-in-reactive-programming-with-analogies-761cccd37494
public class Testd {

    public static void main(String[] args) {
        testc();
    }

    private static void testa() {

        // Create a Sink for many messages
        Sinks.Many<String> chatSink = Sinks
                .many()
                .multicast()
                .onBackpressureBuffer();

        Sinks.Many<String> sink = Sinks.many().multicast().directBestEffort();

        // Create a Flux from the Sink
        Flux<String> chatFlux = chatSink.asFlux();

        // Subscribers
        chatFlux.subscribe(message -> System.out.println("User1 received: " + message));

        chatFlux.subscribe(message -> System.out.println("User2 received: " + message));

        // Emitting messages
        chatSink.tryEmitNext("Hello, everyone!");
        chatSink.tryEmitNext("How are you all?");

    }

    private static void testb() {

        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> flux = sink.asFlux();

        sink.tryEmitNext("Hello, user!");
        sink.tryEmitNext("How are you?");

        flux.subscribe(System.out::println);

    }

    private static void testc() {
        Sinks.Many<String> sink = Sinks.many().replay().all();
        Flux<String> flux = sink.asFlux();

        sink.tryEmitNext("Message 1");
        sink.tryEmitNext("Message 2");

        flux.subscribe(message -> System.out.println("Subscriber 1: " + message));

        sink.tryEmitNext("Message 3");

        flux.subscribe(message -> System.out.println("Subscriber 2: " + message));
    }

}
