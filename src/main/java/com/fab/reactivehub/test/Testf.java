package com.fab.reactivehub.test;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

public class Testf {

    public static void main(String[] args) {
        testd();
    }

    private static void testa() {

        Flux<Integer> evenNumbers = Flux
                .range(0, 5)
                .filter(x -> x % 2 == 0); // i.e. 2, 4

        Flux<Integer> oddNumbers = Flux
                .range(0, 5)
                .filter(x -> x % 2 > 0); // ie. 1, 3, 5

        Flux<Integer> fluxOfIntegers = Flux.concat(
                evenNumbers,
                oddNumbers);

        fluxOfIntegers.subscribe(message -> System.out.println(message));

    }

    private static void testb() {

        Flux<Integer> evenNumbers = Flux
                .range(0, 5)
                .filter(x -> x % 2 == 0); // i.e. 2, 4

        Flux<Integer> oddNumbers = Flux
                .range(0, 5)
                .filter(x -> x % 2 > 0); // ie. 1, 3, 5

        Flux<Integer> fluxOfIntegers = evenNumbers.concat(
                oddNumbers);

        fluxOfIntegers.subscribe(message -> System.out.println(message));

    }

    private static void testc() {

        Flux<Integer> evenNumbers = Flux
                .range(0, 5)
                .filter(x -> x % 2 == 0); // i.e. 2, 4

        Flux<Integer> oddNumbers = Flux
                .range(0, 5)
                .filter(x -> x % 2 > 0); // ie. 1, 3, 5

        Flux<Integer> fluxOfIntegers = Flux.combineLatest(
                evenNumbers,
                oddNumbers,
                (a, b) -> a + b);

        fluxOfIntegers.subscribe(message -> System.out.println(message));

    }

    private static void testd() {

        Flux<Integer> evenNumbers = Flux
                .range(0, 10)
                .filter(x -> x % 2 == 0); // i.e. 2, 4

        Flux<Integer> oddNumbers = Flux
                .range(0, 10)
                .filter(x -> x % 2 > 0); // ie. 1, 3, 5

        Flux<Tuple2<Integer, Integer>> fluxOfIntegers = Flux.zip(
                evenNumbers,
                oddNumbers);

        Flux<Tuple2<Integer, Integer>> fluxOfIntegersb = evenNumbers
                .zipWith(oddNumbers);
            

        fluxOfIntegersb.subscribe(message -> System.out.println(message));

    }

}
