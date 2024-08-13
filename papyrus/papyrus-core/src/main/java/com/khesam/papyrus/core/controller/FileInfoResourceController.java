package com.khesam.papyrus.core.controller;

import com.khesam.papyrus.common.domain.FileInfo;
import com.khesam.papyrus.common.dto.AssignFileToSignerCommand;
import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import com.khesam.papyrus.core.converter.FileInfoConverter;
import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
import com.khesam.papyrus.core.service.FileAssignmentService;
import com.khesam.papyrus.core.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/files-info", produces = "application/vnd.api.v1+json")
public class FileInfoResourceController {

    private final FileInfoService fileInfoService;
    private final FileAssignmentService fileAssignmentService;
    private final FileInfoConverter fileInfoConverter;

    @Autowired
    public FileInfoResourceController(
            FileInfoService fileInfoService,
            FileAssignmentService fileAssignmentService,
            FileInfoConverter fileInfoConverter
    ) {
        this.fileInfoService = fileInfoService;
        this.fileAssignmentService = fileAssignmentService;
        this.fileInfoConverter = fileInfoConverter;
    }

    @PostMapping
    ResponseEntity<String> createFileInfo(
            @RequestBody SaveFileInfoCommand command
    ) {
        UUID id = fileInfoService.saveFileInfo(
                command.name(),
                command.contentType(),
                command.size()
        );

        return ResponseEntity.ok(
                id.toString()
        );
    }

    @GetMapping
    ResponseEntity<List<FileInfo>> getAllFilesInfo() {
        return ResponseEntity.ok(
                fileInfoConverter.fromFileInfoEntityList(
                        fileInfoService.getAllFilesInfo()
                )
        );
    }

    @GetMapping("/{file-id}")
    ResponseEntity<FileInfo> getFileInfo(@PathVariable("file-id") String fileId) {
        return ResponseEntity.ok(
                fileInfoConverter.fromFileInfoEntity(
                        fileInfoService.getFileInfo(UUID.fromString(fileId))
                )
        );
    }

    @GetMapping("/{file-id}/signer-id")
    ResponseEntity<Integer> getSignerId(@PathVariable("file-id") String fileId) {
        return ResponseEntity.ok(
                fileAssignmentService.getFileSignerId(fileId)
        );
    }

    @PutMapping("/{file-id}/assign")
    ResponseEntity<Void> assignFileToSigner(
            @PathVariable("file-id") String fileId,
            @RequestBody AssignFileToSignerCommand request
    ) {
        fileAssignmentService.assignFileToSigner(fileId, request.signerId());
        return ResponseEntity.noContent().build();
    }
}
