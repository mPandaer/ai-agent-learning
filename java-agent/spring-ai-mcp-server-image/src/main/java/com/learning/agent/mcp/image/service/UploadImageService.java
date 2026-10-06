package com.learning.agent.mcp.image.service;

import com.learning.agent.mcp.image.dto.image.GenUploadUrlResponse;
import com.learning.agent.mcp.image.dto.minio.UploadRequest;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import com.learning.agent.mcp.image.utils.SignUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.mcp.annotation.McpTool;
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

    @McpTool(description = "生成一个有效期为 10 分钟的图片上传链接，用于上传本地参考图片。获取链接后，使用 HTTP POST 请求，以 multipart/form-data 格式上传图片，文件字段名为 file。上传成功后返回图片访问 URL，可用于后续图片编辑。")
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
