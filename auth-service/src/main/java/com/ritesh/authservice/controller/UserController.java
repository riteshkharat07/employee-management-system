package com.ritesh.authservice.controller;

import com.ritesh.authservice.entity.User;
import com.ritesh.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password) {

        return userService.loginUser(username, password);
    }
}
