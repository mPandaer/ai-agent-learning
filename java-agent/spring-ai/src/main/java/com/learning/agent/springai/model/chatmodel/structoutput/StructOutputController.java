package com.learning.agent.springai.model.chatmodel.structoutput;


import com.learning.agent.springai.model.chatmodel.memory.advisor.RequestSimpleLogAdvisor;
import com.learning.agent.springai.model.chatmodel.structoutput.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/chat/structoutput")
@RestController
public class StructOutputController {

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    @PostConstruct
    public void initChatClient() {
        chatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(
                        new RequestSimpleLogAdvisor()
//                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @GetMapping("mockUser")
    public List<User> getUsers() {
        return chatClient.prompt().user("生成5个用户信息").call().entity(new ParameterizedTypeReference<>() {
        });
    }


}
