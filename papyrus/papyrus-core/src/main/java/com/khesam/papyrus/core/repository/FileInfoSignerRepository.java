package com.khesam.papyrus.core.repository;

import com.khesam.papyrus.core.repository.entity.FileInfoSignerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileInfoSignerRepository extends JpaRepository<FileInfoSignerEntity, String> {

    Optional<FileInfoSignerEntity> findByFileId(String fileId);
}
