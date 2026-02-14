package com.example.ParkEase20.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reset_password")
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(name = "otp")
    private String otp;
    @Column(unique = true)
    private String token;
    @Column(nullable = false)
    private Boolean used;
    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Enumerated(EnumType.STRING)
    private ResetType resetType;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public OtpEntity(){}

    public OtpEntity(Long id, String email, String otp, String token, Boolean used, LocalDateTime expiryTime, ResetType resetType) {
        this.id = id;
        this.email = email;
        this.otp = otp;
        this.token = token;
        this.used = used;
        this.expiryTime = expiryTime;
        this.resetType = resetType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public ResetType getResetType() {
        return resetType;
    }

    public void setResetType(ResetType resetType) {
        this.resetType = resetType;
    }
}
