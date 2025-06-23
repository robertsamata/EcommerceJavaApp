package com.example.ecommerceApp.controller;

import com.example.ecommerceApp.model.User;
import com.example.ecommerceApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // Login successful - poți returna un mesaj sau date relevante
        return ResponseEntity.ok("Login successful for user: " + username);
    }
}