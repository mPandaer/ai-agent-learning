package com.learning.agent.mcp.image.dto.minio;


import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Builder
@Data
public class UploadRequest {

    private String objectName;

    private InputStream inputStream;

    private String mediaType;
}
