package com.example.ParkEase20.service;

import com.example.ParkEase20.entity.ImageDirectoryType;
import com.example.ParkEase20.exceptions.FileStorageException;
import com.example.ParkEase20.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class LocalStorageService implements FileStorageService {
    @Value("${file.upload.base-path}")
    private String basePath;

    @Override
    public String saveFile(MultipartFile file, Long ownerId, ImageDirectoryType type)  {
        if (file == null || file.getSize() == 0) {
            return null;
        }

        if (ownerId == null) {
            throw new NotFoundException("owner id is required");
        }

        if (basePath == null || basePath.isBlank()) {
            throw new IllegalStateException("basePath is not configured");
        }

        try {
            Path path = Paths.get(
                    basePath,
                    type.getFolderName(),
                    ownerId.toString(),
                    "images"
            );

            Files.createDirectories(path);

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) {
                throw new NotFoundException("file name is empty");
            }

            String cleanFileName =
                    Paths.get(originalFileName).getFileName().toString();

            String extension = "";
            int dotIndex = cleanFileName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = cleanFileName.substring(dotIndex);
            }

            String newFileName = UUID.randomUUID() + extension;
            Path filePath = path.resolve(newFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return filePath.toString();

        } catch (IOException e) {
            throw new FileStorageException(
                    "Failed to save file for ownerId=" + ownerId +
                            ", type=" + type,
                    e
            );
        }

    }

    @Override
    public Resource loadResource(String storedPath) throws IOException {
        if (storedPath == null || storedPath.isEmpty()) {
            throw new NotFoundException("file stored path is empty");
        }
        Path path=Paths.get(storedPath);
        if (!Files.exists(path)) {
            throw new NotFoundException("file stored path does not exist");
        }
        return new FileSystemResource(path);
    }

    @Override
    public void deleteFile(String storedPath)  {
        if (storedPath == null || storedPath.isBlank()) {
            throw new NotFoundException("file stored path is empty");
        }
        try{
            Files.deleteIfExists(Paths.get(storedPath));
        }catch (Exception e){
            throw new FileStorageException(
                    "Failed to delete file for storedPath=" + storedPath,
                    e);
        }


    }
}
