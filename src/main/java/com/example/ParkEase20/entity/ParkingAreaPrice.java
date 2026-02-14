package com.example.ParkEase20.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pricing")
public class ParkingAreaPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(optional = false)
    @JoinColumn(name = "parking_area_id", nullable = false, unique = true)
    private ParkingArea parkingArea;

    // Base prices

    private Double bikePricePerHour;


    private Double carPricePerHour;


    private Double suvPricePerHour;


    private Double truckPricePerHour;

    // Optional daily prices
    private Double bikePricePerDay;
    private Double carPricePerDay;
    private Double suvPricePerDay;
    private Double truckPricePerDay;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
