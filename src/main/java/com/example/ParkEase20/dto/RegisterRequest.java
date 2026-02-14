package com.example.ParkEase20.dto;

import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest{
    @NotBlank(message="username cannot be empty")
    String  username;
    @NotBlank(message = "email is required")
    @Email(message = "Invalid email format")
    String email;
    @NotBlank(message = "password is required")
    String password;
    RegisterRequest() {}

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static User toUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setUserRole(UserRole.USER);
        return user;
    }
}
