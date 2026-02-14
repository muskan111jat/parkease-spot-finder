package com.example.ParkEase20.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="provider_update_application")
public class ParkingProviderUpdateApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_profile_id", nullable = false)
    private ParkingProviderProfile providerProfile;

    @ManyToOne
    @JoinColumn(name = "approved_by_admin_id")
    private User admin;

    private String parkingProviderName;
    private String phoneNumber;
    private String email;
    private String parkingAreaImageUrl;
    private String address;

    @Enumerated(EnumType.STRING)
    private ParkingProviderApplicationStatus status;

    private String rejectionReason;
    //private Boolean isLatest;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;


    public static void updateParkingProviderProfile(ParkingProviderUpdateApplication application, ParkingProviderProfile profile) {
        if (application.getProviderProfile() != null) {
            profile.setParkingProviderName(application.getProviderProfile().getParkingProviderName());
        }
        if (application.getPhoneNumber() != null) {
            profile.setPhoneNumber(application.getProviderProfile().getPhoneNumber());
        }
        if (application.getEmail() != null) {
            profile.setEmail(application.getProviderProfile().getEmail());
        }
        if (application.getAddress() != null) {
            profile.setAddress(application.getProviderProfile().getAddress());
        }
        if(application.getParkingAreaImageUrl()!=null){
            profile.setParkingAreaImageUrl(application.getParkingAreaImageUrl());
        }
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setIsActive(application.getIsActive());
    }
}
