package com.example.ParkEase20.service;

import com.example.ParkEase20.config.CustomUserDetails;
import com.example.ParkEase20.dto.peakprice.ParkingAreaPeakPriceRequest;
import com.example.ParkEase20.dto.peakprice.ParkingAreaPeakPriceResponse;
import com.example.ParkEase20.dto.peakprice.ParkingAreaPeakPriceUpdateRequest;
import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaPeakPrice;
import com.example.ParkEase20.entity.ParkingProviderProfile;
import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.exceptions.NotFoundException;
import com.example.ParkEase20.exceptions.StatusException;
import com.example.ParkEase20.repository.ParkingAreaRepository;
import com.example.ParkEase20.repository.ParkingProviderProfileRepository;
import com.example.ParkEase20.repository.PeakPricingRepository;
import com.example.ParkEase20.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PeakPricingService {
    private final PeakPricingRepository peakPricingRepository;
    private final ParkingAreaRepository parkingAreaRepository;
    private final ParkingProviderProfileRepository parkingProviderProfileRepository;
    private final UserRepository userRepository;

    public PeakPricingService(PeakPricingRepository peakPricingRepository, ParkingAreaRepository parkingAreaRepository, ParkingProviderProfileRepository parkingProviderProfileRepository, UserRepository userRepository) {
        this.peakPricingRepository = peakPricingRepository;
        this.parkingAreaRepository = parkingAreaRepository;
        this.parkingProviderProfileRepository = parkingProviderProfileRepository;
        this.userRepository = userRepository;
    }


    private User getApprovedUserByAuth(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userRepository.findById(userDetails.getUserId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    private ParkingArea getParkingAreaByAuth(Authentication auth, Long parkingAreaId) {
        User user = getApprovedUserByAuth(auth);
        ParkingProviderProfile profile = parkingProviderProfileRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Parking Provider Profile not found"));
        return parkingAreaRepository.findByIdAndProviderProfile(parkingAreaId, profile).orElseThrow(
                () -> new NotFoundException("Parking Area not found"));
    }
    public void createPeakPricing(
            Authentication auth,
            Long parkingAreaId,
            ParkingAreaPeakPriceRequest request
    ) {
        request.validate();
        ParkingArea parkingArea = getParkingAreaByAuth(auth, parkingAreaId);

        if (peakPricingRepository.findByParkingArea(parkingArea).isPresent()) {
            throw new StatusException("Peak pricing already exists for this parking area");
        }



        ParkingAreaPeakPrice peakPrice =ParkingAreaPeakPriceRequest.toEntity(request);;
        peakPrice.setParkingArea(parkingArea);
        peakPrice.setIsActive(true);
        peakPrice.setCreatedAt(LocalDateTime.now());

        peakPricingRepository.save(peakPrice);
    }

    public void updatePeakPricing(
            Authentication auth,
            Long parkingAreaId,
            ParkingAreaPeakPriceUpdateRequest request
    ) {
        request.validate();
        ParkingArea parkingArea = getParkingAreaByAuth(auth, parkingAreaId);

        ParkingAreaPeakPrice peakPrice = peakPricingRepository
                .findByParkingArea(parkingArea)
                .orElseThrow(() -> new NotFoundException("Peak pricing not found"));

        ParkingAreaPeakPriceUpdateRequest.updateParkingAreaPeakPricing(request, peakPrice);
        peakPrice.setUpdatedAt(LocalDateTime.now());

        peakPricingRepository.save(peakPrice);
    }
    public ParkingAreaPeakPriceResponse getPeakPricing(
            Authentication auth,
            Long parkingAreaId
    ) {
        ParkingArea parkingArea = getParkingAreaByAuth(auth, parkingAreaId);

        ParkingAreaPeakPrice peakPrice = peakPricingRepository
                .findByParkingArea(parkingArea)
                .orElseThrow(() -> new NotFoundException("Peak pricing not found"));

        return ParkingAreaPeakPriceResponse.from(peakPrice);
    }
    public void disablePeakPricing(
            Authentication auth,
            Long parkingAreaId
    ) {
        ParkingArea parkingArea = getParkingAreaByAuth(auth, parkingAreaId);

        ParkingAreaPeakPrice peakPrice = peakPricingRepository
                .findByParkingArea(parkingArea)
                .orElseThrow(() -> new NotFoundException("Peak pricing not found"));

        peakPrice.setIsActive(false);
        peakPrice.setUpdatedAt(LocalDateTime.now());

        peakPricingRepository.save(peakPrice);
        peakPricingRepository.delete(peakPrice);
    }
}
