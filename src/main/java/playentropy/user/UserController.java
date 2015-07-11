package org.playentropy.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.Model;

@RestController
@RequestMapping(value="/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable String id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/user";
    }

    /* 
    @RequestMapping(value="/", method=RequestMethod.POST)
    public User createUser() {
    }

    @RequestMapping(value="/{user}", method=RequestMethod.POST)
    public User updateUser() {
    }

    @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long user) {
    }*/
}
