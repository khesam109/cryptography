package com.khesam.papyrus.core.converter;

import com.khesam.papyrus.common.domain.FileInfo;
import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileInfoConverter {

    public FileInfo fromFileInfoEntity(final FileInfoEntity fileInfoEntity) {
        return new FileInfo(
                fileInfoEntity.getId(),
                fileInfoEntity.getName(),
                fileInfoEntity.getContentType(),
                fileInfoEntity.getSize()
        );
    }

    public List<FileInfo> fromFileInfoEntityList(final List<FileInfoEntity> fileInfoEntityList) {
        return fileInfoEntityList.stream().map(this::fromFileInfoEntity).toList();
    }
}
