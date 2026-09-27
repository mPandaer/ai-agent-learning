package com.learning.agent.springai.model.chatmodel.client;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/client")
public class ChatClientController {

    @Autowired
    private ChatClient openAiChatClient;


    @GetMapping("hello")
    public String clientHello() {
        return openAiChatClient.prompt().user("你好呀，讲个笑话").call().content();
    }

}
