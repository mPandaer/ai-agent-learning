package com.learning.agent.mcp.image.client;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
public class ImageClientManager {

    @Autowired
    private List<ImageClient> imageClients;

    public ImageClient getImageClient() {
        ImageClient imageClient = imageClients.get(0);
        log.info("Image client found: {}", imageClient.getName());
        return imageClient;
    }

    @PostConstruct
    public void init() {
        imageClients.sort(Comparator.comparingInt(ImageClient::getOrder));
    }

}
