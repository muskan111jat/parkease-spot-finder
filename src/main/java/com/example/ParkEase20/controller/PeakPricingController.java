package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.peakprice.ParkingAreaPeakPriceRequest;
import com.example.ParkEase20.dto.peakprice.ParkingAreaPeakPriceUpdateRequest;
import com.example.ParkEase20.service.PeakPricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-provider/parking-areas/{parkingAreaId}/peak-pricing")
public class PeakPricingController {

private final PeakPricingService peakPricingService;

    public PeakPricingController(PeakPricingService peakPricingService) {
        this.peakPricingService = peakPricingService;
    }

    @PostMapping
    public ResponseEntity<?> createPeakPricing(
            Authentication auth,
            @PathVariable Long parkingAreaId,
            @RequestBody ParkingAreaPeakPriceRequest request
    ) {
        peakPricingService.createPeakPricing(auth,parkingAreaId,request);
        return ResponseEntity.ok("success");
    }
    @PutMapping
    public ResponseEntity<?> updatePeakPricing(
            Authentication auth,
            @PathVariable Long parkingAreaId,
            @RequestBody ParkingAreaPeakPriceUpdateRequest request
    ) {
        peakPricingService.updatePeakPricing(auth,parkingAreaId,request);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<?> getPeakPricing(
            Authentication auth,
            @PathVariable Long parkingAreaId
    ) {

        return ResponseEntity.ok(peakPricingService.getPeakPricing(auth,parkingAreaId));
    }

    @DeleteMapping
    public ResponseEntity<?> disablePeakPricing(
            Authentication auth,
            @PathVariable Long parkingAreaId
    ){
        peakPricingService.disablePeakPricing(auth,parkingAreaId);
        return ResponseEntity.ok(null);
    }
}
