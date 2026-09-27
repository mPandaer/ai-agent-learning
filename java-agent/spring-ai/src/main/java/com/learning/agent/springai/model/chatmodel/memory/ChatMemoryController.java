package com.learning.agent.springai.model.chatmodel.memory;


import com.learning.agent.springai.model.chatmodel.memory.advisor.RequestSimpleLogAdvisor;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat/memory")
public class ChatMemoryController {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private JdbcChatMemoryRepository chatMemoryRepository;

    private ChatClient chatClient;



    @GetMapping("/call")
    public String chatMemory(String message,String chatId) {
        return chatClient.prompt().user(message)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID,chatId))
                .call().content();
    }





    @PostConstruct
    public void initChatClient() {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository).maxMessages(3).build();
        chatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(
                        new RequestSimpleLogAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
}
