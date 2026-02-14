package com.example.ParkEase20.service;

import com.example.ParkEase20.config.CustomUserDetails;
import com.example.ParkEase20.dto.*;
import com.example.ParkEase20.entity.*;
import com.example.ParkEase20.exceptions.FileStorageException;
import com.example.ParkEase20.exceptions.NotFoundException;
import com.example.ParkEase20.exceptions.StatusException;
import com.example.ParkEase20.repository.ParkingProviderApplicationRepository;
import com.example.ParkEase20.repository.ParkingProviderProfileRepository;
import com.example.ParkEase20.repository.ParkingProviderUpdateApplicationRepository;
import com.example.ParkEase20.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingProviderService {
    private final ParkingProviderApplicationRepository applicationRepository;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final ParkingProviderUpdateApplicationRepository updateApplicationRepository;
    private final ParkingProviderProfileRepository profileRepository;

    public ParkingProviderService(ParkingProviderApplicationRepository parkingProviderRepository, FileStorageService fileStorageService, UserRepository userRepository, ParkingProviderUpdateApplicationRepository updateApplicationRepository, ParkingProviderProfileRepository profileRepository) {
        this.applicationRepository = parkingProviderRepository;
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;

        this.updateApplicationRepository = updateApplicationRepository;
        this.profileRepository = profileRepository;
    }

    private User getApprovedUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));
    }

    private void createProviderProfileFromApplication(
            ParkingProviderApplication app
    ) {
        ParkingProviderProfile profile = new ParkingProviderProfile();

        profile.setUser(app.getUser());
        profile.setParkingProviderName(app.getParkingProviderName());
        profile.setPhoneNumber(app.getPhoneNumber());
        profile.setEmail(app.getEmail());
        profile.setAddress(app.getAddress());
        profile.setParkingAreaImageUrl(app.getParkingAreaImageUrl());

        profile.setIsActive(true);
        profile.setCreatedAt(LocalDateTime.now());

        profileRepository.save(profile);
    }

    private String getImagePath(MultipartFile file, Long id,ImageDirectoryType type) {
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

    private ParkingProviderApplication getParkingProviderApplicationByAuth(Authentication auth) {

        User user = getApprovedUserByAuth(auth);
        return applicationRepository.findByUserAndIsLatestTrue(user).orElseThrow(
                () -> new NotFoundException("Parking provider's Application not found")
        );
    }

    private User getApprovedUserByAuth(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userRepository.findById(userDetails.getUserId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    private void createParkingProviderApplication(User user, ParkingProviderApplicationRequest parkingProviderApplicationRequest) {
        ParkingProviderApplication parkingProviderApplication = ParkingProviderApplicationRequest.toEntity(parkingProviderApplicationRequest, user);
        //parkingProviderApplication.setIsActive(false);
        ParkingProviderApplication saved =applicationRepository.save(parkingProviderApplication);
        String path;
        try {
            path = fileStorageService.saveFile(parkingProviderApplicationRequest.getParkingAreaImage(), parkingProviderApplication.getId(),ImageDirectoryType.APPLICATION);
        } catch (Exception e) {
            throw new FileStorageException("Could not save Parking Provider Application ", e);
        }
        saved.setParkingAreaImageUrl(path);
        applicationRepository.save(saved);




    }

    private void validateReApply(ParkingProviderApplication parkingProviderApplication) {
        LocalDateTime now = parkingProviderApplication.getUpdatedAt();
        if (now == null) {
            throw new StatusException("Parking provider application  updated time is missing");
        }
        LocalDateTime allowedAt = now.plusDays(15);
        if (LocalDateTime.now().isBefore(allowedAt)) {
            long leftDays = ChronoUnit.DAYS.between(LocalDateTime.now(), allowedAt);
            throw new StatusException("you can re apply after " + leftDays + " days");
        }
    }

    public void applyParkingProviderApplication(Authentication auth, ParkingProviderApplicationRequest parkingProviderApplicationRequest) {
        User user = getApprovedUserByAuth(auth);
        Optional<ParkingProviderApplication> applicationData = applicationRepository.findByUserAndIsLatestTrue(user);
        if (applicationData.isPresent()) {
            ParkingProviderApplication parkingProviderApplication = applicationData.get();
            switch (parkingProviderApplication.getStatus()) {
                case PENDING:
                    throw new StatusException("your application has already been pending");
                case APPROVED:
                    throw new StatusException("your application has already been approved");
                case REJECTED:
                    validateReApply(parkingProviderApplication);
                    parkingProviderApplication.setIsLatest(false);
                    applicationRepository.save(parkingProviderApplication);


            }


        }
        createParkingProviderApplication(user, parkingProviderApplicationRequest);

    }

    // this method is for status checking whether user has applied for to became provider right
    public ParkingProviderApplicationStatusResponse getParkingProviderApplicationStatus(Authentication auth) {
        User user = getApprovedUserByAuth(auth);
        return applicationRepository
                .findByUserAndIsLatestTrue(user)
                .map(application -> switch (application.getStatus()) {

                    case PENDING, APPROVED -> new ParkingProviderApplicationStatusResponse(
                            application.getStatus(),
                            null
                    );

                    case REJECTED -> new ParkingProviderApplicationStatusResponse(
                            application.getStatus(),
                            application.getRejectionReason()
                    );
                    case NOT_APPLIED -> throw new StatusException("NOT_APPLIED shall not be ever applied");

                })
                .orElse(
                        new ParkingProviderApplicationStatusResponse(
                                ParkingProviderApplicationStatus.NOT_APPLIED,
                                null
                        )
                );
    }

    public void approveParkingProvider(Authentication auth, Long parkingProviderId) {
        User admin = getApprovedUserByAuth(auth);
        ParkingProviderApplication parkingProviderApplication = applicationRepository.findById(parkingProviderId).orElseThrow(
                () -> new NotFoundException("Parking provider application not found")
        );
        if (!parkingProviderApplication.getStatus().equals(ParkingProviderApplicationStatus.PENDING)) {
            throw new StatusException("Request has been processed as " + parkingProviderApplication.getStatus().name());
        }
        User user = userRepository.findById(parkingProviderApplication.getUser().getId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        parkingProviderApplication.setStatus(ParkingProviderApplicationStatus.APPROVED);
        parkingProviderApplication.setUpdatedAt(LocalDateTime.now());
        //parkingProviderApplication.setIsActive(true);
        parkingProviderApplication.setAdmin(admin);
        user.setUserRole(UserRole.PARKING_PROVIDER);
        applicationRepository.save(parkingProviderApplication);
        userRepository.save(user);
        createProviderProfileFromApplication(parkingProviderApplication);

    }

    public void denyParkingProvider(Authentication auth, Long parkingProviderId, String reason) {
        User admin = getApprovedUserByAuth(auth);
        ParkingProviderApplication parkingProviderApplication = applicationRepository.findById(parkingProviderId).orElseThrow(
                () -> new NotFoundException("Parking provider application not found")
        );
        if (!parkingProviderApplication.getStatus().equals(ParkingProviderApplicationStatus.PENDING)) {
            throw new StatusException("Request has been processed as " + parkingProviderApplication.getStatus().name());
        }
        parkingProviderApplication.setStatus(ParkingProviderApplicationStatus.REJECTED);
        parkingProviderApplication.setUpdatedAt(LocalDateTime.now());
        //parkingProviderApplication.setIsActive(false);
        parkingProviderApplication.setAdmin(admin);
//        User user=userRepository.findById(parkingProviderApplication.getUser().getId()).orElseThrow(
//                () -> new NotFoundException("User not found")
//        );

        parkingProviderApplication.setRejectionReason(reason);
        applicationRepository.save(parkingProviderApplication);
//        userRepository.save(user);
    }


    public void updateParkingProvider(Authentication auth, ParkingProviderUpdateApplicationRequestDto updateDto) {
        User provider = getApprovedUserByAuth(auth);
        ParkingProviderProfile profile = profileRepository.findByUser(provider).orElseThrow(
                () -> new NotFoundException("Parking provider profile not found")
        );
        boolean needApproval = updateDto.hasBigChanges();
        if (needApproval) {
            boolean pendingRequestExists = updateApplicationRepository.existsByProviderProfileAndStatus(profile, ParkingProviderApplicationStatus.PENDING);
            if (pendingRequestExists) {
                throw new StatusException("an update request has already requested");
            }
            ParkingProviderUpdateApplication updateApplication = ParkingProviderUpdateApplicationRequestDto.toParkingProviderUpdateApplication(updateDto, profile);
            ParkingProviderUpdateApplication savedApplication=updateApplicationRepository.save(updateApplication);
            if (updateDto.getParkingAreaImage() != null) {
                updateApplication.setParkingAreaImageUrl(getImagePath(updateDto.getParkingAreaImage(), savedApplication.getId(), ImageDirectoryType.UPDATE_APPLICATION));
                updateApplicationRepository.save(updateApplication);
            }


            return;
        }
        ParkingProviderUpdateApplicationRequestDto.toParkingProviderProfile(updateDto, profile);
        if (updateDto.getParkingAreaImage() != null) {
            profile.setParkingAreaImageUrl(getImagePath(updateDto.getParkingAreaImage(), profile.getId(),ImageDirectoryType.PROVIDER));
        }
        profileRepository.save(profile);


    }

    public void approveUpdateApplication(Authentication auth, Long applicationId) {
        User admin = getApprovedUserByAuth(auth);
        ParkingProviderUpdateApplication application = updateApplicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Parking provider application not found")
        );
        if (!application.getStatus().equals(ParkingProviderApplicationStatus.PENDING)) {
            throw new StatusException("Request has been processed as " + application.getStatus().name());
        }
        application.setStatus(ParkingProviderApplicationStatus.APPROVED);
        ParkingProviderProfile profile = application.getProviderProfile();
        application.setUpdatedAt(LocalDateTime.now());
        application.setAdmin(admin);
        ParkingProviderUpdateApplication.updateParkingProviderProfile(application, profile);
        updateApplicationRepository.save(application);
        profileRepository.save(profile);

    }

    public void denyUpdateApplication(Authentication auth, Long applicationId, String reason) {
        User admin = getApprovedUserByAuth(auth);
        ParkingProviderUpdateApplication application = updateApplicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Parking provider application not found")
        );
        if (!application.getStatus().equals(ParkingProviderApplicationStatus.PENDING)) {
            throw new StatusException("Request has been processed as " + application.getStatus().name());
        }
        application.setStatus(ParkingProviderApplicationStatus.REJECTED);
        application.setUpdatedAt(LocalDateTime.now());
        application.setAdmin(admin);
        application.setRejectionReason(reason);
        updateApplicationRepository.save(application);
    }

    public List<ParkingProviderAdminListCard> getParkingProviderAdminListCard(ParkingProviderApplicationStatus status) {
        List<ParkingProviderApplication> applications = applicationRepository.findAllByStatusAndIsLatestTrue(status);
        if (applications.isEmpty()) {
            throw new NotFoundException("No applications found by " + status);
        }
        return applications.stream()
                .map(application ->
                        ParkingProviderAdminListCard.fromParkingProviderApplication(application))
                .toList();
    }

    public ParkingProviderViewDto getParkingProviderViewDto(Authentication auth) {
        User user = getApprovedUserByAuth(auth);
        ParkingProviderProfile profile = profileRepository.findByUser(user).orElseThrow(
                () -> new NotFoundException("Parking provider profile not found")
        );
        return ParkingProviderViewDto.fromParkingProviderProfile(profile);
    }

    public ParkingProviderApplicationAdminViewDto getParkingProviderApplicationAdminViewDto( Long applicationId) {
        ParkingProviderApplication application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new NotFoundException("Parking provider application not found")
        );
        return ParkingProviderApplicationAdminViewDto.from(application);


    }
    public ParkingProviderApplicationStatusResponse getParkingProviderUpdateApplicationStatus(Authentication auth) {
        User provider = getApprovedUserByAuth(auth);
        ParkingProviderProfile profile = profileRepository.findByUser(provider).orElseThrow(
                ()->  new NotFoundException("Parking provider profile not found")
        );
       return updateApplicationRepository.findTopByProviderProfileOrderByCreatedAtDesc(profile)
               .map(application -> switch (application.getStatus()) {

                   case PENDING, APPROVED -> new ParkingProviderApplicationStatusResponse(
                           application.getStatus(),
                           null
                   );

                   case REJECTED -> new ParkingProviderApplicationStatusResponse(
                           application.getStatus(),
                           application.getRejectionReason()
                   );
                   case NOT_APPLIED -> throw new StatusException("NOT_APPLIED shall not be ever applied");

               })
               .orElse(
                       new ParkingProviderApplicationStatusResponse(
                               ParkingProviderApplicationStatus.NOT_APPLIED,
                               null
                       )
               );

    }

}
