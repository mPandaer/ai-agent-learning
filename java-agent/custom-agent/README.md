# custom-agent

基于 **Spring AI 基础积木**，自己实现 Agent 核心逻辑的学习模块。

## 1. 定位与边界

| | 说明 |
|---|---|
| 做什么 | 手写 Agent 的决策循环、工具编排、记忆策略、停止条件、流式输出 |
| 不做什么 | 不引入任何 Agent 框架（Spring AI Alibaba、spring-ai-agent-utils、LangChain4j 等） |
| 为什么 | 先看清"Agent 到底由哪些原语拼出来"，再回头用框架才不会变成调包侠 |

依赖边界（见 `pom.xml`）：

```
spring-ai-model          ← 最底层抽象：ChatModel / Prompt / Message /
                            ToolCallback / @Tool / ToolCallbackProvider /
                            ChatMemory / ToolCallingManager
spring-ai-client-chat    ← ChatClient（仅作对照组，不用于实现主流程）
spring-ai-starter-model-openai   ← OpenAI 兼容模型接入
spring-boot-starter-web  ← HTTP 调试入口
```

**刻意没有引入**：`spring-ai-starter-model-chat-memory-repository-jdbc`（记忆自己实现）、
`spring-ai-alibaba-*`、`spring-ai-agent-utils`。

## 2. 要自己实现的 Agent 骨架

一个最小可用的 Agent 循环，本质上就是下面这段伪代码：

```
messages = [system, ...memory, user]
for step in 1..maxSteps:
    response = chatModel.call(prompt(messages, tools))
    if response 没有 toolCalls:
        return response 文本
    for call in toolCalls:
        result = 执行工具(call)
        messages += assistant(toolCalls) + tool(result)
return "达到最大步数仍未收敛"
```

拆成几块，正好对应 Spring AI 给出的原语：

| 自己写 | 借助 Spring AI 提供 |
|---|---|
| 循环 / 停止条件 / 最大步数 / 超时 | `ChatModel#call`、`ChatResponse` |
| 每轮 messages 的拼装（system + 历史 + 工具回填） | `Prompt`、`UserMessage`、`AssistantMessage`、`ToolResponseMessage` |
| 工具注册与 schema 生成 | `@Tool`、`@ToolParam`、`MethodToolCallbackProvider` |
| 工具执行 | `ToolCallingManager#executeToolCalls`（也可完全自己反射调用） |
| 记忆裁剪 / 摘要 / 持久化 | `ChatMemory` 接口、`MessageWindowChatMemory` |
| 流式输出与思考过程展示 | `ChatModel#stream`、`StreamingChatResponseHandler` |
| 可观测性（每步耗时、token、工具入参出参） | 自己的日志 / Micrometer |

## 3. 建议的推进顺序

- [ ] **M1 裸循环**：单工具 + 非流式 `ChatModel#call`，把 ReAct 主循环跑通，打印每轮 messages
- [ ] **M2 工具注册**：`@Tool` 方法扫描 → `ToolCallback` 列表 → 按需过滤（对照 `spring-ai` 模块里的 `FindToolAdvisor`）
- [ ] **M3 记忆**：自实现 `ChatMemory`（窗口 / 摘要），对比 `MessageWindowChatMemory`
- [ ] **M4 流式**：`stream()` + SSE，边生成边推工具调用事件
- [ ] **M5 健壮性**：最大步数、工具异常回填、幂等、超时、重试、token 预算
- [ ] **M6 对照组**：同一套工具分别用"自己的循环"和 `ChatClient` 跑，比较行为差异

约定：每个里程碑保持可运行 + 有对应 `api/*.http` 调试脚本。

## 4. 运行

```bash
cd java-agent
mvn -pl custom-agent -am spring-boot:run
# 或
mvn -pl custom-agent -am package && java -jar custom-agent/target/custom-agent-1.0.0.jar
```

冒烟自检（不调用模型，只验证 Spring AI 积木装配）：

```bash
curl -s http://localhost:8083/custom-agent/health
```

预期返回 `status: READY`，并给出当前 `ChatModel` 实现类与已注册的 `ToolCallback` 数量。
