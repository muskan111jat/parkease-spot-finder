package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.*;
import com.example.ParkEase20.exceptions.InvalidCredentialsException;
import com.example.ParkEase20.service.AuthService;
import com.example.ParkEase20.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final OtpService otpService;

    public AuthController(AuthService authService, OtpService otpService) {
        this.authService = authService;
        this.otpService = otpService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return ResponseEntity.ok().body("success");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            return ResponseEntity.ok().body(authService.login(loginRequest));
        }catch (Exception e){
            throw new InvalidCredentialsException("invalid username or password");
        }


    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        otpService.generateAndSendOtp(request.getEmail());
        return ResponseEntity.ok().body("success");
    }
    @PostMapping("/otp-verify")
    public ResponseEntity<?> otpVerification(@Valid @RequestBody OtpAndEmailRequest request) {
        otpService.verifyOtp(request.getEmail(), request.getOtpCode());
        return ResponseEntity.ok().body("success");
    }
    @PostMapping("/reset-password/otp")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
    authService.resetPasswordViaOtp(request.getEmail(), request.getNewPassword());
    return ResponseEntity.ok().body("successfully changed password");
    }

}
