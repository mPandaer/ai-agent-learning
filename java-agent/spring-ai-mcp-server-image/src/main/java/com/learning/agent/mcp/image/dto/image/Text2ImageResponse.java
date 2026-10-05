package com.learning.agent.mcp.image.dto.image;

import com.learning.agent.mcp.image.client.ImageClient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Builder
@Data
public class Text2ImageResponse {

    private String imageUrl;

    private String expiresAt;

    private String message;

    public static Text2ImageResponse fail() {
        return fail("文生图失败");
    }

    public static Text2ImageResponse fail(String message) {
        return Text2ImageResponse.builder().message(message).build();
    }

    public static Text2ImageResponse success(String imageUrl,String expiresAt) {
        return Text2ImageResponse.builder().message("success").imageUrl(imageUrl).expiresAt(expiresAt).build();
    }

    public static Text2ImageResponse success(String imageUrl, Integer expires, TimeUnit timeUnit) {
        String expiresAt = LocalDateTime.now().plus(expires, timeUnit.toChronoUnit()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Text2ImageResponse.builder().message("success").imageUrl(imageUrl).expiresAt(expiresAt).build();
    }
}
