package com.example.ParkEase20.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class AdminDashBoardDto {
    @Getter
    private Long totalUsers;
    private Long totalParkingProviders;// use ParkingProviderProfile  repository
    private Long totalApprovedParkingProviders;// use ParkingProviderApplication  repository
    private Long totalPendingParkingProviders;// use ParkingProviderApplication  repository
    private Long totalRejectedParkingProviders;// use ParkingProviderApplication  repository
    private Long totalActiveParkingAreas;//user Parrking Area repo
    private Long totalParkingAreas;// use Parking Area repo
    private Long totalPayments;// i have not created these so set 0
    private Long totalBookings;// i have not created it yet so set 0 for now
    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalParkingProviders() {
        return totalParkingProviders;
    }

    public void setTotalParkingProviders(Long totalParkingProviders) {
        this.totalParkingProviders = totalParkingProviders;
    }

    public Long getTotalApprovedParkingProviders() {
        return totalApprovedParkingProviders;
    }

    public void setTotalApprovedParkingProviders(Long totalApprovedParkingProviders) {
        this.totalApprovedParkingProviders = totalApprovedParkingProviders;
    }

    public Long getTotalPendingParkingProviders() {
        return totalPendingParkingProviders;
    }

    public void setTotalPendingParkingProviders(Long totalPendingParkingProviders) {
        this.totalPendingParkingProviders = totalPendingParkingProviders;
    }

    public Long getTotalRejectedParkingProviders() {
        return totalRejectedParkingProviders;
    }

    public void setTotalRejectedParkingProviders(Long totalRejectedParkingProviders) {
        this.totalRejectedParkingProviders = totalRejectedParkingProviders;
    }

    public Long getTotalActiveParkingAreas() {
        return totalActiveParkingAreas;
    }

    public void setTotalActiveParkingAreas(Long totalActiveParkingAreas) {
        this.totalActiveParkingAreas = totalActiveParkingAreas;
    }

    public Long getTotalParkingAreas() {
        return totalParkingAreas;
    }

    public void setTotalParkingAreas(Long totalParkingAreas) {
        this.totalParkingAreas = totalParkingAreas;
    }

    public Long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }
}
