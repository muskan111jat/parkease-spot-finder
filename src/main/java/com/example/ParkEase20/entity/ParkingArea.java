package com.example.ParkEase20.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parking-area")
public class ParkingArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ownership (shared business identity)
    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_profile_id", nullable = false)
    private ParkingProviderProfile providerProfile;

    // Basic info
    @Column(nullable = false)
    private String parkingAreaName;

    @Column(nullable = false)
    private String parkingAreaAddress;

    private String parkingAreaDescription;

    private Double latitude;
    private Double longitude;


    // Type & availability
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingAreaType type; // FIXED / EVENT

    // FIXED parking fields
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean overnightAllowed;

    // EVENT parking fields
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventEndDateTime;

    // Area-based capacity
    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double height;

    private Double totalArea; // can be derived: width * height

    // Status
    private Boolean isActive;
    //image
    private String parkingAreaImageUrl;
    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    public static ParkingSpot fromParkingProviderApplication(ParkingProviderApplication parkingProviderApplication) {
//
//    }
//    public static void updateParkingSpot(ParkingSpot parkingSpot, ParkingProviderApplication parkingProviderApplication) {
//
//    }


}
