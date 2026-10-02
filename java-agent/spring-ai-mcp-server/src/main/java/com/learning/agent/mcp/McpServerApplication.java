package com.learning.agent.mcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring AI MCP Server 启动类。
 *
 * <p>启动后 MCP 端点默认暴露在 {@code http://localhost:8081/mcp}（Streamable-HTTP），
 * 任何支持 Streamable-HTTP 的 MCP 客户端（Claude Desktop、Cline、MCP Inspector 等）都可以接入。
 */
@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }
}
