package com.learning.agent.mcp.image.dto.minio;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UploadResponse {
    private String objectName;

    private String url;

    private Integer urlExpireHours;
}
