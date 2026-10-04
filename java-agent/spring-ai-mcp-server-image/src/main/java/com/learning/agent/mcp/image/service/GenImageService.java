package com.learning.agent.mcp.image.service;

import com.learning.agent.mcp.image.dto.MinioUploadRequestDTO;
import com.learning.agent.mcp.image.dto.MinioUploadResponseDTO;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Service
public class GenImageService {


    @Autowired
    private MinioService minioService;

    @Autowired
    private ImageModel imageModel;

    @McpTool(description = "用自然语言生成图片")
    public MinioUploadResponseDTO genImage(@McpToolParam(description = "针对待生成图片的一段文本描述") String message) {
        ImagePrompt imagePrompt = new ImagePrompt(message);
        ImageResponse response = imageModel.call(imagePrompt);
        ImageGeneration result = response.getResult();
        if (result == null) {
            return null;
        }

        String b64Image = result.getOutput().getB64Json();
        byte[] bytes = b64Decode(b64Image);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        MinioUploadRequestDTO request = MinioUploadRequestDTO.builder()
                .inputStream(byteArrayInputStream)
                .mediaType(MediaType.IMAGE_PNG_VALUE)
                .objectName(genImageName())
                .build();
        return minioService.upload(request);
    }


    private byte[] b64Decode(String b64Image) {
        return Base64.getDecoder().decode(b64Image);
    }

    private String genImageName() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return String.format("%s-%s.png",uuid,LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss")));
    }

}
