package com.example.dump4pass.controller;

import com.example.dump4pass.dto.*;
import com.example.dump4pass.Repository.OtpService;
import com.example.dump4pass.service.OtpServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpServiceImpl otpService;

    public OtpController(OtpServiceImpl otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public ResponseEntity<RequestOtpResponse> requestOtp(@RequestBody RequestOtp request) {
        RequestOtpResponse resp = otpService.requestOtp(request);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyOtpResponse> verifyOtp(@RequestBody VerifyOtp request) {
        VerifyOtpResponse resp = otpService.verifyOtp(request);
        if (resp.isSuccess()) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<RequestOtpResponse> resendOtp(@RequestBody ResendOtp request) {
        RequestOtpResponse resp = otpService.resendOtp(request);
        return ResponseEntity.ok(resp);
    }
}
