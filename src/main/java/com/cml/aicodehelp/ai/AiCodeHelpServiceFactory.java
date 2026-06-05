package com.cml.aicodehelp.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiCodeHelpServiceFactory {

    @Autowired
    private ChatModel qwChatModel;

    @Bean
    public AiCodeHelpService createAiCodeHelpService() {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        return AiServices.builder(AiCodeHelpService.class)
                .chatModel(qwChatModel)// 模型
                .chatMemory(chatMemory)// 会话记忆
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))// 会话隔离
                .build();
    }
}
