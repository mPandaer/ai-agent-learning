package com.learning.agent.custom.core.manager;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ToolManager {
    private Map<String, ToolCallback> toolCallbackMap = new HashMap<>();

    public ToolManager(List<ToolCallback> toolCallbacks) {
        initToolCallbackMap(toolCallbacks);
    }

    private void initToolCallbackMap(List<ToolCallback> toolCallbacks) {
        if (CollectionUtils.isEmpty(toolCallbacks)) {
            return;
        }
        this.toolCallbackMap = toolCallbacks.stream().collect(Collectors.toMap(
                toolCallback -> toolCallback.getToolDefinition().name(),
                Function.identity()
        ));
    }


    public String callTool(String name,String params) {
        ToolCallback toolCallback = toolCallbackMap.get(name);
        if (toolCallback == null) {
            return notFoundTool(name);
        }
        return toolCallback.call(params);
    }


    public ToolCallback[]  getToolCallbacks() {
        return toolCallbackMap.values().toArray(new ToolCallback[0]);
    }

    private String notFoundTool(String name) {
        return String.format("Tool %s not found", name);
    }
}
