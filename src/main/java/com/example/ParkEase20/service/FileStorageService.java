package com.example.ParkEase20.service;

import com.example.ParkEase20.entity.ImageDirectoryType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String saveFile(MultipartFile file, Long ownerId, ImageDirectoryType type) throws IOException;
    Resource loadResource(String storedPath) throws IOException;
    void deleteFile(String storedPath) ;
}
