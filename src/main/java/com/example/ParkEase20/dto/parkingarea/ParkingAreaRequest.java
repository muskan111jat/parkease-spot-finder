package com.example.ParkEase20.dto.parkingarea;

import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingAreaRequest {
    @NotBlank(message = "parking area name must be required")
    private String parkingAreaName;
    @NotBlank(message = "parking area address must be required")
    private String parkingAreaAddress;
    @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
    @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
    private Double latitude;
    @DecimalMin(value = "-180.0", message = "LONGITUDE must be >= -180")
    @DecimalMax(value = "180.0", message = "LONGITUDE must be <= 180")
    private Double longitude;
    @NotNull(message = "parking area TYPE is required")
    private ParkingAreaType type;

    private String parkingAreaDescription;

    // FIXED
    @NotNull(message = "opening time is required")

    private LocalTime openingTime;
    @NotNull(message = "closing time is required")
    private LocalTime closingTime;
    @NotNull(message = "overnight allowed  required")
    private Boolean overnightAllowed;

    // EVENT
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventEndDateTime;
    @NotNull(message = "parking area WIDTH is required")
    private Double width;
    @NotNull(message = "parking area HEIGHT is required")
    private Double height;
    @NotNull(message = "parking area image is required")
    private MultipartFile parkingAreaImage;

    private static Double calculateArea(Double width, Double height) {
        return width * height;
    }

    public static ParkingArea toEntity(ParkingAreaRequest parkingAreaRequest) {
        ParkingArea parkingArea = new ParkingArea();
        parkingArea.setParkingAreaName(parkingAreaRequest.getParkingAreaName());
        parkingArea.setParkingAreaAddress(parkingAreaRequest.getParkingAreaAddress());
        parkingArea.setParkingAreaDescription(parkingAreaRequest.getParkingAreaDescription());
        parkingArea.setLatitude(parkingAreaRequest.getLatitude());
        parkingArea.setLongitude(parkingAreaRequest.getLongitude());
        parkingArea.setType(parkingAreaRequest.getType());

        if (parkingAreaRequest.getType() == ParkingAreaType.FIXED) {
            parkingArea.setOpeningTime(parkingAreaRequest.getOpeningTime());
            parkingArea.setClosingTime(parkingAreaRequest.getClosingTime());
            parkingArea.setOvernightAllowed(parkingAreaRequest.getOvernightAllowed());

            // explicitly null event fields
            parkingArea.setEventStartDateTime(null);
            parkingArea.setEventEndDateTime(null);
        }

        if (parkingAreaRequest.getType() == ParkingAreaType.EVENT) {
            parkingArea.setEventStartDateTime(parkingAreaRequest.getEventStartDateTime());
            parkingArea.setEventEndDateTime(parkingAreaRequest.getEventEndDateTime());

            // explicitly null fixed fields
            parkingArea.setOpeningTime(null);
            parkingArea.setClosingTime(null);
            parkingArea.setOvernightAllowed(false);
        }

        parkingArea.setWidth(parkingAreaRequest.getWidth());
        parkingArea.setHeight(parkingAreaRequest.getHeight());
        parkingArea.setTotalArea(calculateArea(parkingArea.getWidth(), parkingArea.getHeight()));
        parkingArea.setIsActive(false);
        parkingArea.setCreatedAt(LocalDateTime.now());

        return parkingArea;
    }

}
