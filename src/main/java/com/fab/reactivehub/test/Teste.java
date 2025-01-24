package com.fab.reactivehub.test;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class Teste {

    public static void main(String[] args) {

        Sinks.One<String> sink = Sinks.one();
        Mono<String> mono = sink.asMono();

        sink.emitValue("Process started!", Sinks.EmitFailureHandler.FAIL_FAST);

        sink.emitValue("Another attempt!", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(System.out::println);

    }

}
