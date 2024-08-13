package com.khesam.papyrus.core.service.impl;

import com.khesam.papyrus.core.repository.FileInfoRepository;
import com.khesam.papyrus.core.repository.FileInfoSignerRepository;
import com.khesam.papyrus.core.repository.SingerRepository;
import com.khesam.papyrus.core.repository.entity.FileInfoSignerEntity;
import com.khesam.papyrus.core.service.FileAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FileAssignmentServiceImpl implements FileAssignmentService {

    private final FileInfoRepository fileInfoRepository;
    private final SingerRepository singerRepository;
    private final FileInfoSignerRepository fileInfoSignerRepository;

    @Autowired
    public FileAssignmentServiceImpl(
            FileInfoRepository fileInfoRepository,
            SingerRepository singerRepository,
            FileInfoSignerRepository fileInfoSignerRepository
    ) {
        this.fileInfoRepository = fileInfoRepository;
        this.singerRepository = singerRepository;
        this.fileInfoSignerRepository = fileInfoSignerRepository;
    }

    @Override
    public void assignFileToSigner(String fileId, int signerId) {
        if (!fileInfoRepository.existsById(fileId)) {
            throw new IllegalArgumentException("provided fileId is not exist");
        }

        if (!singerRepository.existsById(signerId)) {
            throw new IllegalArgumentException("provided signerId is not exist");
        }

        FileInfoSignerEntity fileInfoSignerEntity = new FileInfoSignerEntity();
        fileInfoSignerEntity.setId(UUID.randomUUID().toString());
        fileInfoSignerEntity.setFileId(fileId);
        fileInfoSignerEntity.setSingerId(signerId);

        fileInfoSignerRepository.save(fileInfoSignerEntity);
    }

    @Override
    public int getFileSignerId(String fileId) {
        if (!fileInfoRepository.existsById(fileId)) {
            throw new IllegalArgumentException("provided fileId is not exist");
        }
        return fileInfoSignerRepository
                .findByFileId(fileId)
                .map(FileInfoSignerEntity::getSingerId)
                .orElse(-1);
    }
}
