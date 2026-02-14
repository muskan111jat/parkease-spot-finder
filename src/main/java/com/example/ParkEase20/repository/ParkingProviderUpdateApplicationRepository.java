package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import com.example.ParkEase20.entity.ParkingProviderProfile;
import com.example.ParkEase20.entity.ParkingProviderUpdateApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingProviderUpdateApplicationRepository extends JpaRepository<ParkingProviderUpdateApplication, Long> {
    //Optional<ParkingProviderUpdateApplication>findByProviderProfileAndIsLatestTrue(ParkingProviderProfile parkingProviderProfile);
    boolean existsByProviderProfileAndStatus(ParkingProviderProfile providerProfile, ParkingProviderApplicationStatus status);
    Optional<ParkingProviderUpdateApplication> findTopByProviderProfileOrderByCreatedAtDesc(ParkingProviderProfile providerProfile);


}
