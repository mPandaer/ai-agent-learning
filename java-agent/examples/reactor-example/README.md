# Project Reactor 学习示例

这个模块既是 Reactor 的可运行示例，也是课程资料的归档位置。课程从信号协议开始，逐步深入订阅生命周期、实现原理及 Spring Boot 生产实践。

## 课程

1. [Reactor 是信号协议，不是异步魔法](docs/lessons/0001-reactor-is-a-signal-protocol.html) — 组装与订阅、Publisher/Subscriber/Subscription、信号方向。
2. [Reactor 信号模型速查表](docs/reference/reactor-signal-model.html)

打开 HTML 文件可阅读课程；本课配套代码：`src/main/java/com/learning/reactor/lesson01/AssemblyVsSubscription.java`。

## 运行示例

```bash
cd java-agent/examples/reactor-example
mvn compile
```

在 IDE 中运行 `AssemblyVsSubscription.main()`。预期先输出 `组装完成`，然后输出 `处理 1`、`处理 2`、`处理 3`。观察 `flow.subscribe()`：注释掉它后，`doOnNext` 中的处理不会执行。

## 学习目标与资料

- [Mission](docs/MISSION.md)：这套学习路径要解决的问题和成功标准。
- [Resources](docs/RESOURCES.md)：官方参考文档、规范与社区入口。
- [Project Reactor Reference Guide](https://projectreactor.io/docs/core/release/reference/)
- [Reactive Streams JVM Specification](https://github.com/reactive-streams/reactive-streams-jvm)
