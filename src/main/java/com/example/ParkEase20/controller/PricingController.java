package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.price.ParkingAreaPriceRequest;
import com.example.ParkEase20.dto.price.ParkingAreaPriceResponse;
import com.example.ParkEase20.dto.price.ParkingAreaPriceUpdateRequest;
import com.example.ParkEase20.service.PricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-provider/parking-areas/{parkingAreaId}/price")
public class PricingController {
private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping
    public ResponseEntity<Void> createPricing(
            Authentication auth,
            @PathVariable Long parkingAreaId,
            @RequestBody ParkingAreaPriceRequest request
    ) {
pricingService.createPricing(auth, parkingAreaId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updatePricing(
            Authentication auth,
            @PathVariable Long parkingAreaId,
            @RequestBody ParkingAreaPriceUpdateRequest request
    ) {
        pricingService.updatePricing(auth, parkingAreaId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ParkingAreaPriceResponse> getPricing(
            Authentication auth,
            @PathVariable Long parkingAreaId
    ){

        return ResponseEntity.ok(pricingService.getPricing(auth, parkingAreaId));
    }

}
