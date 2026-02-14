package com.example.ParkEase20.dto;

import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import com.example.ParkEase20.entity.ParkingProviderProfile;
import com.example.ParkEase20.entity.ParkingProviderUpdateApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingProviderUpdateApplicationRequestDto {

    private String parkingProviderName;

    //private String ParkingAreaName;
    private String phoneNumber;
    private String address;

    private String email;
    private MultipartFile parkingAreaImage;
    private Boolean isActive;

    public static ParkingProviderUpdateApplication toParkingProviderUpdateApplication(ParkingProviderUpdateApplicationRequestDto updateDto, ParkingProviderProfile profile) {


        ParkingProviderUpdateApplication app =
                new ParkingProviderUpdateApplication();

        app.setProviderProfile(profile);

        if (updateDto.getParkingProviderName() != null) {
            app.setParkingProviderName(updateDto.getParkingProviderName());
        }

        if (updateDto.getPhoneNumber() != null) {
            app.setPhoneNumber(updateDto.getPhoneNumber());
        }

        if (updateDto.getEmail() != null) {
            app.setEmail(updateDto.getEmail());
        }

        if (updateDto.getAddress() != null) {
            app.setAddress(updateDto.getAddress());
        }
        if(updateDto.getIsActive() != null) {
            app.setIsActive(updateDto.getIsActive());
        }

        app.setStatus(ParkingProviderApplicationStatus.PENDING);
        app.setCreatedAt(LocalDateTime.now());

        return app;


    }

    //private Boolean isActive;
    //
    public boolean hasBigChanges() {
        return false;
    }
    public static void toParkingProviderProfile(ParkingProviderUpdateApplicationRequestDto updateDto, ParkingProviderProfile profile) {
        if (updateDto.getParkingProviderName() != null) {
            profile.setParkingProviderName(updateDto.getParkingProviderName());
        }
        if (updateDto.getPhoneNumber() != null) {
            profile.setPhoneNumber(updateDto.getPhoneNumber());
        }
        if (updateDto.getEmail() != null) {
            profile.setEmail(updateDto.getEmail());
        }
        if (updateDto.getAddress() != null) {
            profile.setAddress(updateDto.getAddress());
        }
        if (updateDto.getIsActive() != null) {
            profile.setIsActive(updateDto.getIsActive());
        }
        profile.setUpdatedAt(LocalDateTime.now());

    }
}
