package com.learning.agent.mcp.image.utils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class SignUtils {


    private final static long MAX_DIFF = 10 * 60;

    public static String getTimeSign() {
        long second = Instant.now().getEpochSecond();
        byte[] encode = Base64.getEncoder().encode(Long.toString(second).getBytes());
        return new String(encode, StandardCharsets.US_ASCII);
    }

    public static boolean validateTimeSign(String timeSign) {
        byte[] decode = Base64.getDecoder().decode(timeSign);
        long signImageTimeStamp = Long.parseLong(new String(decode, StandardCharsets.US_ASCII));
        Instant now = Instant.now();
        long diff = now.getEpochSecond() - signImageTimeStamp;
        return diff >=0 && diff <= MAX_DIFF;
    }
}
