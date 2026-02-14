package com.example.ParkEase20.dto.peakprice;

import com.example.ParkEase20.entity.ParkingAreaPeakPrice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingAreaPeakPriceResponse {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Double multiplier;
    private Double extraAmount;

    private Boolean isActive;

    public static ParkingAreaPeakPriceResponse from(ParkingAreaPeakPrice price){
        ParkingAreaPeakPriceResponse response = new ParkingAreaPeakPriceResponse();
        response.setId(price.getId());
        response.setStartDateTime(price.getStartDateTime());
        response.setEndDateTime(price.getEndDateTime());
        response.setMultiplier(price.getMultiplier());
        response.setExtraAmount(price.getExtraAmount());
        response.setIsActive(price.getIsActive());
        return response;
    }
}
