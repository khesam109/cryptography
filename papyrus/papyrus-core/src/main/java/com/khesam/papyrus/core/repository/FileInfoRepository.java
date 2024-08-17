package com.khesam.papyrus.core.repository;

import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfoEntity, String> {

}
