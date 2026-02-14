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
@Table(name="parking_provider")
public class ParkingProviderProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String parkingProviderName;

    private String phoneNumber;
    private String email;

    private String parkingAreaImageUrl;
    private String address;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
