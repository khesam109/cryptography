package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.common.client.FileInfoRestClient;
import com.khesam.papyrus.common.domain.FileInfo;
import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import com.khesam.papyrus.common.dto.AssignFileToSignerCommand;
import com.khesam.papyrus.gateway.exception.StorageException;
import com.khesam.papyrus.gateway.service.StorageService;
import com.khesam.papyrus.gateway.validator.FileNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/files", produces = "application/vnd.api.v1+json")
public class FileResourceProxyController {

    private final FileNameValidator fileNameValidator;
    private final StorageService storageService;
    private final FileInfoRestClient fileInfoRestClient;

    @Autowired
    public FileResourceProxyController(
            FileNameValidator fileNameValidator,
            StorageService storageService,
            FileInfoRestClient fileInfoRestClient
    ) {
        this.fileNameValidator = fileNameValidator;
        this.storageService = storageService;
        this.fileInfoRestClient = fileInfoRestClient;
    }

    @PostMapping
    ResponseEntity<Void> postFile(
            @RequestPart("file") MultipartFile file,
            @RequestParam("file-name") String fileName
    ) {
        fileNameValidator.validate(fileName);
        try {
            String path = storageService.store(fileName, file.getInputStream());

            String fileId = fileInfoRestClient.createFileInfo(
                    new SaveFileInfoCommand(
                            fileName, file.getContentType(), file.getSize(), path
                    )
            );

            return ResponseEntity.created(URI.create(fileId)).build();
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @GetMapping("/info")
    ResponseEntity<List<FileInfo>> getAllFilesInfo() {
        return ResponseEntity.ok(
                fileInfoRestClient.getAllFilesInfo()
        );
    }

    @GetMapping(value = "/{file-id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<Resource> downloadFile(@PathVariable("file-id") String fileId) {
        FileInfo fileInfo = fileInfoRestClient.getFileInfo(fileId);
        Resource file = storageService.loadAsResource(fileInfo.name());

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\""
        ).body(file);
    }

    @PutMapping("/{file-id}/assign")
    ResponseEntity<Void> assignFileToSigner(
            @PathVariable("file-id") String fileId,
            @RequestBody AssignFileToSignerCommand request
    ) {
        fileInfoRestClient.assignFileToSigner(fileId, request);
        return ResponseEntity.noContent().build();
    }
}
