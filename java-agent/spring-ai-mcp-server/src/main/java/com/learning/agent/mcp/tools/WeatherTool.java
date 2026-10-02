package com.learning.agent.mcp.tools;


import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WeatherTool {

    @McpTool(name = "currentWeather",description = "获取当前天气状况")
    public String getCurrentWeather(@McpToolParam(description = "当前时间，格式为：yyyy-MM-dd HH:mm:ss") String dateTime) {
        LocalDateTime weatherDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return String.format("当前{%s}天气晴，可以出去玩耍",DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒").format(weatherDateTime));

    }

}
