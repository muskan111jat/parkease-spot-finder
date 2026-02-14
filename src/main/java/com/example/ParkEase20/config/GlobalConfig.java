package com.example.ParkEase20.config;

import com.example.ParkEase20.service.FileStorageService;
import com.example.ParkEase20.service.LocalStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GlobalConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public FileStorageService fileStorageService(@Value("${storage.type}") String type) {
        if (type.equalsIgnoreCase("local")) {
            return new LocalStorageService();
        }else{
            return null;
        }
    }
}
