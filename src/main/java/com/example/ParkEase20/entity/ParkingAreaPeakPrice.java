package com.example.ParkEase20.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="peak-pricing")
public class ParkingAreaPeakPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "parking_area_id", nullable = false)
    private ParkingArea parkingArea;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    // choose ONE strategy (you can keep both nullable)
    private Double multiplier;   // e.g. 1.5
    private Double extraAmount;  // e.g. +10rs

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
