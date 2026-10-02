package com.learning.agent.mcp.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring AI 生图 MCP Server 启动类。
 *
 * <p>把 OpenAI 生图模型（ImageModel）的调用能力封装成标准 MCP Server 对外提供：
 * 启动后 MCP 端点默认暴露在 {@code http://localhost:8082/mcp}（Streamable-HTTP），
 * 任何支持 Streamable-HTTP 的 MCP 客户端（Claude Desktop、Cline、MCP Inspector 等）都可以接入。
 */
@SpringBootApplication
public class ImageMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageMcpServerApplication.class, args);
    }
}
