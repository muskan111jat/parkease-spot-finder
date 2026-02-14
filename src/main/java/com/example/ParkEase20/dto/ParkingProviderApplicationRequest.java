package com.example.ParkEase20.dto;

import com.example.ParkEase20.entity.ParkingProviderApplication;
import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import com.example.ParkEase20.entity.User;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingProviderApplicationRequest {
    @NotBlank(message = "Provider name is required")
    private String parkingProviderName;

//    @NotBlank(message = "Owner name is required")
//    private String garageOwnerName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    private MultipartFile parkingAreaImage; // optional branding image

    public static ParkingProviderApplication toEntity(ParkingProviderApplicationRequest appRequest, User user) {
        ParkingProviderApplication app=new ParkingProviderApplication();
        app.setParkingProviderName(appRequest.getParkingProviderName());
        app.setPhoneNumber(appRequest.getPhoneNumber());
        app.setEmail(appRequest.getEmail());
        app.setAddress(appRequest.getAddress());
        app.setUser(user);
        app.setCreatedAt(LocalDateTime.now());
        app.setIsLatest(true);
        app.setStatus(ParkingProviderApplicationStatus.PENDING);
        return app;
    }

}
