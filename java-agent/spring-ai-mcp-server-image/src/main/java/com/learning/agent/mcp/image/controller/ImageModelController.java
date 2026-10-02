package com.learning.agent.mcp.image.controller;


import com.learning.agent.mcp.image.dto.MinioUploadResponseDTO;
import com.learning.agent.mcp.image.service.GenImageService;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.util.JSONPObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;

@RestController
public class ImageModelController {


    @Autowired
    private GenImageService genImageService;


    @GetMapping("/genImage")
    public ResponseEntity<MinioUploadResponseDTO> imageModel(String message) throws IOException {
        MinioUploadResponseDTO response = genImageService.genImage(message);
        return ResponseEntity.ok(response);
    }
}
