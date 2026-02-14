package com.example.ParkEase20.service;
import com.example.ParkEase20.repository.ParkingAreaRepository;
import com.example.ParkEase20.repository.ParkingProviderApplicationRepository;
import com.example.ParkEase20.repository.ParkingProviderProfileRepository;
import com.example.ParkEase20.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParkingAreaRepository parkingAreaRepository;

    @Autowired
    private ParkingProviderProfileRepository parkingProviderProfileRepository;

    @Autowired
    private ParkingProviderApplicationRepository parkingProviderApplicationRepository;

    public AdminDashboardDto getAdminDashboard() {
        AdminDashboardDto dto = new AdminDashboardDto();

    }
}

