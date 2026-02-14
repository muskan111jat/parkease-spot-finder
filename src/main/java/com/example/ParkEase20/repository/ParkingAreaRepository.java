package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingProviderApplication;
import com.example.ParkEase20.entity.ParkingProviderProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ParkingAreaRepository extends JpaRepository<ParkingArea, Long> {
    List<ParkingArea> findByProviderProfile(ParkingProviderProfile parkingProviderProfile);
    boolean existsByProviderProfile(ParkingProviderProfile parkingProviderProfile);
    Optional<ParkingArea> findByIdAndProviderProfile(Long id,ParkingProviderProfile parkingProviderProfile);
    int countByIsActive(boolean active);
}
