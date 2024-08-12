package com.khesam.papyrus.gateway.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.khesam.papyrus.gateway.config.StorageProperties;
import com.khesam.papyrus.gateway.exception.StorageException;
import com.khesam.papyrus.gateway.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
//https://spring.io/guides/gs/uploading-files
public class LocalFileStorageService implements FileStorageService {

    private final Path rootLocation;

    @Autowired
    public LocalFileStorageService(StorageProperties storageProperties) {
        if (storageProperties.location().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(storageProperties.location());
    }

    @Override
    public void store(String filename, InputStream inputStream) {
        Path destinationFile = this.rootLocation.resolve(
                Paths.get(filename)
        ).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new StorageException("Cannot store file outside current directory.");
        }

        try (inputStream) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }
}
