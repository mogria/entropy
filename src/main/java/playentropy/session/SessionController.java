package org.playentropy.session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.playentropy.user.User;

@Controller
public class SessionController {
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String createSessionForm(Model model) {
        model.addAttribute("user", new User());
        return "session/loginForm";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String createSession(Model model) {
        model.addAttribute("user", new User());
        return "session/loginForm";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String destroySession(Model model) {
        return "session/loginForm";
    }
}
