package com.khesam.papyrus.core.service.impl;

import com.khesam.papyrus.core.repository.FileInfoRepository;
import com.khesam.papyrus.core.repository.FileInfoSignerRepository;
import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
import com.khesam.papyrus.core.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;
    private final FileInfoSignerRepository fileInfoSignerRepository;

    @Autowired
    public FileInfoServiceImpl(
            FileInfoRepository fileInfoRepository,
            FileInfoSignerRepository fileInfoSignerRepository
    ) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileInfoSignerRepository = fileInfoSignerRepository;
    }

    @Override
    public UUID saveFileInfo(String fileName, String contentType, long size, String path) {
        UUID id = UUID.randomUUID();
        FileInfoEntity fileInfoEntity = new FileInfoEntity();
        fileInfoEntity.setId(id.toString());
        fileInfoEntity.setName(fileName);
        fileInfoEntity.setContentType(contentType);
        fileInfoEntity.setSize(size);
        fileInfoEntity.setPath(path);

        fileInfoRepository.save(fileInfoEntity);

        return id;
    }

    @Override
    public List<FileInfoEntity> getAllFilesInfo() {
        return fileInfoRepository.findAll();
    }

    @Override
    public FileInfoEntity getFileInfo(UUID id) {
        return fileInfoRepository.findById(id.toString()).orElseThrow();
    }
}
