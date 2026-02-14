package com.example.ParkEase20.initializer;

import com.example.ParkEase20.entity.User;
import com.example.ParkEase20.entity.UserRole;
import com.example.ParkEase20.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${app.admin.email}")
    private String email;
    @Value("${app.admin.password}")
    private String password;
    @PostConstruct
    public void  createAdmin(){
        if(!userRepository.existsByEmail(email)){
            User user=new User();
            user.setUsername("Administrator");
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setEmail(email);
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
        }

    }
}
