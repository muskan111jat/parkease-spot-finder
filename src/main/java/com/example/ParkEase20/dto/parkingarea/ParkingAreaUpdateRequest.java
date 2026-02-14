package com.example.ParkEase20.dto.parkingarea;

import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAreaUpdateRequest {
    private String parkingAreaName;
    private String parkingAreaAddress;
    private Double latitude;
    private Double longitude;

    private ParkingAreaType type; // optional (FIXED / EVENT)

    // FIXED
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean overnightAllowed;

    // EVENT
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventEndDateTime;

    private Double width;
    private Double height;
    private String parkingAreaDescription;

    private MultipartFile parkingAreaImage;

    private Boolean isActive;
    public static void updateParkingArea(ParkingAreaUpdateRequest request, ParkingArea parkingArea) {
        if(request.getParkingAreaName()!=null){
            parkingArea.setParkingAreaName(request.getParkingAreaName());
        }
        if(request.getParkingAreaAddress()!=null){
            parkingArea.setParkingAreaAddress(request.getParkingAreaAddress());
        }
        if(request.getLatitude()!=null){
            parkingArea.setLatitude(request.getLatitude());
        }
        if(request.getLongitude()!=null){
            parkingArea.setLongitude(request.getLongitude());
        }
        if(request.getParkingAreaDescription()!=null){
            parkingArea.setParkingAreaDescription(request.getParkingAreaDescription());
        }
        if(request.getWidth()!=null){
            parkingArea.setWidth(request.getWidth());
        }
        if(request.getHeight()!=null){
            parkingArea.setHeight(request.getHeight());
        }
        // cal new area
        if(request.getWidth()!=null||request.getHeight()!=null){
            parkingArea.setTotalArea(request.getWidth() * request.getHeight());
        }
        if(request.getOpeningTime()!=null){
            parkingArea.setOpeningTime(request.getOpeningTime());
        }
        if(request.getClosingTime()!=null){
            parkingArea.setClosingTime(request.getClosingTime());
        }
        if(request.getOvernightAllowed()!=null){
            parkingArea.setOvernightAllowed(request.getOvernightAllowed());
        }
        if(request.getEventStartDateTime()!=null){
            parkingArea.setEventStartDateTime(request.getEventStartDateTime());
        }
        if(request.getEventEndDateTime()!=null){
            parkingArea.setEventEndDateTime(request.getEventEndDateTime());
        }

    }

}
