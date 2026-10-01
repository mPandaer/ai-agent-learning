package com.learning.agent.springai.model.chatmodel.toolcall.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.http.converter.autoconfigure.ClientHttpMessageConvertersCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


@Component
public class FindToolAdvisor implements CallAdvisor {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ClientHttpMessageConvertersCustomizer clientConvertersCustomizer;


    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        Object dateTimeTool = applicationContext.getBean("dateTimeTool");
        ToolCallback[] from = ToolCallbacks.from(dateTimeTool);
        ChatOptions options = ((ToolCallingChatOptions) chatClientRequest.prompt().getOptions()).mutate().toolCallbacks(from).build();
        Prompt prompt = chatClientRequest.prompt();
        Prompt processedPrompt = new Prompt(prompt.getInstructions(),options);
        ChatClientRequest newChatClientRequest = chatClientRequest.mutate().prompt(processedPrompt).build();
        return callAdvisorChain.nextCall(newChatClientRequest);

    }

    @Override
    public String getName() {
        return "findToolAdvisor";
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
