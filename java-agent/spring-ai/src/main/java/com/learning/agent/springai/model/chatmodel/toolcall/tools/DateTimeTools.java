package com.learning.agent.springai.model.chatmodel.toolcall.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class DateTimeTools {

    @Tool(description = "获取当前日期")
    public String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        return DateTimeFormatter.ofPattern("yyyy年MM月dd日-HH时mm分ss秒").format(now);
    }
}
