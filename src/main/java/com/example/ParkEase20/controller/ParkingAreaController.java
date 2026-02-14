package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.parkingarea.ParkingAreaCardDto;
import com.example.ParkEase20.dto.parkingarea.ParkingAreaRequest;
import com.example.ParkEase20.dto.parkingarea.ParkingAreaResponse;
import com.example.ParkEase20.dto.parkingarea.ParkingAreaUpdateRequest;

import com.example.ParkEase20.service.ParkingAreaService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-provider/parking-areas")
public class ParkingAreaController {
    private final ParkingAreaService parkingAreaService;

    public ParkingAreaController(ParkingAreaService parkingAreaService) {
        this.parkingAreaService = parkingAreaService;
    }
    @GetMapping()
    public ResponseEntity<List<ParkingAreaCardDto>> getAllParkingAreas(Authentication authentication) {
      List<ParkingAreaCardDto> parkingAreaCard=parkingAreaService.getParkingAreaCard(authentication);

        return ResponseEntity.ok(parkingAreaCard) ;
    }
    @GetMapping("/{parkingAreaId}")
    public ResponseEntity<ParkingAreaResponse> getParkingAreaById(Authentication auth, @PathVariable Long parkingAreaId) {
        return ResponseEntity.ok(parkingAreaService.getParkingAreas(auth,parkingAreaId));
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createParkingArea(Authentication auth, @Valid @ModelAttribute ParkingAreaRequest request){
        parkingAreaService.createParkingArea(auth,request);
        return ResponseEntity.ok("success");
    }
    @PutMapping(value="/{parkingAreaId}/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateParkingArea(Authentication auth,@Valid @ModelAttribute ParkingAreaUpdateRequest request,@PathVariable Long parkingAreaId){
        parkingAreaService.updateParkingArea(auth,parkingAreaId,request);
        return ResponseEntity.ok("success");
    }
}
