package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.ParkingProviderApplicationStatusResponse;
import com.example.ParkEase20.dto.ParkingProviderUpdateApplicationRequestDto;
import com.example.ParkEase20.dto.ParkingProviderViewDto;
import com.example.ParkEase20.service.ParkingProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-provider")
public class ParkingProviderController {
    @Autowired
    private ParkingProviderService parkingProviderService;

    @PutMapping(value="/update",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> updateParkingProvider(Authentication auth, @ModelAttribute ParkingProviderUpdateApplicationRequestDto parkingProviderUpdateApplicationRequestDto) {
        parkingProviderService.updateParkingProvider(auth, parkingProviderUpdateApplicationRequestDto);
        return ResponseEntity.ok().body("Parking provider update request successfully applied");
    }

    @GetMapping("/get-profile")
    public ResponseEntity<?> getParkingProviderProfile(Authentication auth) {
        ParkingProviderViewDto dto = parkingProviderService.getParkingProviderViewDto(auth);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping("/update-request-status")
    public ResponseEntity<ParkingProviderApplicationStatusResponse> getParkingProviderRequestStatus(Authentication auth) {
        return ResponseEntity.ok().body(parkingProviderService.getParkingProviderUpdateApplicationStatus(auth));
    }
}
