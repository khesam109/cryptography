package com.khesam.papyrus.core.service.impl;

import com.khesam.papyrus.common.domain.FileInfo;
import com.khesam.papyrus.core.repository.FileInfoRepository;
import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
import com.khesam.papyrus.core.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;

    @Autowired
    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    @Override
    public UUID saveFileInfo(String fileName, String contentType, long size) {
        UUID id = UUID.randomUUID();
        FileInfoEntity fileInfoEntity = new FileInfoEntity();
        fileInfoEntity.setId(id.toString());
        fileInfoEntity.setName(fileName);
        fileInfoEntity.setContentType(contentType);
        fileInfoEntity.setSize(size);

        fileInfoRepository.save(fileInfoEntity);

        return id;
    }

    @Override
    public FileInfoEntity getFileInfo(UUID id) {
        return fileInfoRepository.findById(id.toString()).orElseThrow();
    }
}
