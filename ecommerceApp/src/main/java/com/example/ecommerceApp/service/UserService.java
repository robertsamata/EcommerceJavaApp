package com.example.ecommerceApp.service;

import java.util.List;
import java.util.Optional;
import com.example.ecommerceApp.model.User;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void deleteUser(Long id);
    User registerUser(String username, String password, String role);
    User login(String username, String password);
    User findByUsername(String username);
}
