package com.example.ParkEase20.service;

import com.example.ParkEase20.config.CustomUserDetails;
import com.example.ParkEase20.dto.AuthResponse;
import com.example.ParkEase20.dto.LoginRequest;
import com.example.ParkEase20.dto.RegisterRequest;
import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.exceptions.AlreadyFoundException;
import com.example.ParkEase20.exceptions.InvalidCredentialsException;
import com.example.ParkEase20.exceptions.NotFoundException;
import com.example.ParkEase20.repository.UserRepository;
import com.example.ParkEase20.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OtpService otpService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public void registerUser(RegisterRequest registerRequest) {
        User user=RegisterRequest.toUser(registerRequest);

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AlreadyFoundException("email already found");
        }
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AlreadyFoundException("username already found");
        }
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );
        CustomUserDetails userDetails=(CustomUserDetails) auth.getPrincipal();
        String token=jwtUtil.generateToken(userDetails);
        return new AuthResponse(token,userDetails.getEmail(),userDetails.getAuthorities().stream() .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER"));
    }
    public void resetPasswordViaOtp(String email,String newPassword) {
        User user=userRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("User not found")
        );
        if(!otpService.isOtpVerified(email)){
            throw new InvalidCredentialsException("OTP has Expired");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
