package com.example.ParkEase20.dto;

import com.example.ParkEase20.entity.ParkingProviderProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingProviderViewDto {

    private String parkingProviderName;

    private String phoneNumber;
    private String email;

    private String parkingAreaImageUrl;
    private String address;

    private Boolean isActive;

    public static ParkingProviderViewDto fromParkingProviderProfile(ParkingProviderProfile parkingProviderProfile) {
        ParkingProviderViewDto parkingProviderViewDto = new ParkingProviderViewDto();
        parkingProviderViewDto.setAddress(parkingProviderProfile.getAddress());
        parkingProviderViewDto.setEmail(parkingProviderProfile.getEmail());
        parkingProviderViewDto.setPhoneNumber(parkingProviderProfile.getPhoneNumber());
        parkingProviderViewDto.setParkingProviderName(parkingProviderProfile.getParkingProviderName());
        parkingProviderViewDto.setParkingAreaImageUrl(parkingProviderProfile.getParkingAreaImageUrl());
        parkingProviderViewDto.setIsActive(parkingProviderProfile.getIsActive());
        return parkingProviderViewDto;
    }
}
