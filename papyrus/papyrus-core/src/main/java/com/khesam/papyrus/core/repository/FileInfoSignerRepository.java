package com.khesam.papyrus.core.repository;

import com.khesam.papyrus.core.repository.entity.FileInfoSignerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileInfoSignerRepository extends JpaRepository<FileInfoSignerEntity, String> {

    Optional<FileInfoSignerEntity> findByFileId(String fileId);
}
