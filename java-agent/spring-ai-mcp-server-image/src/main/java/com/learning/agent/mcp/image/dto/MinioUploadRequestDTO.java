package com.learning.agent.mcp.image.dto;


import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Builder
@Data
public class MinioUploadRequestDTO {

    private String objectName;

    private InputStream inputStream;

    private String mediaType;
}
