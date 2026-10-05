package com.learning.agent.mcp.image.config.image;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "openai.images")
@Data
public class OpenAiImageConfigProperties {

    List<ImageProvider> providers;



    @Data
    public static class ImageProvider {
        public String name;

        public String baseUrl;

        private String apiKey;

        private Integer order;

        private String model;
    }

}
