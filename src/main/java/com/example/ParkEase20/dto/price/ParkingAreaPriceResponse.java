package com.example.ParkEase20.dto.price;

import com.example.ParkEase20.entity.ParkingAreaPrice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParkingAreaPriceResponse {
    private Long id;

    // Base prices

    private Double bikePricePerHour;


    private Double carPricePerHour;


    private Double suvPricePerHour;


    private Double truckPricePerHour;

    // Optional daily prices
    private Double bikePricePerDay;
    private Double carPricePerDay;
    private Double suvPricePerDay;
    private Double truckPricePerDay;

    private Boolean isActive;


    public static ParkingAreaPriceResponse from(ParkingAreaPrice price){
        ParkingAreaPriceResponse response = new ParkingAreaPriceResponse();
        response.setId(price.getId());
        response.setBikePricePerHour(price.getBikePricePerHour());
        response.setCarPricePerHour(price.getCarPricePerHour());
        response.setSuvPricePerHour(price.getSuvPricePerHour());
        response.setTruckPricePerHour(price.getTruckPricePerHour());
        response.setBikePricePerDay(price.getBikePricePerDay());
        response.setCarPricePerDay(price.getCarPricePerDay());
        response.setSuvPricePerDay(price.getSuvPricePerDay());
        response.setTruckPricePerDay(price.getTruckPricePerDay());
        response.setIsActive(price.getIsActive());
        return response;

    }


}
