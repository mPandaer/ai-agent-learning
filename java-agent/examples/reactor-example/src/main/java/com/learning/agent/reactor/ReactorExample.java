package com.learning.agent.reactor;

import reactor.core.publisher.Flux;

public class ReactorExample {

    public static void main(String[] args) {
        Flux.range(1, 5)
                .map(number -> number * 2)
                .subscribe(System.out::println);
    }
}
