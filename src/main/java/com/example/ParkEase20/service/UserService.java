package com.example.ParkEase20.service;

import com.example.ParkEase20.config.CustomUserDetails;
import com.example.ParkEase20.dto.UpdatePasswordDTO;
import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.exceptions.InvalidCredentialsException;
import com.example.ParkEase20.exceptions.NotFoundException;
import com.example.ParkEase20.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private User getUserByAuthentication(Authentication auth) {
        CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
        User user = userRepository.findById(details.getUserId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        return user;
    }

    public void updatePassword(Authentication auth, UpdatePasswordDTO dto) {
        User user = getUserByAuthentication(auth);

        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new InvalidCredentialsException("new password must be different from old password");
        }
        if (!bCryptPasswordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password");

        }
        user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

    }
}
