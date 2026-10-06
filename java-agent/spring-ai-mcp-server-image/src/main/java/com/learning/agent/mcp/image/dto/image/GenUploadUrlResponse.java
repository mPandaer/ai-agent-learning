package com.learning.agent.mcp.image.dto.image;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GenUploadUrlResponse {
    private String uploadImageUrl;

    private String expiresAt;
}
