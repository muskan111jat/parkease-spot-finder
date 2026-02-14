package com.example.ParkEase20.dto.parkingarea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAreaSearchCardDto {
    private Long parkingAreaId;
    private String parkingAreaName;
    private String address;
    private String imageUrl;

    private Double startingPrice;

    //  contact info (read-only)
    private String providerPhone;
    private String providerEmail;

    private Boolean isEventBased;
}
