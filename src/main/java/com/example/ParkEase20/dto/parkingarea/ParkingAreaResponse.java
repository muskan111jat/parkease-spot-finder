package com.example.ParkEase20.dto.parkingarea;

import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAreaResponse {
    private Long id;

    private String parkingAreaName;
    private String parkingAreaAddress;
    private Double latitude;
    private Double longitude;

    private String parkingAreaImageUrl;

    private ParkingAreaType type; // FIXED / EVENT

    // FIXED
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean overnightAllowed;

    // EVENT
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventEndDateTime;

    private Double width;
    private Double height;
    private Double totalArea;

    private Boolean isActive;

    public static ParkingAreaResponse from(ParkingArea area) {
        ParkingAreaResponse response = new ParkingAreaResponse();

        response.setId(area.getId());
        response.setParkingAreaName(area.getParkingAreaName());
        response.setParkingAreaAddress(area.getParkingAreaAddress());

        response.setLatitude(area.getLatitude());
        response.setLongitude(area.getLongitude());

        response.setParkingAreaImageUrl(area.getParkingAreaImageUrl());

        response.setType(area.getType());

        // FIXED
        response.setOpeningTime(area.getOpeningTime());
        response.setClosingTime(area.getClosingTime());
        response.setOvernightAllowed(area.getOvernightAllowed());

        // EVENT
        response.setEventStartDateTime(area.getEventStartDateTime());
        response.setEventEndDateTime(area.getEventEndDateTime());

        response.setWidth(area.getWidth());
        response.setHeight(area.getHeight());

        if (area.getTotalArea() != null) {
            response.setTotalArea(area.getTotalArea());
        } else if (area.getWidth() != null && area.getHeight() != null) {
            response.setTotalArea(area.getWidth() * area.getHeight());
        }

        response.setIsActive(area.getIsActive());

        return response;

    }
}
