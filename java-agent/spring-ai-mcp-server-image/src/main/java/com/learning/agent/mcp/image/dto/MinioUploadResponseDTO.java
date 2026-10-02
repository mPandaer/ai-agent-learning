package com.learning.agent.mcp.image.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MinioUploadResponseDTO {
    private String objectName;

    private String url;

    private Integer urlExpireHours;
}
