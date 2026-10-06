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


    public Text2ImageResponse text2Image(String prompt) {
        ImageClient imageClient = imageClientManager.getImageClient();
        Text2ImageRequest text2ImageRequest = new Text2ImageRequest();
        text2ImageRequest.setPrompt(prompt);
        return imageClient.text2Image(text2ImageRequest);
    }

    public Image2ImageResponse image2Image(Image2ImageRequest request) {
        ImageClient imageClient = imageClientManager.getImageClient();
        return imageClient.image2Image(request);
    }

}
