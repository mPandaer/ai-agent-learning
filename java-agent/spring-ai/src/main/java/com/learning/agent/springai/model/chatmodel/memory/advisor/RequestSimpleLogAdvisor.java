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
import org.springframework.stereotype.Component;


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
        logger.debug("========================开始========================");
        request.prompt().getInstructions().stream().map(item -> String.format("[%s] %s", item.getMessageType(), item.getText())).forEach(logger::debug);
        logger.debug("========================结束========================");
    }
}
