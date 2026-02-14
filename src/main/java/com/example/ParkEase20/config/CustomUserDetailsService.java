package com.example.ParkEase20.config;

import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;
        if (login == null || login.isBlank()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        if (login.contains("@")) {
            user = userRepository.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("Invalid login-credential or password"));

        }else{
            user = userRepository.findByUsername(login).orElseThrow(() -> new UsernameNotFoundException("Invalid login-credential or password"));
        }
        return new  CustomUserDetails(user);
    }
}
