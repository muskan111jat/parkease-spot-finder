package com.example.ParkEase20.dto;

import com.example.ParkEase20.entity.ParkingProviderApplication;
import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingProviderAdminListCard {
    private long parkingProviderApplicationId;
    private String parkingProviderName;
    private ParkingProviderApplicationStatus parkingProviderApplicationStatus;
    private LocalDateTime submittedAt;
    private LocalDateTime updatedAt;

    public static ParkingProviderAdminListCard fromParkingProviderApplication(ParkingProviderApplication parkingProviderApplication) {
        ParkingProviderAdminListCard card = new ParkingProviderAdminListCard();
        card.parkingProviderApplicationId = parkingProviderApplication.getId();
        card.parkingProviderName = parkingProviderApplication.getParkingProviderName();
        card.parkingProviderApplicationStatus = parkingProviderApplication.getStatus();
        card.setUpdatedAt(parkingProviderApplication.getUpdatedAt());
        card.setSubmittedAt(parkingProviderApplication.getCreatedAt());
        return card;
    }
}
