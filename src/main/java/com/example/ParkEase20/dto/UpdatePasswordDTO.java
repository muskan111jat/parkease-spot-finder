package com.example.ParkEase20.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdatePasswordDTO {
    @NotBlank(message = "old password must be required")
    private String oldPassword;
    @NotBlank(message = "new password is required")
    private String newPassword;

    public UpdatePasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UpdatePasswordDTO() {

    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
