package com.learning.agent.custom.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.util.StringUtils;

public class SearchTool {

    @Tool
    public String search(String key) {
        System.out.println("调用工具 关键字为：" + key);
        if (StringUtils.hasText(key) && key.contains("成都")) {
            return "成都的天气非常的不错，28度，适合出去游玩";
        }
        return "当前关键字: "+key+"不支持搜索";
    }
}
