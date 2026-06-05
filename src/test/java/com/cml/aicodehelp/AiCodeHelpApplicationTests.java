package com.cml.aicodehelp;

import com.cml.aicodehelp.ai.AiCodeHelp;
import com.cml.aicodehelp.ai.AiCodeHelpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AiCodeHelpApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private AiCodeHelp aiCodeHelp;

    @Test
    void test() {
        aiCodeHelp.chat("你好，我是程序员cml");
    }

    @Test
    void chatSystemMessage() {
        aiCodeHelp.chat("你好，我是程序员cml");
    }

    @Autowired
    private AiCodeHelpService aiCodeHelpService;

    @Test
    void testAiCedeHelpService() {
        String result = aiCodeHelpService.chat("你好，我是程序员cml");
        log.info("结果：{}", result);
        result = aiCodeHelpService.chat("你好，我是谁？");
        log.info("结果：{}", result);
    }
}
