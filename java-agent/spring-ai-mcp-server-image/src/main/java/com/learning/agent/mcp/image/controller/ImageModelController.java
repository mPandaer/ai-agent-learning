package com.learning.agent.mcp.image.controller;


import com.learning.agent.mcp.image.dto.image.Text2ImageResponse;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import com.learning.agent.mcp.image.service.GenImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageModelController {


    @Autowired
    private GenImageService genImageService;


    @GetMapping("/text2image")
    public ResponseEntity<Text2ImageResponse> imageModel(String message) throws IOException {
        Text2ImageResponse text2ImageResponse = genImageService.text2Image(message);
        return ResponseEntity.ok(text2ImageResponse);
    }
}
