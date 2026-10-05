package com.learning.agent.mcp.image.config.image;

import com.learning.agent.mcp.image.client.ImageClient;
import com.learning.agent.mcp.image.service.MinioService;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@EnableConfigurationProperties(OpenAiImageConfigProperties.class)
@Configuration
public class OpenAiImageConfig {

    @Autowired
    private OpenAiImageConfigProperties properties;



    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public List<ImageClient> openAIClients(MinioService minioService,RestClient restClient) {
        List<OpenAiImageConfigProperties.ImageProvider> providers = properties.getProviders();
        if (providers == null || providers.isEmpty()) {
            return null;
        }
        return providers.stream().map(item -> initImageClient(item,minioService,restClient)).collect(Collectors.toList());
    }

    private ImageClient initImageClient(OpenAiImageConfigProperties.ImageProvider item,MinioService minioService,RestClient restClient) {
        OpenAIClient openAiClient = OpenAIOkHttpClient.builder()
                .baseUrl(item.getBaseUrl())
                .apiKey(item.getApiKey())
                .build();

        return ImageClient.builder()
                .client(openAiClient)
                .restClient(restClient)
                .minioService(minioService)
                .model(item.getModel())
                .name(item.getName())
                .order(item.getOrder())
                .build();
    }

}
