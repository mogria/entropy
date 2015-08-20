package org.playentropy.chat;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class ChatController {

    static Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("chat/messsage")
    public void onMessage(ChatMessage message, SimpMessageHeaderAccessor header) {
        log.warn("message: " + header.getSessionId() + " " + message.getMessage());

        template.convertAndSend("/chat", message);
    }
}
