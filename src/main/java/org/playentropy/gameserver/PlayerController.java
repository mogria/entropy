package org.playentropy.gameserver;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Controller
public class PlayerController {

    static Logger log = LoggerFactory.getLogger(PlayerController.class);

    @MessageMapping("/move")
    public String handle(MoveMessage message) {
        log.warn("player moved " + message.getX() + ", " + message.getY());
        return "dafuq";
    }
}
