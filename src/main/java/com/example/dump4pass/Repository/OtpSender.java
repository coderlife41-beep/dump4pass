package com.example.dump4pass.Repository;

public interface OtpSender {
    void send(String channel, String contact, String message);
}

