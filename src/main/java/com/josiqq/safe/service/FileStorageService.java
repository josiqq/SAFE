package com.josiqq.safe.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.storage.location}")
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        try {
            rootLocation = Paths.get(storageLocation);
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la ubicación de almacenamiento de archivos", e);
        }
    }

    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("No se puede guardar un archivo vacío.");
        }
        
        // Generar un nombre de archivo único para evitar colisiones
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        try (InputStream inputStream = file.getInputStream()) {
            Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename))
                                     .normalize().toAbsolutePath();
            
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            
            return uniqueFilename; // Devolvemos el nombre único para guardarlo en la DB
        } catch (IOException e) {
            throw new RuntimeException("Fallo al guardar el archivo.", e);
        }
    }
}