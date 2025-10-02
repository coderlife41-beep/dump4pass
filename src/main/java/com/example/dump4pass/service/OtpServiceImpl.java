package com.example.dump4pass.service;

import com.example.dump4pass.Repository.OtpService;
import com.example.dump4pass.dto.*;
import com.example.dump4pass.Repository.OtpRepository;
import com.example.dump4pass.Repository.OtpSender;
import com.example.dump4pass.entity.Otp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final OtpSender otpSender;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // configuration constants - tweak as you need or move to properties
    private static final int OTP_LENGTH = 6;
    private static final Duration OTP_TTL = Duration.ofMinutes(5);
    private static final int MAX_ATTEMPTS = 5;
    private static final int MAX_RESENDS = 3;

    public OtpServiceImpl(OtpRepository otpRepository, OtpSender otpSender) {
        this.otpRepository = otpRepository;
        this.otpSender = otpSender;
    }

    @Override
    @Transactional
    public RequestOtpResponse requestOtp(RequestOtp request) {
        String otpPlain = generateNumericOtp(OTP_LENGTH);
        String hashed = passwordEncoder.encode(otpPlain);

        Instant now = Instant.now();
        Otp otp = new Otp();
        otp.setUserId(request.getUserId());
        otp.setOtpHash(hashed);
        otp.setCreatedAt(now);
        otp.setExpiresAt(now.plus(OTP_TTL));
        otp.setVerified(false);
        otp.setAttempts(0);
        otp.setResendCount(0);
        otp.setChannel(request.getChannel());

        otpRepository.save(otp);

        // send (replace contact mapping as required)
        String contact = request.getContact() != null ? request.getContact() : request.getUserId();
        String message = String.format("Your OTP is %s. It will expire in %d minutes.", otpPlain, OTP_TTL.toMinutes());
        otpSender.send(request.getChannel(), contact, message);

        return new RequestOtpResponse("OTP sent");
    }

    @Override
    @Transactional
    public VerifyOtpResponse verifyOtp(VerifyOtp request) {
        Optional<Otp> maybe = otpRepository.findTopByUserIdAndVerifiedFalseOrderByCreatedAtDesc(request.getUserId());
        if (maybe.isEmpty()) {
            return new VerifyOtpResponse(false, "No OTP request found");
        }
        Otp otp = maybe.get();

        if (Instant.now().isAfter(otp.getExpiresAt())) {
            return new VerifyOtpResponse(false, "OTP expired");
        }

        if (otp.getAttempts() >= MAX_ATTEMPTS) {
            return new VerifyOtpResponse(false, "Max verification attempts exceeded");
        }

        otp.setAttempts(otp.getAttempts() + 1);
        otpRepository.save(otp);

        boolean matches = passwordEncoder.matches(request.getOtp(), otp.getOtpHash());
        if (!matches) {
            return new VerifyOtpResponse(false, "Invalid OTP");
        }

        otp.setVerified(true);
        otpRepository.save(otp);
        return new VerifyOtpResponse(true, "OTP verified");
    }

    @Override
    @Transactional
    public RequestOtpResponse resendOtp(ResendOtp request) {
        Optional<Otp> maybe = otpRepository.findTopByUserIdAndVerifiedFalseOrderByCreatedAtDesc(request.getUserId());
        if (maybe.isEmpty()) {
            // no prior OTP - behave like request
            RequestOtp r = new RequestOtp();
            r.setUserId(request.getUserId());
            r.setChannel(request.getChannel());
            r.setContact(request.getContact());
            return requestOtp(r);
        }

        Otp otp = maybe.get();
        if (otp.getResendCount() >= MAX_RESENDS) {
            return new RequestOtpResponse("Resend limit reached");
        }

        // generate a new OTP or reuse existing? Here generate new to be safer
        String newPlain = generateNumericOtp(OTP_LENGTH);
        otp.setOtpHash(passwordEncoder.encode(newPlain));
        otp.setCreatedAt(Instant.now());
        otp.setExpiresAt(Instant.now().plus(OTP_TTL));
        otp.setAttempts(0);
        otp.setResendCount(otp.getResendCount() + 1);
        otpRepository.save(otp);

        String contact = request.getContact() != null ? request.getContact() : request.getUserId();
        String message = String.format("Your OTP is %s. It will expire in %d minutes.", newPlain, OTP_TTL.toMinutes());
        otpSender.send(request.getChannel(), contact, message);

        return new RequestOtpResponse("OTP resent");
    }

    private String generateNumericOtp(int len) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

//    @Override
//    public RequestOtpResponse requestOtp(RequestOtp request) {
//        return null;
//    }
//
//    @Override
//    public VerifyOtpResponse verifyOtp(VerifyOtp request) {
//        return null;
//    }
//
//    @Override
//    public RequestOtpResponse resendOtp(ResendOtp request) {
//        return null;
//    }
}
