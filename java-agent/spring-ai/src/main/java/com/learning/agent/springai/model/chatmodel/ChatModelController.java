package com.learning.agent.springai.model.chatmodel;


import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerResponse;

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

    @GetMapping("/hello/stream")
    public Flux<String> helloStream() {
        UserMessage userMessage = UserMessage.builder().text("你好呀，你是谁呀？").build();
        Prompt prompt = Prompt.builder().messages(userMessage).build();
        return chatModel.stream(prompt)
                .mapNotNull(ChatResponse::getResult).mapNotNull(Generation::getOutput).mapNotNull(Message::getText);
    }


    @GetMapping("/hello/streamv2")
    public Flux<String> helloStreamV2() {
        return chatModel.stream("你好呀，你是谁呀？");
    }

}
