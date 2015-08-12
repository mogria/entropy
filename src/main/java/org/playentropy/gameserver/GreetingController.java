package org.playentropy.gameserver;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/name")
    public String handle() {
        String message = "adasd";
        return "[hello, " + message + "]";
    }
}
