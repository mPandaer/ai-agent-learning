package com.learning.agent.custom.api;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.learning.agent.custom.core.ReactAgent;
import com.learning.agent.custom.core.manager.ToolManager;
import com.learning.agent.custom.tool.SearchTool;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 冒烟自检端点：只验证"基础积木"装配是否正常，不调用模型。
 *
 * <p>Agent 逻辑尚未实现时，用它确认 Spring AI 的 ChatModel / 工具注册是否就绪。
 */
@RestController
@RequestMapping("/custom-agent")
public class SelfCheckController {

    @Autowired
    private ChatModel chatModel;

    @GetMapping("/run")
    public String agentRun(String message) {
        ToolManager toolManager = new ToolManager(Arrays.stream(MethodToolCallbackProvider.builder().toolObjects(new SearchTool()).build().getToolCallbacks()).toList());
        ReactAgent agent = ReactAgent.builder().model(chatModel).toolManager(toolManager).build();
        return agent.run(message).getResult().getOutput().getText();
    }
}
