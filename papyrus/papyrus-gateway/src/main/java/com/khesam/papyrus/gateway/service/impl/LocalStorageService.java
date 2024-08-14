package com.khesam.papyrus.gateway.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.khesam.papyrus.gateway.config.StorageProperties;
import com.khesam.papyrus.gateway.exception.StorageException;
import com.khesam.papyrus.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
//https://spring.io/guides/gs/uploading-files
public class LocalStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public LocalStorageService(StorageProperties storageProperties) {
        if (storageProperties.location().trim().isEmpty()) {
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(storageProperties.location());
    }

    @Override
    public String store(String filename, InputStream inputStream) {
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

        return destinationFile.toString();
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }
}
