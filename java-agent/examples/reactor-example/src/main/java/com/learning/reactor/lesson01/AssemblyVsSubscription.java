package com.learning.reactor.lesson01;

import reactor.core.publisher.Flux;

/** Companion example for docs/lessons/0001-reactor-is-a-signal-protocol.html. */
public class AssemblyVsSubscription {
    public static void main(String[] args) {
        Flux<Integer> flow = Flux.range(1, 3)
                .doOnNext(n -> System.out.println("处理 " + n));

        System.out.println("组装完成");
        flow.subscribe();
        flow.subscribe();
    }
}
