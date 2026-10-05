package com.learning.agent.mcp.image.controller;


import com.learning.agent.mcp.image.dto.image.Image2ImageRequest;
import com.learning.agent.mcp.image.dto.image.Image2ImageResponse;
import com.learning.agent.mcp.image.dto.image.Text2ImageResponse;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import com.learning.agent.mcp.image.service.GenImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageModelController {


    @Autowired
    private GenImageService genImageService;


    @GetMapping("/text2image")
    public ResponseEntity<Text2ImageResponse> text2Image(String message) throws IOException {
        Text2ImageResponse text2ImageResponse = genImageService.text2Image(message);
        return ResponseEntity.ok(text2ImageResponse);
    }

    @PostMapping("/image2image")
    public ResponseEntity<Image2ImageResponse> image2Image(String message,String imageUrl) throws IOException {
        Image2ImageRequest image2ImageRequest = new Image2ImageRequest();
        image2ImageRequest.setImageUrl(imageUrl);
        image2ImageRequest.setPrompt(message);
        Image2ImageResponse response = genImageService.image2Image(image2ImageRequest);
        return ResponseEntity.ok(response);
    }
}
