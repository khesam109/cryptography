package com.khesam.papyrus.core.service;

import com.khesam.papyrus.core.repository.entity.FileInfoEntity;

import java.util.List;
import java.util.UUID;

public interface FileInfoService {

    UUID saveFileInfo(String fileName, String contentType, long size, String path);
    List<FileInfoEntity> getAllFilesInfo();
    FileInfoEntity getFileInfo(UUID id);
}
