package com.example.dump4pass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor   // generates constructor with all fields
@NoArgsConstructor    // generates no-args constructor (needed by Jackson for JSON)
public class VerifyOtpResponse {
    private boolean success;
    private String message;
}