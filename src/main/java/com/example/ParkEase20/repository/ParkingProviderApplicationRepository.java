package com.example.ParkEase20.repository;

import com.example.ParkEase20.entity.ParkingProviderApplication;
import com.example.ParkEase20.entity.ParkingProviderApplicationStatus;
import com.example.ParkEase20.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ParkingProviderApplicationRepository extends JpaRepository<ParkingProviderApplication, Long> {
    Optional<ParkingProviderApplication> findById(Long id);
    Optional<ParkingProviderApplication>findByUser(User user);
    int countByStatus(ParkingProviderApplicationStatus parkingProviderApplicationStatus);
    Optional<ParkingProviderApplication>findByUserAndIsLatestTrue(User user);
    int countByStatusAndIsLatestTrue(ParkingProviderApplicationStatus parkingProviderApplicationStatus);

    List<ParkingProviderApplication> findAllByStatusAndIsLatestTrue(ParkingProviderApplicationStatus parkingProviderApplicationStatus);


}
