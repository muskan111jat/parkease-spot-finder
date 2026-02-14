package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpEntityRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findTopByEmailOrderByExpiryTimeDesc(String email);
    Optional<OtpEntity> findByTokenAndUsedFalse(String token);
}
