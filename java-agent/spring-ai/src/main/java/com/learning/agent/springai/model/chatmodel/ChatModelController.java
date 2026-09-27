package com.learning.agent.springai.model.chatmodel;


import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("model/chat")
public class ChatModelController {

    @Autowired
    private ChatModel chatModel;

    @GetMapping("/hello-llm")
    public String helloLLM() {
        return "hello,LLM";
    }

    @GetMapping("/hello")
    public ChatResponse hello() {
        UserMessage userMessage = UserMessage.builder().text("你好呀，你是谁呀").build();
        Prompt prompt = Prompt.builder().messages(userMessage).build();
        return chatModel.call(prompt);
    }

}
