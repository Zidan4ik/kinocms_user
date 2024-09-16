package com.example.kinocms_user.controller;

import com.example.kinocms_user.auth.UserDetailsImp;
import com.example.kinocms_user.entity.User;
import com.example.kinocms_user.mapper.UserMapper;
import com.example.kinocms_user.model.UserDTO;
import com.example.kinocms_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ModelAndView showProfile() {
        return new ModelAndView("pages/profile");
    }

    @GetMapping("/profile/data")
    @ResponseBody
    public UserDTO getProfileData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = ((UserDetailsImp) authentication.getPrincipal()).getUser();
            Optional<User> userId = userService.getById(user.getId());
            if (userId.isPresent()) {
                return UserMapper.toDTO(userId.get());
            }
        }
        return null;
    }

    @PostMapping("/add")
    public ModelAndView addUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                          Errors errors,
                          RedirectAttributes redirectAttributes) {
        ModelAndView model = new ModelAndView();
        if (errors.hasErrors()) {
            model.setViewName("auth/register");
            return model;
        } else if (userService.getByEmail(userDTO.getEmail()).isPresent()) {
            model.addObject("existEmail", "The user exists with similar email!");
            model.setViewName("auth/register");
            return model;
        }
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        user.setDateOfRegistration(LocalDate.now());
        userService.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "User was successfully created. Please log in.");
        model.setViewName("redirect:/login");
        return model;
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<Object> editUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getPassword() == null) {
            Optional<User> userServiceById = userService.getById(userDTO.getId());
            userServiceById.ifPresent(u -> userDTO.setPassword(u.getPassword()));
        } else {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userService.save(UserMapper.toEntity(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
