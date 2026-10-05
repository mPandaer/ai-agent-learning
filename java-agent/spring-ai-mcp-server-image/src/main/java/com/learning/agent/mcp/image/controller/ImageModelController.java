package com.learning.agent.mcp.image.controller;


import com.learning.agent.mcp.image.dto.image.Image2ImageRequest;
import com.learning.agent.mcp.image.dto.image.Image2ImageResponse;
import com.learning.agent.mcp.image.dto.image.Text2ImageResponse;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import com.learning.agent.mcp.image.service.GenImageService;
import com.learning.agent.mcp.image.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageModelController {


    @Autowired
    private GenImageService genImageService;

    @Autowired
    private MinioService minioService;




    @GetMapping("/text2image")
    public ResponseEntity<Text2ImageResponse> text2Image(String message) throws IOException {
        Text2ImageResponse text2ImageResponse = genImageService.text2Image(message);
        return ResponseEntity.ok(text2ImageResponse);
    }

    @PostMapping("/image2image")
    public ResponseEntity<Image2ImageResponse> image2Image(@RequestBody Image2ImageRequest request) throws IOException {
        Image2ImageResponse response = genImageService.image2Image(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/url")
    public ResponseEntity<String> image2Image() throws IOException {
        String url = minioService.getObjectUrl("3858574b543010834c6f4b14b9ab8eac4dcd4e9e6a3d42380f191988c89a1747.png", 12);
        return ResponseEntity.ok(url);
    }
}
