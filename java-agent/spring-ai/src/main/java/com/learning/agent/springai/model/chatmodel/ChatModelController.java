package com.learning.agent.springai.model.chatmodel;


import com.learning.agent.springai.model.chatmodel.bean.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.print.Book;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("model/chat")
public class ChatModelController {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private ChatClient chatClient;

    @Value("classpath:prompts/prompt_demo.md")
    private Resource promptDemo;



    @GetMapping("/hello")
    public String hello() {
        return chatModel.call("你好呀，你是谁呀？");
    }


    @GetMapping("/stream")
    public Flux<String> helloStream(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8);
        return chatModel.stream("你好呀，你是谁呀？请自我介绍一下");
    }


    @GetMapping("/client")
    public String clientChat(String message) {
        return chatClient.prompt().user(message).call().content();
    }


    @GetMapping("/client/stream")
    public Flux<String> clientChatStream(String message,HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8);
        return chatClient.prompt().user(message).stream().content();
    }

    @GetMapping("/prompt/stream")
    public Flux<String> clientPromptStream(String message,String age,HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8);
        PromptTemplate pt = PromptTemplate.builder().resource(promptDemo).build();
        Prompt prompt = pt.create(Map.of("age", age));

        return chatClient.prompt().system(prompt.getContents()).user(message).stream().content();
    }

    @GetMapping("/output/bean")
    public User clientOutputBean(String message,HttpServletResponse response) {

        return chatClient.prompt().system("你是一个有用的工具").user(message).call().entity(User.class);
    }

    @GetMapping("/output/beans")
    public List<User> clientOutputBeans(String message,HttpServletResponse response) {
        return chatClient.prompt().system("你是一个有用的工具").user(message).call().entity(new ParameterizedTypeReference<List<User>>() {});
    }
}
