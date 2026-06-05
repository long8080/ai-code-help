package com.cml.aicodehelp.controller;

import com.cml.aicodehelp.ai.AiCodeHelpService;
import com.cml.aicodehelp.controller.dto.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiCodeHelpController {

    @Autowired
    private AiCodeHelpService aiCodeHelpService;

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        return aiCodeHelpService.chat(request.message());
    }
}
