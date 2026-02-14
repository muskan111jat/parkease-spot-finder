package com.example.ParkEase20.dto.peakprice;

import com.example.ParkEase20.entity.ParkingAreaPeakPrice;
import com.example.ParkEase20.entity.ParkingAreaPrice;
import com.example.ParkEase20.exceptions.InvalidInputException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingAreaPeakPriceRequest {
    @NotNull(message="startDateTime is requires")
    private LocalDateTime startDateTime;
    @NotNull(message="startDateTime is requires")
    private LocalDateTime endDateTime;

    private Double multiplier;
    private Double extraAmount;

    private Boolean isActive;
    public void validate(){
        if (startDateTime == null || endDateTime == null) {
            throw new InvalidInputException("startDateTime and endDateTime cannot be empty");
        }
        boolean flag=(multiplier != null || extraAmount != null);
        if(!flag){
            throw new InvalidInputException("Multiplier and extra amount cannot be empty");
        }
    }


    public static ParkingAreaPeakPrice toEntity(ParkingAreaPeakPriceRequest request){
        request.validate();
        ParkingAreaPeakPrice price = new ParkingAreaPeakPrice();
        price.setStartDateTime(request.getStartDateTime());
        price.setEndDateTime(request.getEndDateTime());
        price.setMultiplier(request.getMultiplier());
        price.setExtraAmount(request.getExtraAmount());
        price.setIsActive(request.getIsActive());
        return price;
    }

}
