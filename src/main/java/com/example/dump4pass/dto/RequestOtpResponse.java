package com.example.dump4pass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOtpResponse {
    private String message;
// optionally return a masked contact, expiry time, or transaction id
}