package com.example.dump4pass.service;


import com.example.dump4pass.Repository.UserRepository;
import com.example.dump4pass.dto.LoginRequest;
import com.example.dump4pass.dto.LoginResponse;
import com.example.dump4pass.dto.RegisterRequest;
import com.example.dump4pass.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already taken!";
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered!";
        }

        // ⚠️ In real projects, encrypt password with BCrypt
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        userRepository.save(user);
        return "User registered successfully!";
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(
                request.getUsernameOrEmail(),
                request.getUsernameOrEmail()
        );

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(request.getPassword())) { // ⚠️ use BCrypt in real app
                return new LoginResponse("Login successful", user.getUsername(), user.getEmail());
            } else {
                return new LoginResponse("Invalid credentials", null, null);
            }
        }
        return new LoginResponse("User not found", null, null);
    }
}

