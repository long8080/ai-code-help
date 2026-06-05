package com.cml.aicodehelp.ai;

import dev.langchain4j.service.SystemMessage;

public interface AiCodeHelpService {

    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String message);
}
