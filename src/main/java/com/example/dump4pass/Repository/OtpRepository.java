package com.example.dump4pass.Repository;

import com.example.dump4pass.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    // find most recent unverified OTP for a user
    Optional<Otp> findTopByUserIdAndVerifiedFalseOrderByCreatedAtDesc(String userId);
}
