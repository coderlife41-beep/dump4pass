package com.example.dump4pass.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "otps", indexes = {
    @Index(name = "idx_user_unverified_created", columnList = "userId, verified, createdAt")
})
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID for the user or target (could be mobile/email/username)
    @Column(nullable = false)
    private String userId;

    // hashed otp
    @Column(nullable = false)
    private String otpHash;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    // whether OTP was successfully verified
    @Column(nullable = false)
    private boolean verified = false;

    // number of verification attempts
    @Column(nullable = false)
    private int attempts = 0;

    // number of resends
    @Column(nullable = false)
    private int resendCount = 0;

    // optional channel (SMS / EMAIL)
    private String channel;

    public Otp() {}

    // getters and setters omitted for brevity (generate with Lombok or your IDE)
    // ... include standard getters/setters
    // (If you use Lombok, annotate with @Data or @Getter/@Setter)
    // For clarity, include all getters/setters in your implementation
}
