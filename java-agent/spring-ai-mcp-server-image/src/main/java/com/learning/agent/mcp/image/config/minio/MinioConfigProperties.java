package com.learning.agent.mcp.image.config.minio;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio.config")
@Data
public class MinioConfigProperties {

    private String url;

    private String username;

    private String password;

    private String bucketName;
}
