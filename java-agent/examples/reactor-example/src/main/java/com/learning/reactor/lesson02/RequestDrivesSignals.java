package com.learning.reactor.lesson02;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/** 手动控制需求：先建立订阅，再请求两项，最后请求一项。 */
public class RequestDrivesSignals {
    public static void main(String[] args) {
        BaseSubscriber<Integer> learner = new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                System.out.println("onSubscribe：已建立订阅，暂不请求");
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("onNext " + value);
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("onComplete");
            }

            @Override
            protected void hookOnError(Throwable error) {
                System.out.println("onError " + error);
            }
        };

        Flux<Integer> source = Flux.range(1, 3)
            .doOnRequest(n -> System.out.println("request " + n));

        System.out.println("调用 subscribe");
        source.subscribe(learner);
        System.out.println("subscribe 已返回，尚未收到元素");
        learner.request(2);
        System.out.println("第一次 request 已返回");
        learner.request(1);
    }
}
