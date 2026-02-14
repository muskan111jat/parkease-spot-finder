package com.example.ParkEase20.dto.price;

import com.example.ParkEase20.entity.ParkingAreaPrice;
import com.example.ParkEase20.exceptions.InvalidInputException;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingAreaPriceRequest {
    private Double bikePricePerHour;
    private Double carPricePerHour;
    private Double suvPricePerHour;
    private Double truckPricePerHour;

    private Double bikePricePerDay;
    private Double carPricePerDay;
    private Double suvPricePerDay;
    private Double truckPricePerDay;

    public void validate(){
        boolean hasAtLeastOnePrice=isValid(this.bikePricePerHour,this.carPricePerHour)||
                isValid(this.carPricePerHour,this.carPricePerDay)||
                isValid(this.suvPricePerHour,this.suvPricePerDay)||
                isValid(this.truckPricePerHour,this.truckPricePerDay);
        if(!hasAtLeastOnePrice){
            throw new InvalidInputException("Parking Area Price Request is invalid");
        }
    }
    private boolean isValid(Double perHourPrice,Double perDayPrice){
        return perHourPrice!=null || perDayPrice!=null;
    }


    public static ParkingAreaPrice toEntity(ParkingAreaPriceRequest request) {
        request.validate();
        ParkingAreaPrice price = new ParkingAreaPrice();
        price.setBikePricePerDay(request.getBikePricePerDay());
        price.setCarPricePerDay(request.getCarPricePerDay());
        price.setSuvPricePerDay(request.getSuvPricePerDay());
        price.setTruckPricePerDay(request.getTruckPricePerDay());

        price.setBikePricePerHour(request.getBikePricePerHour());
        price.setCarPricePerHour(request.getCarPricePerHour());
        price.setSuvPricePerHour(request.getSuvPricePerHour());
        price.setTruckPricePerHour(request.getTruckPricePerHour());

        price.setCreatedAt(LocalDateTime.now());
        price.setIsActive(true);
        return price;
    }
}
