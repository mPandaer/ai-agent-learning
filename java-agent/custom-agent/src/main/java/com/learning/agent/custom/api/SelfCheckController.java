package com.learning.agent.custom.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 冒烟自检端点：只验证"基础积木"装配是否正常，不调用模型。
 *
 * <p>Agent 逻辑尚未实现时，用它确认 Spring AI 的 ChatModel / 工具注册是否就绪。
 */
@RestController
@RequestMapping("/custom-agent")
public class SelfCheckController {

    private final ObjectProvider<ChatModel> chatModels;

    private final ObjectProvider<ToolCallback> toolCallbacks;

    public SelfCheckController(ObjectProvider<ChatModel> chatModels, ObjectProvider<ToolCallback> toolCallbacks) {
        this.chatModels = chatModels;
        this.toolCallbacks = toolCallbacks;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        ChatModel chatModel = this.chatModels.getIfAvailable();
        long toolCallbackCount = this.toolCallbacks.stream().count();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("module", "custom-agent");
        body.put("status", chatModel != null ? "READY" : "NO_CHAT_MODEL");
        body.put("chatModel", chatModel != null ? chatModel.getClass().getSimpleName() : null);
        body.put("toolCallbackBeans", toolCallbackCount);
        body.put("note", "Agent 循环尚未实现，当前仅校验 Spring AI 基础积木装配");
        return body;
    }
}
