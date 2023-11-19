package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${spring.mail.username}")
    private String senderEmail;
    public String getSenderEmail(){
        return senderEmail;
    }
}
