package com.example.ParkEase20.service;


import com.example.ParkEase20.config.CustomUserDetails;
import com.example.ParkEase20.dto.parkingarea.ParkingAreaCardDto;
import com.example.ParkEase20.dto.parkingarea.ParkingAreaRequest;
import com.example.ParkEase20.dto.parkingarea.ParkingAreaResponse;
import com.example.ParkEase20.dto.parkingarea.ParkingAreaUpdateRequest;
import com.example.ParkEase20.entity.ImageDirectoryType;
import com.example.ParkEase20.entity.ParkingArea;
import com.example.ParkEase20.entity.ParkingProviderProfile;
import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.exceptions.FileStorageException;
import com.example.ParkEase20.exceptions.NotFoundException;
import com.example.ParkEase20.repository.ParkingAreaRepository;
import com.example.ParkEase20.repository.ParkingProviderProfileRepository;
import com.example.ParkEase20.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingAreaService {
private final ParkingAreaRepository parkingAreaRepository;
private final ParkingProviderProfileRepository parkingProviderProfileRepository;
private final UserRepository userRepository;
private final FileStorageService fileStorageService;

    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository, ParkingProviderProfileRepository parkingProviderProfileRepository, UserRepository userRepository, FileStorageService fileStorageService) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.parkingProviderProfileRepository = parkingProviderProfileRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
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
    private Double calculateArea(Double width, Double height) {
        return width * height;
    }
    private String getImagePath(MultipartFile file, Long id, ImageDirectoryType type) {
        if (file == null) {
            return null;
        }
        String path;
        try {
            path = fileStorageService.saveFile(file, id,type);
        } catch (Exception e) {
            throw new FileStorageException("Could not save Parking Provider Application "+id.toString()+" "+type.name(), e);
        }
        return path;
    }
    public void createParkingArea(Authentication auth, ParkingAreaRequest parkingAreaRequest) {
        User user = getApprovedUserByAuth(auth);
        ParkingProviderProfile parkingProviderProfile = getParkingProviderProfileByUser(user);
        ParkingArea parkingArea=ParkingAreaRequest.toEntity(parkingAreaRequest);
        parkingArea.setProviderProfile(parkingProviderProfile);
        ParkingArea savedParkingArea=parkingAreaRepository.save(parkingArea);
        if(parkingAreaRequest.getParkingAreaImage()!=null){
            String path=getImagePath(parkingAreaRequest.getParkingAreaImage(),savedParkingArea.getId(),ImageDirectoryType.PARKING_AREA);
            parkingArea.setParkingAreaImageUrl(path);
            parkingAreaRepository.save(parkingArea);
        }

    }
    public void updateParkingArea(Authentication auth, Long parkingAreaId, ParkingAreaUpdateRequest updateRequest) {
        User user = getApprovedUserByAuth(auth);
        ParkingProviderProfile parkingProviderProfile = getParkingProviderProfileByUser(user);
        ParkingArea parkingArea=parkingAreaRepository.findByIdAndProviderProfile(parkingAreaId,parkingProviderProfile).orElseThrow(
                () -> new NotFoundException("Parking Area not found")
        );
        ParkingAreaUpdateRequest.updateParkingArea(updateRequest,parkingArea);
        parkingArea.setUpdatedAt(LocalDateTime.now());
        if(updateRequest.getParkingAreaImage()!=null){
            fileStorageService.deleteFile(parkingArea.getParkingAreaImageUrl());
            String path=getImagePath(updateRequest.getParkingAreaImage(),parkingArea.getId(),ImageDirectoryType.PARKING_AREA);
            parkingArea.setParkingAreaImageUrl(path);
        }
        parkingAreaRepository.save(parkingArea);
    }

    public List<ParkingAreaCardDto> getParkingAreaCard(Authentication auth) {
        User user = getApprovedUserByAuth(auth);
        ParkingProviderProfile parkingProviderProfile = getParkingProviderProfileByUser(user);
        List<ParkingArea> parkingAreas=parkingAreaRepository.findByProviderProfile(parkingProviderProfile);
        if(parkingAreas.isEmpty()){
            throw new NotFoundException("Parking Areas not found");
        }
        return parkingAreas.stream()
                .map(area->ParkingAreaCardDto.from(area)).toList();

    }
    public ParkingAreaResponse getParkingAreas( Authentication auth,Long parkingAreaId) {
        User user = getApprovedUserByAuth(auth);
        ParkingProviderProfile parkingProviderProfile = getParkingProviderProfileByUser(user);
        ParkingArea parkingArea=parkingAreaRepository.findByIdAndProviderProfile(parkingAreaId,parkingProviderProfile).orElseThrow(
                () -> new NotFoundException("Parking Area not found by id: "+parkingAreaId.toString())
        );
        return ParkingAreaResponse.from(parkingArea);
    }

}
