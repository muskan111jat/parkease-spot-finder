package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.ParkingProviderApplicationRequest;
import com.example.ParkEase20.dto.UpdatePasswordDTO;
import com.example.ParkEase20.service.ParkingProviderService;
import com.example.ParkEase20.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ParkingProviderService parkingProviderService;

    public UserController(UserService userService, ParkingProviderService parkingProviderService) {
        this.userService = userService;
        this.parkingProviderService = parkingProviderService;
    }

    @PatchMapping("/update-password")
    public ResponseEntity updatePassword(
            Authentication auth,
            @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        userService.updatePassword(auth, updatePasswordDTO);
        return ResponseEntity.ok().body("Password updated successfully");
    }

    @GetMapping("/get-application-status")
    public ResponseEntity getApplicationStatus(Authentication auth) {
        return ResponseEntity.ok().body(parkingProviderService.getParkingProviderApplicationStatus(auth));
    }

    @PostMapping(value = "/apply",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> applyForParkingProvider(Authentication auth,
                                                     @ModelAttribute ParkingProviderApplicationRequest parkingProviderApplicationRequest) {
        parkingProviderService.applyParkingProviderApplication(auth, parkingProviderApplicationRequest);
        return ResponseEntity.ok().body("Parking provider application successfully applied");

    }

}
