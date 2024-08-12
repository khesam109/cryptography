package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.gateway.exception.StorageException;
import com.khesam.papyrus.gateway.service.FileStorageService;
import com.khesam.papyrus.gateway.validator.FileNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/files")
public class FileResourceController {

    private final FileNameValidator fileNameValidator;
    private final FileStorageService fileStorageService;

    @Autowired
    public FileResourceController(
            FileNameValidator fileNameValidator,
            FileStorageService fileStorageService
    ) {
        this.fileNameValidator = fileNameValidator;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResponseEntity<Void> postFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("file-name") String fileName
    ) {
        fileNameValidator.validate(fileName);
        try {
            fileStorageService.store(fileName, file.getInputStream());
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
        return ResponseEntity.created(URI.create("salam")).build();
    }
}
