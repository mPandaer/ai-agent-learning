package com.learning.agent.springai.model.chatmodel.toolcall;


import com.learning.agent.springai.model.chatmodel.memory.advisor.RequestSimpleLogAdvisor;
import com.learning.agent.springai.model.chatmodel.toolcall.tools.DateTimeTools;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/model/chat/toolcall")
public class ToolCallController {


    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;


    @GetMapping("time")
    public String toolCall() {
        return chatClient.prompt().user("现在是什么时候了").call().content();
    }



    @PostConstruct
    public void initChatClient() {
        chatClient = ChatClient
                .builder(chatModel)
                .defaultTools(new DateTimeTools())
                .defaultAdvisors(
                        new RequestSimpleLogAdvisor()
                )
                .build();
    }
}
