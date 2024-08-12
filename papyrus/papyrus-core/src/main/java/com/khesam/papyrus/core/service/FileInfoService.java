package com.khesam.papyrus.core.service;

import com.khesam.papyrus.core.repository.entity.FileInfoEntity;

import java.util.UUID;

public interface FileInfoService {

    UUID saveFileInfo(String fileName, String contentType, long size);
    FileInfoEntity getFileInfo(UUID id);
}
