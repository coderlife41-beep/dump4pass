package com.example.dump4pass.dto;

import lombok.Data;

@Data
public class RequestOtp {
    private String userId;
    private String channel; // "SMS" or "EMAIL"
    private String contact; // phone number or email - optional if you map userId to contact on your side

    // getters/setters
}

