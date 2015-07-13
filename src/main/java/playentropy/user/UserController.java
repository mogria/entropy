package org.playentropy.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(value="/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "users/user";
    }

    @RequestMapping(value="/new", method=RequestMethod.GET)
    public String getUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/userForm";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String createUser(@ModelAttribute User newUser, Model model) {
        model.addAttribute("user", newUser);
        if(userService.createUser(newUser) != null) {
            return "template";
        } else {
            return "users/userForm";
        }
    }

    /* 
    @RequestMapping(value="/{user}", method=RequestMethod.POST)
    public User updateUser() {
    }

    @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long user) {
    }*/
}
