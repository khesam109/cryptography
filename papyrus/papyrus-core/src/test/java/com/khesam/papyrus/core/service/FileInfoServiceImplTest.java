package com.khesam.papyrus.core.service;

import com.khesam.papyrus.core.exception.NotFoundException;
import com.khesam.papyrus.core.repository.FileInfoRepository;
import com.khesam.papyrus.core.service.impl.FileInfoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileInfoServiceImplTest {

    @Mock
    private FileInfoRepository fileInfoRepository;

    @InjectMocks
    private FileInfoServiceImpl fileInfoService;

    @Test
    void testGetFileInfoWhenIdIsNotExist() {
        when(fileInfoRepository.findById(any())).thenReturn(Optional.empty());

        UUID fileInfoId = UUID.randomUUID();
        RuntimeException exception = assertThrows(
                NotFoundException.class,
                () -> fileInfoService.getFileInfo(fileInfoId)
        );

        assertEquals("No file found with id: " + fileInfoId, exception.getMessage());
    }
}
