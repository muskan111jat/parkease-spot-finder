package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaPeakPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeakPricingRepository extends JpaRepository<ParkingAreaPeakPrice, Long> {
    Optional<ParkingAreaPeakPrice> findByParkingArea(ParkingArea parkingArea);
}
