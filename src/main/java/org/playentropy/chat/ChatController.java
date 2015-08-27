package org.playentropy.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;

@Controller
public class ChatController {

    static Logger log = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/chat/message")
    @SendTo("/app/chat")
    public TextMessage onMessage(TextMessage message, SimpMessageHeaderAccessor header) {
        log.warn("message: " + header.getSessionId() + " " + message.toString());
        return message;
    }
}
