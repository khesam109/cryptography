package com.khesam.papyrus.core.controller;

import com.khesam.papyrus.common.dto.AssignFileToSignerCommand;
import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
import com.khesam.papyrus.core.service.FileAssignmentService;
import com.khesam.papyrus.core.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/files-info", produces = "application/vnd.api.v1+json")
public class FileInfoResourceController {

    private final FileInfoService fileInfoService;
    private final FileAssignmentService fileAssignmentService;

    @Autowired
    public FileInfoResourceController(
            FileInfoService fileInfoService,
            FileAssignmentService fileAssignmentService
    ) {
        this.fileInfoService = fileInfoService;
        this.fileAssignmentService = fileAssignmentService;
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

    @PutMapping("/{file-id}/assign")
    ResponseEntity<Void> assignFileToSigner(
            @PathVariable("file-id") String fileId,
            @RequestBody AssignFileToSignerCommand request
    ) {
        fileAssignmentService.assignFileToSigner(fileId, request.signerId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{file-id}")
    ResponseEntity<FileInfoEntity> getId(@PathVariable("file-id") String fileId) {
        return ResponseEntity.ok(
                fileInfoService.getFileInfo(UUID.fromString(fileId))
        );
    }

    @GetMapping("/{file-id}/signer-id")
    ResponseEntity<Integer> getSignerId(@PathVariable("file-id") String fileId) {
        return ResponseEntity.ok(
                fileAssignmentService.getFileSignerId(fileId)
        );
    }
}
