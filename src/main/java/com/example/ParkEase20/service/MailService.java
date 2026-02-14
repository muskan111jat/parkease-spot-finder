package com.example.ParkEase20.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendEmail(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Park Ease20 Mail Service");
            message.setText("Hello World");
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void sendOtp(String email, String otp) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your Park Ease otp code");
            message.setText(
                    "Your OTP is: " + otp +
                            "\nThis OTP is valid for 5 minutes." +
                            "\n\nDo not share this OTP with anyone."
            );
            mailSender.send(message);

    }
}
