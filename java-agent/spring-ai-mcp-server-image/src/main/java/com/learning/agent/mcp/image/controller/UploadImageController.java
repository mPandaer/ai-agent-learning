package com.learning.agent.mcp.image.controller;

import com.learning.agent.mcp.image.dto.image.GenUploadUrlResponse;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import com.learning.agent.mcp.image.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadImageController {

    @Autowired
    private UploadImageService uploadImageService;


    @PostMapping("/image/upload/{sign}")
    public ResponseEntity<UploadResponse> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable String sign) {
        UploadResponse uploadResponse = uploadImageService.uploadImage(file,sign);
        return ResponseEntity.ok(uploadResponse);
    }


    @GetMapping("/image/upload/url")
    public ResponseEntity<GenUploadUrlResponse> genImageUrl() {
        GenUploadUrlResponse genUploadUrlResponse = uploadImageService.genUploadUrl();
        return ResponseEntity.ok(genUploadUrlResponse);
    }
}
