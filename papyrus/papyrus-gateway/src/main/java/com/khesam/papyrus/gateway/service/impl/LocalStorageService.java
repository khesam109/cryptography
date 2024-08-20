package com.khesam.papyrus.gateway.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.khesam.papyrus.common.exception.IllegalInputException;
import com.khesam.papyrus.common.exception.ResourceNotFoundException;
import com.khesam.papyrus.gateway.exception.StorageException;
import com.khesam.papyrus.gateway.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public LocalStorageService(
            @Qualifier("file-storage-root-location") Path rootLocation
    ) {
        this.rootLocation = rootLocation;
    }

    @Override
    public String store(String filename, InputStream inputStream) {
        Path destinationFile = this.rootLocation.resolve(
                Paths.get(filename)
        ).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new IllegalInputException("Cannot store file outside current directory.");
        }

        try (inputStream) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException.IOStorageException("Failed to store file.", e);
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
                throw new ResourceNotFoundException("Could not find file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageException.IOStorageException("Could not read file: " + filename, e);
        }
    }
}
