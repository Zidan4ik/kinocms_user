package com.example.kinocms_user.controller;

import com.example.kinocms_user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthorityController {
    @GetMapping("/user/login")
    public ModelAndView showLogin(ModelAndView model, @ModelAttribute(value = "successMessage") String successMessage) {
        if (!successMessage.isEmpty()) {
            model.addObject("message", successMessage);
        }
        model.setViewName("auth/login");
        return model;
    }

    @GetMapping("/user/register")
    public ModelAndView showRegister(@ModelAttribute UserDTO userDTO) {
        ModelAndView model = new ModelAndView("auth/register");
        model.addObject("user", userDTO);
        return model;
    }
}
