package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.ParkingProviderAdminListCard;
import com.example.ParkEase20.dto.ParkingProviderApplicationAdminViewDto;
import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import com.example.ParkEase20.service.ParkingProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ParkingProviderService parkingProviderService;

    public AdminController(ParkingProviderService parkingProviderService) {
        this.parkingProviderService = parkingProviderService;
    }

    @PutMapping("/parking-providers/{id}/approve")
    public ResponseEntity<String> approveParkingProvider(Authentication auth, @PathVariable Long id) {
        parkingProviderService.approveParkingProvider(auth, id);
        return ResponseEntity.ok().body("Parking provider approved successfully");

    }

    @PutMapping("/parking-providers/{id}/reject")
    public ResponseEntity<String> rejectParkingProvider(Authentication auth, @PathVariable Long id,@RequestParam String reason) {
        parkingProviderService.denyParkingProvider(auth,id,reason);
        return ResponseEntity.ok().body("Parking provider rejected successfully");

    }

//    @GetMapping("/parking-providers/approved")
//    public ResponseEntity<List<ParkingProviderAdminListCard>> getParkingProviderApprovedCards() {
//        List<ParkingProviderAdminListCard> cards = parkingProviderService.getParkingProviderAdminListCard(ParkingProviderApplicationStatus.APPROVED);
//        return ResponseEntity.ok().body(cards);
//    }
//    @GetMapping("/parking-providers/rejected")
//    public ResponseEntity<List<ParkingProviderAdminListCard>> getRejectedParkingProviderCards() {
//        List<ParkingProviderAdminListCard> cards = parkingProviderService.getParkingProviderAdminListCard(ParkingProviderApplicationStatus.REJECTED);
//        return ResponseEntity.ok().body(cards);
//    }
//    @GetMapping("/parking-providers/pending")
//    public ResponseEntity<List<ParkingProviderAdminListCard>> getPendingParkingProviderCards() {
//        List<ParkingProviderAdminListCard> cards = parkingProviderService.getParkingProviderAdminListCard(ParkingProviderApplicationStatus.PENDING);
//        return ResponseEntity.ok().body(cards);
//    }
    @GetMapping("/parking-providers")//"/parking-providers?status=PENDING OR APPROVED OR REJECTED"
    public ResponseEntity<List<ParkingProviderAdminListCard>> getParkingProviderStatusCards(@RequestParam ParkingProviderApplicationStatus status) {
        List<ParkingProviderAdminListCard> cards = parkingProviderService.getParkingProviderAdminListCard(status);
        return ResponseEntity.ok().body(cards);
    }
    @GetMapping("/parking-providers/{id}/view")
    public ResponseEntity<ParkingProviderApplicationAdminViewDto> getParkingProviderView(@PathVariable Long id) {
        ParkingProviderApplicationAdminViewDto dto=parkingProviderService.getParkingProviderApplicationAdminViewDto(id);
        return ResponseEntity.ok().body(dto);
    }


}
