package com.example.ParkEase20.service;

import com.example.ParkEase20.entity.OtpEntity;
import com.example.ParkEase20.entity.ResetType;
import com.example.ParkEase20.exceptions.NotFoundException;
import com.example.ParkEase20.repository.OtpEntityRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {
    private final OtpEntityRepository otpEntityRepository;
    private final MailService mailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public OtpService(OtpEntityRepository otpEntityRepository, MailService mailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.otpEntityRepository = otpEntityRepository;
        this.mailService = mailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    private String generateOtp(){
        return String.valueOf(100000+new SecureRandom().nextLong(900000));
    }
    public void generateAndSendOtp(String email){
        OtpEntity otpEntity = new OtpEntity();
        String otp=generateOtp();
        otpEntity.setOtp(bCryptPasswordEncoder.encode(otp));
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(15));
        otpEntity.setEmail(email);
        otpEntity.setResetType(ResetType.OTP);
        otpEntity.setUsed(false);
        otpEntityRepository.save(otpEntity);
        mailService.sendOtp(email,otp);
    }
    public boolean verifyOtp(String email, String otp){
        OtpEntity otpEntity = otpEntityRepository.findTopByEmailOrderByExpiryTimeDesc(email).orElseThrow(
                ()-> new NotFoundException("Otp Not Found")
        );
        if(otpEntity.getUsed())return false;
        if(otpEntity.getResetType()!=ResetType.OTP)return false;
        if(otpEntity.getExpiryTime().isBefore(LocalDateTime.now()))return false;
        if(!bCryptPasswordEncoder.matches(otp,otpEntity.getOtp()))return false;
        otpEntity.setUsed(true);
        otpEntityRepository.save(otpEntity);
        return true;

    }
    public boolean isOtpVerified(String email){// when provider email and new pasword again we need to check otp has expired
        return otpEntityRepository.findTopByEmailOrderByExpiryTimeDesc(email)// 2nd verfication no changes here
                .filter(otp->
                        otp.getUsed()&&
                        otp.getExpiryTime().isAfter(LocalDateTime.now())&&
                        otp.getResetType()==ResetType.OTP)
                .isPresent();

    }
}
