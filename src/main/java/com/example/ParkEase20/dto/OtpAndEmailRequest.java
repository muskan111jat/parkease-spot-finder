package com.example.ParkEase20.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class OtpAndEmailRequest {
    @NotBlank(message="otp is required for reset password")
    private String otpCode;
    @NotBlank(message="email is required")
    @Email
    private String email;

    public OtpAndEmailRequest(String otpCode, String email) {
        this.otpCode = otpCode;
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
