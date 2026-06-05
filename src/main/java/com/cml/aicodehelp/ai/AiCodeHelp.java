package com.cml.aicodehelp.ai;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiCodeHelp {

    private static final String SYSTEM_MESSAGE = """
            你是编程领域的小助手，帮助用户解答学习和求职面试相关问题，并给出建议。重点关注4个方向：
            1.规划学习路线
            2.提供项目学习建议
            3.提供求职流程指南
            4.分享面试技巧
            """;

    @Autowired
    private ChatModel qwChatModel;

    /**
     * 简单文本聊天
     * @param message
     * @return
     */
    public String chat(String message) {
        UserMessage userMessage = UserMessage.from(message);
        // 调用模型
        ChatResponse chatResponse = qwChatModel.chat(userMessage);
        // 获取模型返回结果
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("模型返回结果：{}", aiMessage.text());

        return aiMessage.text();
    }

    /**
     * 简单自定义聊天-多模态
     * @param userMessage
     * @return
     */
    public String chatWithMessage(UserMessage userMessage) {
        // 调用模型
        ChatResponse chatResponse = qwChatModel.chat(userMessage);
        // 获取模型返回结果
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("模型返回结果：{}", aiMessage.text());

        return aiMessage.text();
    }

    /**
     * 简单文本聊天-系统提示词
     * @param message
     * @return
     */
    public String chatSystemMessage(String message) {
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
        UserMessage userMessage = UserMessage.from(message);
        // 调用模型
        ChatResponse chatResponse = qwChatModel.chat(systemMessage, userMessage);
        // 获取模型返回结果
        AiMessage aiMessage = chatResponse.aiMessage();
        log.info("模型返回结果：{}", aiMessage.text());

        return aiMessage.text();
    }
}
