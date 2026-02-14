package com.example.ParkEase20.dto.parkingarea;

import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAreaCardDto {
    private long parkingAreaId;
    private String parkingAreaName;
    private boolean isActive;
    private ParkingAreaType type;
    private double startingPrice;

    public static ParkingAreaCardDto from(ParkingArea parkingArea) {
        ParkingAreaCardDto dto = new ParkingAreaCardDto();
        dto.setParkingAreaId(parkingArea.getId());
        dto.setParkingAreaName(parkingArea.getParkingAreaName());
        dto.setActive(parkingArea.getIsActive());
        dto.setType(parkingArea.getType());
        return dto;
    }


}
