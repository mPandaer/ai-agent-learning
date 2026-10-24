package com.learning.agent.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 手写 Agent 核心逻辑的入口。
 *
 * <p>本模块只依赖 Spring AI 的基础积木（ChatModel / ToolCallback / ChatMemory），
 * Agent 的决策循环、工具编排、记忆策略、停止条件等全部自己实现。
 */
@SpringBootApplication
public class CustomAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomAgentApplication.class, args);
    }
}
