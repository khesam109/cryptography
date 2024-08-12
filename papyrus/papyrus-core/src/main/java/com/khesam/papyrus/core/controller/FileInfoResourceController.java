package com.khesam.papyrus.core.controller;

import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
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

    @Autowired
    public FileInfoResourceController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
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

    @GetMapping("/{file-id}")
    ResponseEntity<FileInfoEntity> getId(@PathVariable("file-id") String fileId) {
        return ResponseEntity.ok(
                fileInfoService.getFileInfo(UUID.fromString(fileId))
        );
    }
}
