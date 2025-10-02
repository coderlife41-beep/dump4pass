package com.example.dump4pass.Repository;

import com.example.dump4pass.dto.*;

public interface OtpService {
    RequestOtpResponse requestOtp(RequestOtp request);
    VerifyOtpResponse verifyOtp(VerifyOtp request);
    RequestOtpResponse resendOtp(ResendOtp request);
}
