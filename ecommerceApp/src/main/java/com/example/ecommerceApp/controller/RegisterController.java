package com.example.ecommerceApp.controller;

import com.example.ecommerceApp.dto.RegisterRequest;
import com.example.ecommerceApp.model.User;
import com.example.ecommerceApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest registerRequest, Model model) {
        try {
            userService.registerUser(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    registerRequest.getRole()
            );
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
