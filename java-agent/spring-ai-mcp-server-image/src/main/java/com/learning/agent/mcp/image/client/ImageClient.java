package com.learning.agent.mcp.image.client;

import com.learning.agent.mcp.image.dto.image.*;
import com.learning.agent.mcp.image.dto.minio.UploadRequest;
import com.learning.agent.mcp.image.dto.minio.UploadResponse;
import com.learning.agent.mcp.image.entity.BlobImage;
import com.learning.agent.mcp.image.service.MinioService;
import com.openai.client.OpenAIClient;
import com.openai.core.MultipartField;
import com.openai.models.audio.translations.TranslationCreateParams;
import com.openai.models.images.Image;
import com.openai.models.images.ImageEditParams;
import com.openai.models.images.ImageGenerateParams;
import com.openai.models.images.ImagesResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
@Builder
public class ImageClient {

    private String name;

    private OpenAIClient client;

    private MinioService minioService;

    private RestClient restClient;

    private Integer order;

    private String model;

    private static final String MEDIA_TYPE = "image/png";

    public Text2ImageResponse text2Image(Text2ImageRequest request) {
        String prompt = request.getPrompt();
        ImageGenerateParams params = ImageGenerateParams.builder()
                .prompt(prompt)
                .model(model)
                .responseFormat(ImageGenerateParams.ResponseFormat.B64_JSON)
                .build();

        ImagesResponse generate = client.images().generate(params);
        List<Image> images = generate.data().orElse(null);
        if (images == null || images.isEmpty()) {
            return Text2ImageResponse.fail("模型提供商没有返回图片");
        }

        Image image = images.get(0);
        String b64Image = image.b64Json().orElse(null);
        if (b64Image == null || b64Image.isEmpty()) {
            return Text2ImageResponse.fail("base64数据不存在");
        }


        BlobImage blobImage = BlobImage.decodeB64(b64Image);
        UploadRequest uploadRequest = UploadRequest.builder()
                .mediaType(MEDIA_TYPE)
                .objectName(blobImage.getImageName())
                .inputStream(blobImage.getInputStream())
                .build();
        UploadResponse upload = minioService.upload(uploadRequest);

        if (upload == null) {
            return Text2ImageResponse.fail("生成可访问的URL失败");
        }
        return Text2ImageResponse.success(upload.getUrl(), upload.getUrlExpireHours(), TimeUnit.HOURS);
    }


    public Image2ImageResponse image2Image(Image2ImageRequest request) {
        String imageUrl = request.getImageUrl();
        String prompt = request.getPrompt();


        try(InputStream inputStream = fetchImageUrl(imageUrl)) {
            MultipartField<ImageEditParams.Image> paramsImage = MultipartField.<ImageEditParams.Image>builder()
                    .value(ImageEditParams.Image.ofInputStream(inputStream))
                    .contentType("image/png")
                    .filename("input.png")
                    .build();

            ImageEditParams params = ImageEditParams.builder()
                    .image(paramsImage)
                    .prompt(prompt)
                    .model(model)
                    .responseFormat(ImageEditParams.ResponseFormat.B64_JSON)
                    .build();
            ImagesResponse edit = client.images().edit(params);


            List<Image> images = edit.data().orElse(null);
            if (images == null || images.isEmpty()) {
                return Image2ImageResponse.fail("模型提供商没有返回图片");
            }

            Image image = images.get(0);
            String b64Image = image.b64Json().orElse(null);
            if (b64Image == null || b64Image.isEmpty()) {
                return Image2ImageResponse.fail("base64数据不存在");
            }


            BlobImage blobImage = BlobImage.decodeB64(b64Image);
            UploadRequest uploadRequest = UploadRequest.builder()
                    .mediaType(MEDIA_TYPE)
                    .objectName(blobImage.getImageName())
                    .inputStream(blobImage.getInputStream())
                    .build();
            UploadResponse upload = minioService.upload(uploadRequest);

            if (upload == null) {
                return Image2ImageResponse.fail("生成可访问的URL失败");
            }
            return Image2ImageResponse.success(upload.getUrl(),upload.getUrlExpireHours(), TimeUnit.HOURS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private InputStream fetchImageUrl(String imageUrl) {
        URI uri = URI.create(imageUrl);
        RestClient.RequestHeadersSpec<?> request = restClient.get().uri(uri);
        return request.retrieve().body(InputStream.class);
    }


}
