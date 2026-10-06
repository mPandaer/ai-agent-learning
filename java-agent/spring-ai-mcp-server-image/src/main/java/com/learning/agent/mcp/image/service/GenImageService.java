package com.learning.agent.mcp.image.service;

import com.learning.agent.mcp.image.client.ImageClient;
import com.learning.agent.mcp.image.client.ImageClientManager;
import com.learning.agent.mcp.image.dto.image.*;
import com.learning.agent.mcp.image.dto.minio.UploadRequest;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Service
public class GenImageService {

    @Autowired
    private ImageClientManager imageClientManager;


    @McpTool(description = "用自然语言生成图片并返回一个可访问图片的URL地址")
    public Text2ImageResponse text2Image(@McpToolParam(description = "一段自然语言") String prompt) {
        ImageClient imageClient = imageClientManager.getImageClient();
        Text2ImageRequest text2ImageRequest = new Text2ImageRequest();
        text2ImageRequest.setPrompt(prompt);
        return imageClient.text2Image(text2ImageRequest);
    }

    @McpTool(description = "根据参考图片 URL 和文字编辑指令修改图片，返回编辑后图片的访问 URL。适用于替换背景、调整风格、添加或移除画面元素等场景。")
    public Image2ImageResponse image2Image(Image2ImageRequest request) {
        ImageClient imageClient = imageClientManager.getImageClient();
        return imageClient.image2Image(request);
    }

}
