package com.example.kinocms_user.controller;

import com.example.kinocms_user.entity.User;
import com.example.kinocms_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/profile")
    public String showProfile() {
        return "pages/profile";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "User was successful saved!";
    }
}
