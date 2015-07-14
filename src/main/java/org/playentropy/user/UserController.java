package org.playentropy.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.BindingResult;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/users/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "users/user";
    }

    @RequestMapping(value="/users", method=RequestMethod.GET)
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users/userList";
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String getUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/userForm";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String createUser(Model model, @ModelAttribute("user") User newUser, BindingResult result) {
        model.addAttribute("user", newUser);
        if(userService.createUser(newUser, result) != null) {
            return "template";
        } else {
            model.addAttribute("errors", result);
            return "users/userForm";
        }
    }

    @RequestMapping(value="/login", method={RequestMethod.GET, RequestMethod.POST})
    public String createSessionForm(Model model) {
        model.addAttribute("user", new User());
        return "session/loginForm";
    }

    /* 
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String createSession(Model model) {
        model.addAttribute("user", new User());
        return "session/loginForm";
    } */
}

