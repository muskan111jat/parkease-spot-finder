package com.example.ParkEase20.dto;

import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
public class ParkingProviderApplicationStatusResponse {
    private ParkingProviderApplicationStatus status;
    private String rejectionReason;
}
