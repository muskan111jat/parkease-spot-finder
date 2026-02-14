package com.example.ParkEase20.controller;

import com.example.ParkEase20.dto.GeoCoordinatesDTO;
import com.example.ParkEase20.service.FileStorageService;
import com.example.ParkEase20.service.MailService;
import com.example.ParkEase20.util.AddressCleaner;
import com.example.ParkEase20.util.OpenStreetMapGeoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health")
    public ResponseEntity<?> getHealth(){
        return ResponseEntity.ok("OK");
    }


}
