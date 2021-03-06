package org.playentropy.sites;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SitesController {
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String welcomePage() {
        return "welcome";
    }

    @RequestMapping(value="/play", method=RequestMethod.GET)
    public String playPage() {
        return "play";
    }
}
