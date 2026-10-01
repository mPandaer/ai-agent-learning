package com.learning.agent.springai.model.chatmodel.promptstemplate;

import com.learning.agent.springai.model.chatmodel.memory.advisor.RequestSimpleLogAdvisor;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RequestMapping("/chat/prompts")
@RestController
public class PromptsTemplateController {

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    @PostConstruct
    public void initChatClient() {
        chatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(
                        new RequestSimpleLogAdvisor()
                )
                .build();
    }


    @GetMapping("/topic")
    public Flux<String> promptTemplateTopic(String topic) {
        PromptTemplate template = PromptTemplate.builder().template("你是{topic}方面的专家，吐槽一下{topic}").build();
        template.add("topic", topic);
        Message userMessage = template.createMessage();
        return chatClient.prompt().messages(userMessage).stream().content();

    }


    @Autowired
    private PromptTemplate roleActPromptTemplate;

    @GetMapping("/userDesc")
    public Flux<String> promptTemplateUserDesc(String userDesc) {
        Message userMessage = roleActPromptTemplate.createMessage(Map.of("userDesc", userDesc));
        return chatClient.prompt().messages(userMessage).stream().content();

    }


}
