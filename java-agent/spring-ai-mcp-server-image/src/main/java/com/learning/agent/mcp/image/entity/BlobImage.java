package com.learning.agent.mcp.image.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HexFormat;

@AllArgsConstructor
@Data
public class BlobImage {

    private String imageName;

    private byte[] blob;

    public InputStream getInputStream() {
        return new ByteArrayInputStream(blob);
    }

    public static BlobImage decodeB64(String base64Image) {
        byte[] blob = Base64.getDecoder().decode(base64Image);
        String name = hashName(blob);
        return new BlobImage(name, blob);
    }

    @SneakyThrows
    private static String hashName(byte[] blob) {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(blob);
        return HexFormat.of().formatHex(digest) + ".png";
    }
}
