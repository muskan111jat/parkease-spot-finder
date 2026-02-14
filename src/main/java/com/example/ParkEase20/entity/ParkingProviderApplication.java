package com.example.ParkEase20.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parking_provider_application")
public class ParkingProviderApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "approved_by_admin_id")
    private User admin;

    @Column(nullable = false)
    private String parkingProviderName;
//    @Column(nullable = false)
//    private String garageOwnerName;
//    @Column(nullable = false)
//    private String ParkingAreaName;
    private String phoneNumber;
    private String email;

//    @Column(nullable = false)
//    private Double price;
//
//    @Column(nullable = false)
//    private Double width;
//    @Column(nullable = false)
//    private Double height;
//
//    private Double totalArea;


//    private LocalTime startTime;
//    private LocalTime endTime;
//    private Boolean overnightAllowed;

    private String parkingAreaImageUrl;

    private String address;

//    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
//    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
//    private Double latitude;
//    @DecimalMin(value = "-180.0", message = "Latitude must be >= -180")
//    @DecimalMax(value = "180.0", message = "Latitude must be <= 180")
//    private Double longitude;

    private Boolean isLatest;
    //private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private ParkingProviderApplicationStatus status;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // BOOLAN IS PEAKDAYS
    // DOUBLE PEAK BASE DAYS
    //

}
