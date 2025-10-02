package com.example.dump4pass.util;

import com.example.dump4pass.Repository.OtpSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingOtpSender implements OtpSender {
    private static final Logger log = LoggerFactory.getLogger(LoggingOtpSender.class);

    @Override
    public void send(String channel, String contact, String message) {
        // replace with Twilio / SendGrid / AWS SNS etc.
        log.info("SEND OTP -> channel: {}, contact: {}, message: {}", channel, contact, message);
    }
}
