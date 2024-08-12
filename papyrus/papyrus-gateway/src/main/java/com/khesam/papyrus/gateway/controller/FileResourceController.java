package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.common.client.FileInfoRestClient;
import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import com.khesam.papyrus.common.dto.AssignFileToSignerCommand;
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
@RequestMapping(value = "/files"/*, produces = "application/vnd.api.v1+json"*/)
public class FileResourceController {

    private final FileNameValidator fileNameValidator;
    private final FileStorageService fileStorageService;
    private final FileInfoRestClient fileInfoRestClient;

    @Autowired
    public FileResourceController(
            FileNameValidator fileNameValidator,
            FileStorageService fileStorageService,
            FileInfoRestClient fileInfoRestClient
    ) {
        this.fileNameValidator = fileNameValidator;
        this.fileStorageService = fileStorageService;
        this.fileInfoRestClient = fileInfoRestClient;
    }

    @PostMapping
    ResponseEntity<Void> postFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("file-name") String fileName
    ) {
        fileNameValidator.validate(fileName);
        try {
            fileStorageService.store(fileName, file.getInputStream());

            String fileId = fileInfoRestClient.createFileInfo(
                    new SaveFileInfoCommand(
                            fileName, file.getContentType(), file.getSize()
                    )
            );

            return ResponseEntity.created(URI.create(fileId)).build();
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @PutMapping("/{file-id}:assign")
    ResponseEntity<Void> assignFileToSigner(
            @PathVariable("file-id") String fileId,
            @RequestBody AssignFileToSignerCommand request
    ) {
        return ResponseEntity.noContent().build();
    }
}
