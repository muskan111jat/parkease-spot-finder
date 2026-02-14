package com.example.ParkEase20.dto;

import com.example.ParkEase20.entity.ParkingProviderApplication;
import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingProviderApplicationAdminViewDto {
    private Long id;

    private Long userId;
    private Long adminId;

    private String parkingProviderName;
    //private String garageOwnerName;
    //private String ParkingAreaName;
    private String phoneNumber;
    private String email;

//    private Double price;
//    private Double width;
//    private Double height;

//    private Double totalArea;

//    private LocalTime startTime;
//    private LocalTime endTime;
//    private Boolean overnightAllowed;

    private String address;
//    private Double latitude;
//    private Double longitude;

    private Boolean isLatest;
    private Boolean isActive;

    private ParkingProviderApplicationStatus status;
    private String rejectionReason;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    private Resource parkingAreaImage;
    private String parkingAreaImageUrl;

public static ParkingProviderApplicationAdminViewDto from (ParkingProviderApplication application){
    ParkingProviderApplicationAdminViewDto dto = new ParkingProviderApplicationAdminViewDto();
    dto.setId(application.getId());
    dto.setUserId(application.getUser().getId());
    if(application.getAdmin()!=null){
        dto.setAdminId(application.getAdmin().getId());
    }

    dto.setParkingProviderName(application.getParkingProviderName());
    dto.setAddress(application.getAddress());
    dto.setIsLatest(application.getIsLatest());
    dto.setParkingAreaImageUrl(application.getParkingAreaImageUrl());
    dto.setRejectionReason(application.getRejectionReason());
    dto.setCreatedAt(application.getCreatedAt());
    dto.setUpdatedAt(application.getUpdatedAt());
    dto.setPhoneNumber(application.getPhoneNumber());
    dto.setEmail(application.getEmail());
    dto.setStatus(application.getStatus());
    return dto;
}
}
