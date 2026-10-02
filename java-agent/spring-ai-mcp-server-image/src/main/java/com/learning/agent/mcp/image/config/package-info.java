/**
 * 生图 MCP Server 配置包。
 *
 * <p>约定：这里放置 MCP Server / ImageModel / 对象存储相关的配置类，例如：
 * <ul>
 *   <li>自定义 {@code OpenAiImageModel}（需要覆盖默认装配时）</li>
 *   <li>{@code MinioClient} 的 {@code @Bean}：Spring Boot 没有 MinIO 自动装配，必须手写</li>
 *   <li>{@code @ConfigurationProperties(prefix = "minio")} 绑定端点 / 密钥 / bucket</li>
 *   <li>ToolCallback 注册等</li>
 * </ul>
 */
package com.learning.agent.mcp.image.config;
