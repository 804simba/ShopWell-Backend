package com.shopwell.api.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
