package com.learning.agent.springai.model.chatmodel.promptstemplate.config;


import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class RoleActPromptTemplateConfig {

    @Bean
    public PromptTemplate roleActPromptTemplate(@Value("classpath:/prompts/role-act.md")
                                                          Resource roleActPromptMd) {
        return PromptTemplate.builder().resource(roleActPromptMd).build();
    }
}
