package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingRepository extends JpaRepository<ParkingAreaPrice, Long> {
    Optional<ParkingAreaPrice> findByParkingArea(ParkingArea parkingArea);
}
