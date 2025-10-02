package com.example.dump4pass.controller;


import com.example.dump4pass.dto.LoginRequest;
import com.example.dump4pass.dto.LoginResponse;
import com.example.dump4pass.dto.RegisterRequest;
import com.example.dump4pass.service.UserService;
import com.example.dump4pass.service.UserService.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
         String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
