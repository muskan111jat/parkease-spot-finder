package com.example.ParkEase20.service;

import com.example.ParkEase20.config.CustomUserDetails;
import com.example.ParkEase20.dto.price.ParkingAreaPriceRequest;
import com.example.ParkEase20.dto.price.ParkingAreaPriceResponse;
import com.example.ParkEase20.dto.price.ParkingAreaPriceUpdateRequest;
import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingAreaPrice;
import com.example.ParkEase20.entity.ParkingProviderProfile;
import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.exceptions.NotFoundException;
import com.example.ParkEase20.exceptions.StatusException;
import com.example.ParkEase20.repository.ParkingAreaRepository;
import com.example.ParkEase20.repository.ParkingProviderProfileRepository;
import com.example.ParkEase20.repository.PricingRepository;
import com.example.ParkEase20.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PricingService {
    private final PricingRepository pricingRepository;
    private final ParkingAreaRepository parkingAreaRepository;
    private final UserRepository userRepository;
    private final ParkingProviderProfileRepository parkingProviderProfileRepository;

    public PricingService(PricingRepository pricingRepository, ParkingAreaRepository parkingAreaRepository, UserRepository userRepository, ParkingProviderProfileRepository parkingProviderProfileRepository) {
        this.pricingRepository = pricingRepository;
        this.parkingAreaRepository = parkingAreaRepository;
        this.userRepository = userRepository;
        this.parkingProviderProfileRepository = parkingProviderProfileRepository;
    }


    private User getApprovedUserByAuth(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userRepository.findById(userDetails.getUserId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }
    private ParkingProviderProfile getParkingProviderProfileByUser(User user) {
        return parkingProviderProfileRepository.findByUser(user).orElseThrow(
                () -> new NotFoundException("Parking Provider  not found")
        );
    }

    private ParkingArea getParkingAreaByAuth(Authentication auth, Long parkingAreaId) {
        User user = getApprovedUserByAuth(auth);
        ParkingProviderProfile parkingProviderProfile = getParkingProviderProfileByUser(user);
        ParkingArea parkingArea=parkingAreaRepository.findByIdAndProviderProfile(parkingAreaId,parkingProviderProfile).orElseThrow(
                () -> new NotFoundException("Parking Area not found by id: "+parkingProviderProfile.getId())
        );
        return parkingArea;
    }

    public void createPricing(
            Authentication auth,
            Long parkingAreaId,
            ParkingAreaPriceRequest request
    ) {
        ParkingArea parkingArea = getParkingAreaByAuth(auth, parkingAreaId);
        if (pricingRepository.findByParkingArea(parkingArea).isPresent()) {
            throw new StatusException("Price already exists for this parking area");
        }
        ParkingAreaPrice pricing = ParkingAreaPriceRequest.toEntity(request);
        pricing.setParkingArea(parkingArea);

        parkingArea.setIsActive(true);
        parkingAreaRepository.save(parkingArea);
        pricingRepository.save(pricing);

    }

    public void updatePricing(
            Authentication auth,
            Long parkingAreaId,
            ParkingAreaPriceUpdateRequest request
    ) {
        ParkingArea parkingArea = getParkingAreaByAuth(auth, parkingAreaId);
        ParkingAreaPrice pricing = pricingRepository.findByParkingArea(parkingArea).orElseThrow(
                () -> new NotFoundException("Parking Area not found")
        );
        ParkingAreaPriceUpdateRequest.updateParkingAreaPrice(request, pricing);
        pricing.setUpdatedAt(LocalDateTime.now());
        pricingRepository.save(pricing);
        parkingArea.setIsActive(Boolean.TRUE.equals(pricing.getIsActive()));
        parkingAreaRepository.save(parkingArea);
    }
    public ParkingAreaPriceResponse getPricing(
            Authentication auth,
            Long parkingAreaId
    ) {
        ParkingArea parkingArea = getParkingAreaByAuth(auth, parkingAreaId);

        ParkingAreaPrice pricing = pricingRepository.findByParkingArea(parkingArea)
                .orElseThrow(() -> new NotFoundException("Price not found"));

        return ParkingAreaPriceResponse.from(pricing);
    }

}
