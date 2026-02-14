package com.example.ParkEase20.dto.price;

import com.example.ParkEase20.entity.ParkingAreaPrice;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ParkingAreaPriceUpdateRequest {
    private Double bikePricePerHour;
    private Double carPricePerHour;
    private Double suvPricePerHour;
    private Double truckPricePerHour;

    private Double bikePricePerDay;
    private Double carPricePerDay;
    private Double suvPricePerDay;
    private Double truckPricePerDay;

    private Boolean isActive;

    public static void updateParkingAreaPrice(ParkingAreaPriceUpdateRequest request, ParkingAreaPrice price) {
        if(request.getBikePricePerDay() != null ) {
            price.setBikePricePerDay(request.getBikePricePerDay());
        }
        if(request.getCarPricePerDay() != null ) {
            price.setCarPricePerDay(request.getCarPricePerDay());
        }
        if(request.getSuvPricePerDay() != null ) {
            price.setSuvPricePerDay(request.getSuvPricePerDay());
        }
        if (request.getTruckPricePerDay() != null ) {
            price.setTruckPricePerDay(request.getTruckPricePerDay());
        }

        if(request.getBikePricePerHour() != null ) {
            price.setBikePricePerHour(request.getBikePricePerHour());
        }
        if(request.getCarPricePerHour() != null ) {
            price.setCarPricePerHour(request.getCarPricePerHour());
        }
        if(request.getSuvPricePerHour() != null ) {
            price.setSuvPricePerHour(request.getSuvPricePerHour());
        }
        if(request.getTruckPricePerHour() != null) {
            price.setTruckPricePerHour(request.getTruckPricePerHour());
        }
        if(request.getIsActive() != null) {
            price.setIsActive(request.getIsActive());
        }
        price.setUpdatedAt(LocalDateTime.now());


    }
}
