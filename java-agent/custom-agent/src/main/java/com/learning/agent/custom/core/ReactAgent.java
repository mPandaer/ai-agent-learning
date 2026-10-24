package com.learning.agent.custom.core;

import com.learning.agent.custom.core.manager.ToolManager;
import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
public class ReactAgent {
    private ChatModel model;
    private ToolManager toolManager;
    @Builder.Default
    private int maxRounds = 30;


    public ChatResponse run(String message) {
        List<Message> context = new ArrayList<>();
        // 初始化 上下文
        SystemMessage reactSystemMessage = loadReactSystemMessage();
        context.add(reactSystemMessage);
        context.add(new UserMessage(message));

        // 进入ReAct循环
        int currentRound = 0;
        while (true) {
            currentRound++;
            if (currentRound > maxRounds) {
                // 达到最大轮次，任务没有完成也需要停止
                context.add(new AssistantMessage("当前任务已达到最大迭代轮次，强制停止"));
                return model.call(new Prompt(context));
            }

            ChatResponse chatResponse = model.call(promptWithTool(context));

            if (!chatResponse.hasToolCalls()) {
                // 最终回答，返回结果
                return chatResponse;
            }

            Generation generation = chatResponse.getResult();
            if (generation == null) {
                context.add(new AssistantMessage("当前迭代，LLM生成的内容为空"));
                continue;
            }

            AssistantMessage assistantMessage = generation.getOutput();
            context.add(assistantMessage);

            List<AssistantMessage.ToolCall> toolCalls = assistantMessage.getToolCalls();
            List<ToolResponseMessage.ToolResponse> toolCallResponses = callTools(toolCalls);
            context.add(ToolResponseMessage.builder().responses(toolCallResponses).build());
        }
    }

    private List<ToolResponseMessage.ToolResponse> callTools(List<AssistantMessage.ToolCall> toolCalls) {
        List<ToolResponseMessage.ToolResponse> toolResponses = new ArrayList<>();
        toolCalls.forEach(toolCall -> {
            String result = toolManager.callTool(toolCall.name(), toolCall.arguments());
            toolResponses.add(new ToolResponseMessage.ToolResponse(toolCall.id(),toolCall.name(), result));
        });
        return toolResponses;
    }

    private Prompt promptWithTool(List<Message> context) {
        ChatOptions chatOptions = model.getOptions();
        if (!(chatOptions instanceof ToolCallingChatOptions toolBase)) {
            throw new IllegalArgumentException("当前模型不支持工具调用: " + chatOptions.getClass());
        }
        chatOptions = toolBase.mutate()
                .toolCallbacks(toolManager.getToolCallbacks())
                .build();
        return new Prompt(context,chatOptions);
    }


    @SneakyThrows
    private SystemMessage loadReactSystemMessage() {
        String reactAgentSystemMessagePath = "prompts/ReAct_Agent.md";
        Resource resource = new ClassPathResource(reactAgentSystemMessagePath);
        String systemPrompt = resource.getContentAsString(StandardCharsets.UTF_8);
        return new SystemMessage(systemPrompt);
    }

}
