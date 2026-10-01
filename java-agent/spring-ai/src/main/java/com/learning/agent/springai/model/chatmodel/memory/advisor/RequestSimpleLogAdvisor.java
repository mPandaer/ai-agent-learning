package com.learning.agent.springai.model.chatmodel.memory.advisor;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@NoArgsConstructor
public class RequestSimpleLogAdvisor extends SimpleLoggerAdvisor {

    private static final Log logger = LogFactory.getLog(RequestSimpleLogAdvisor.class);

    @Override
    public @NonNull ChatClientResponse adviseCall(@NonNull ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        logRequest(chatClientRequest);

        return callAdvisorChain.nextCall(chatClientRequest);
    }

    @Override
    protected void logRequest(ChatClientRequest request) {
        String uuid = UUID.randomUUID().toString();
        request.context().put("log-id", uuid);
        logger.debug("========================请求开始{"+uuid+"}========================");
        request.prompt().getInstructions().stream().map(item -> String.format("[%s] %s", item.getMessageType(), item.getText())).forEach(logger::debug);
        logger.debug("========================请求结束{"+uuid+"}========================");
    }

    @Override
    protected void logResponse(ChatClientResponse chatClientResponse) {
        String uuid = (String) chatClientResponse.context().get("log-id");
        logger.debug("========================响应开始{"+uuid+"}========================");
        Optional.ofNullable(chatClientResponse.chatResponse())
                .orElse(ChatResponse.builder().generations(List.of(new Generation(AssistantMessage.builder().content("无响应内容(logger)").build()))).build())
                .getResults().stream().map(item -> item.getOutput().getText()).forEach(logger::debug);
        logger.debug("========================响应结束{"+uuid+"}========================");
    }
}
