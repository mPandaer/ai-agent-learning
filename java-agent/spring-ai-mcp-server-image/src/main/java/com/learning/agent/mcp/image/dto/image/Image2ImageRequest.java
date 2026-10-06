package com.learning.agent.mcp.image.dto.image;

import lombok.Data;
import org.springframework.ai.mcp.annotation.McpToolParam;

@Data
public class Image2ImageRequest {

    @McpToolParam(description = "参考图可访问的URL，如果只有本地图片文件，需要借助上传工具进行图片上传")
    private String imageUrl;

    @McpToolParam(description = "自然语言描述")
    private String prompt;

}
