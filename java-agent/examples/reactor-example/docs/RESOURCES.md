# Project Reactor Resources

## Knowledge

- [Project Reactor Reference Guide](https://projectreactor.io/docs/core/release/reference/)
  Reactor Core 官方参考手册，覆盖响应式编程模型、operator、线程调度、背压、测试和调试；课程/API 行为的首要依据。
- [Reactor Core source code](https://github.com/reactor/reactor-core)
  官方源码；在建立信号模型后，用于追踪具体 publisher/operator 的订阅与 request/cancel 实现。
- [Reactive Streams Specification](https://github.com/reactive-streams/reactive-streams-jvm)
  JVM 上异步流处理的规范与接口，定义 Publisher、Subscriber、Subscription 和背压协议。
- [Spring Framework: Web Reactive](https://docs.spring.io/spring-framework/reference/web/webflux.html)
  Spring WebFlux 官方文档；用于理解 Reactor 如何进入 Web 应用端到端链路，以及响应式服务边界。

## Wisdom (Communities)

- [Stack Overflow: project-reactor tag](https://stackoverflow.com/questions/tagged/project-reactor)
  针对具体 API、调试与使用问题查阅既有问答；提问时应提供最小复现和 Reactor 版本。
- [Reactor Core GitHub Discussions](https://github.com/reactor/reactor-core/discussions)
  官方仓库社区讨论；适合了解库的设计与疑难问题，源码/API 行为仍以官方 reference 和实现为准。
