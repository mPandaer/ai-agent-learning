package com.learning.agent.mcp.image.service;


import com.learning.agent.mcp.image.config.minio.MinioConfigProperties;
import com.learning.agent.mcp.image.dto.MinioUploadRequestDTO;
import com.learning.agent.mcp.image.dto.MinioUploadResponseDTO;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfigProperties minioConfigProperties;

    private static final Long UNKNOWN_OBJECT_SIZE = -1L;
    private static final Long DEFAULT_PART_SIZE = 10485760L;


    public MinioUploadResponseDTO upload(MinioUploadRequestDTO uploadDTO) {
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfigProperties.getBucketName())
                .contentType(uploadDTO.getMediaType())
                .stream(uploadDTO.getInputStream(),UNKNOWN_OBJECT_SIZE, DEFAULT_PART_SIZE)
                .object(uploadDTO.getObjectName())
                .build();

        try {
            ObjectWriteResponse response = minioClient.putObject(args);
            String url = getObjectUrl(response.object(), 6);
            return new MinioUploadResponseDTO(response.object(),url,6);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("上传失败", e);
        }
        return null;
    }

    private String getObjectUrl(String objectName,Integer expireHours) {
        GetPresignedObjectUrlArgs arg = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(minioConfigProperties.getBucketName())
                .object(objectName)
                .expiry(expireHours, TimeUnit.HOURS)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(arg);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("获取授权链接失败",e);
        }
        return null;
    }
}
