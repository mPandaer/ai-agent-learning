package com.learning.agent.mcp.image.service;

import com.learning.agent.mcp.image.dto.image.GenUploadUrlResponse;
import com.learning.agent.mcp.image.dto.minio.UploadRequest;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import com.learning.agent.mcp.image.utils.SignUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Slf4j
@Service
public class UploadImageService {


    @Autowired
    private MinioService minioService;

    @Value("${upload.images.base-url}")
    private String scheme;

    public static final String BASE_URL = "/image/upload/";

    private static final String MEDIA_TYPE = "image/png";

    public GenUploadUrlResponse genUploadUrl() {
        String suffix = SignUtils.getTimeSign();
        String url = scheme + BASE_URL + suffix;
        return GenUploadUrlResponse.builder().uploadImageUrl(url).build();
    }


    @SneakyThrows
    public UploadResponse uploadImage(MultipartFile file,String sign) {
        if (!StringUtils.hasText(sign) || !validateSign(sign)) {
            log.info("sign没有值，或者验证sign失败");
            return null;
        }

        String filename = file.getOriginalFilename();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        filename = filename + Instant.now().getEpochSecond();
        byte[] digest = md.digest(filename.getBytes(StandardCharsets.UTF_8));
        String objectName = HexFormat.of().formatHex(digest) + ".png";

        UploadRequest request = UploadRequest.builder()
                .inputStream(file.getInputStream())
                .objectName(objectName).mediaType(MEDIA_TYPE).build();
        return minioService.upload(request);
    }

    public boolean validateSign(String sign) {
        return SignUtils.validateTimeSign(sign);
    }

}
