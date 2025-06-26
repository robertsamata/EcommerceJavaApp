package com.example.ecommerceApp.service;

import com.example.ecommerceApp.dto.RegisterRequest;
import com.example.ecommerceApp.model.User;
import com.example.ecommerceApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
    }
}
