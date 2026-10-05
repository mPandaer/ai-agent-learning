package com.learning.agent.mcp.image.dto.image;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Builder
@Data
public class Image2ImageResponse {

    private String imageUrl;

    private String expiresAt;

    private String message;

    public static Image2ImageResponse fail() {
        return fail("图生图失败");
    }

    public static Image2ImageResponse fail(String message) {
        return Image2ImageResponse.builder().message(message).build();
    }

    public static Image2ImageResponse success(String imageUrl, String expiresAt) {
        return Image2ImageResponse.builder().message("success").imageUrl(imageUrl).expiresAt(expiresAt).build();
    }

    public static Image2ImageResponse success(String imageUrl, Integer expires, TimeUnit timeUnit) {
        String expiresAt = LocalDateTime.now().plus(expires, timeUnit.toChronoUnit()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Image2ImageResponse.builder().message("success").imageUrl(imageUrl).expiresAt(expiresAt).build();
    }
}
