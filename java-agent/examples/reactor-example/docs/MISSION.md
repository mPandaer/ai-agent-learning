# Mission: Project Reactor 与生产级 Java 响应式应用

## Why
作为有一年半经验、熟悉 Spring/Spring Boot 的 Java 工程师，用户希望从 Reactor 的抽象与底层信号机制入手，能读懂其实现思路，并在实际业务中判断何时适用、如何正确组合 API，最终构建可靠、可测试、可观测的生产级应用。

## Success looks like
- 能解释 Publisher、Subscriber、Subscription、信号、需求（request）与取消之间的关系，并能用 Reactor 源码验证关键执行路径。
- 能熟练组合 Mono/Flux、错误处理、调度器、并发与背压 API，避免阻塞与 fire-and-forget 等常见生产风险。
- 能在 Spring Boot 项目中实现、测试并评估一条端到端响应式业务链路，说明其资源/延迟/吞吐取舍。

## Constraints
- 用户是 Java 初中级工程师，工作约一年半；熟悉 Spring、Spring Boot，尚未接触响应式编程。
- 从直观 API 和 Reactive Streams 信号模型循序渐进，再进入源码与生产实践。
- 以短小、可运行、逐步增加复杂度的课程为主；使用可信的一手文档。

## Out of scope
- 初期不深入 RxJava、Kotlin Flow 或其他响应式框架比较。
- 不把 Reactor 等同于“异步语法糖”；性能优化结论需基于具体负载与测量，不预设响应式一定更快。
