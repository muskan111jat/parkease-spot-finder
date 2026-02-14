package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.ParkingProviderProfile;
import com.example.ParkEase20.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingProviderProfileRepository extends JpaRepository<ParkingProviderProfile, Long> {
    Optional<ParkingProviderProfile> findByUser(User user);
}
