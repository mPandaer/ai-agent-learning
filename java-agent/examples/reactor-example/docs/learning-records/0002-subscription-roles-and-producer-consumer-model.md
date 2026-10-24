# 建立订阅角色与生产消费的整体模型

用户在第二课中主动追问 learner、Subscription、隐式订阅者和 Disposable 的关系，在解释后明确表示理解，并自行将 Reactor 概括为生产者—消费者模式。这表明用户已经形成整体角色模型，但 request 的额度计算、终止信号和取消行为尚未通过独立预测验证，下一次应先做短时回忆与预测，再继续推进。

## Evidence

- 能主动识别 `learner.request(2)` 与“request 属于 Subscription”之间看似矛盾的地方；已解释 BaseSubscriber 保存 Subscription 并转交请求的机制。
- 已讲解无参 `subscribe()` 创建内部订阅者，普通无参/lambda 订阅默认请求无限量数据；第一课的打印来自链上的 `doOnNext`。
- 已讲解 `Subscription` 通过 `onSubscribe` 交给订阅者，控制 request/cancel；无参/lambda `subscribe` 返回的 `Disposable` 向调用者提供取消入口，`dispose()` 最终取消订阅。两者没有接口继承关系；传入 Subscriber 的 subscribe 重载返回 void。
- 用户主动提出“整体上是生产者—消费者模式”；已在此基础上补充需求控制、操作符组合与同步执行的可能性。

## Implications

下次从第二课复习开始，暂不认定第二课练习已经掌握。先请用户不用笔记解释 Subscriber、Subscription、Disposable 各自的职责，再预测 `Flux.range(1, 3)` 在建立订阅但不请求、请求两项、再请求一项三个阶段的信号；核对完成信号不消耗需求额度。通过后可沿着 map 的上下游双重角色继续追踪操作符链。冷热与重放的间隔复习可引用 [[0001-cold-execution-versus-hot-replay.md]]。
