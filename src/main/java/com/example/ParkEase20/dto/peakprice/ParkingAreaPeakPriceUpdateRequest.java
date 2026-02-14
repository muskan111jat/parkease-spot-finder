package com.example.ParkEase20.dto.peakprice;

import com.example.ParkEase20.entity.ParkingAreaPeakPrice;
import com.example.ParkEase20.exceptions.InvalidCredentialsException;
import com.example.ParkEase20.exceptions.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAreaPeakPriceUpdateRequest {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private Double multiplier;
    private Double extraAmount;

    private Boolean isActive;

public void validate(){
    if(this.startDateTime == null || this.endDateTime == null){
        throw new InvalidInputException("Start and end time cannot be empty");
    }
    if(this.multiplier == null||this.extraAmount == null){
        throw new InvalidInputException("Multiplier and extra amount cannot be empty");
    }
}

    public static void updateParkingAreaPeakPricing(ParkingAreaPeakPriceUpdateRequest request, ParkingAreaPeakPrice price){
        if(request.getStartDateTime()!=null){
            price.setStartDateTime(request.getStartDateTime());
        }
        if(request.getEndDateTime()!=null){
            price.setEndDateTime(request.getEndDateTime());
        }
        if(request.getMultiplier()!=null){
            price.setMultiplier(request.getMultiplier());
        }
        if(request.getExtraAmount()!=null){
            price.setExtraAmount(request.getExtraAmount());
        }
        if(request.getIsActive()!=null){
            price.setIsActive(request.getIsActive());
        }
    }

}
