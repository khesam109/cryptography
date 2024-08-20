package com.khesam.papyrus.core.service;

import com.khesam.papyrus.common.exception.ResourceNotFoundException;
import com.khesam.papyrus.core.repository.FileInfoRepository;
import com.khesam.papyrus.core.repository.FileInfoSignerRepository;
import com.khesam.papyrus.core.repository.SignerRepository;
import com.khesam.papyrus.core.service.impl.FileAssignmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileAssignmentServiceImplTest {

    @Mock private FileInfoRepository fileInfoRepository;
    @Mock private SignerRepository signerRepository;
    @Mock private FileInfoSignerRepository fileInfoSignerRepository;

    @InjectMocks
    private FileAssignmentServiceImpl fileAssignmentService;

    @Test
    void testFileAssignmentWhenFileInfoIdIsNotExist() {
        when(fileInfoRepository.existsById(any())).thenReturn(false);

        RuntimeException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> fileAssignmentService.assignFileToSigner(UUID.randomUUID().toString(), 1)
        );

        assertEquals("provided fileId is not exist", exception.getMessage());
    }

    @Test
    void testFileAssignmentWhenSignerIdIsNotExist() {
        when(fileInfoRepository.existsById(any())).thenReturn(true);
        when(signerRepository.existsById(any())).thenReturn(false);

        RuntimeException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> fileAssignmentService.assignFileToSigner(UUID.randomUUID().toString(), 1)
        );

        assertEquals("provided signerId is not exist", exception.getMessage());
    }

    @Test
    void testGetFileSignersWhenFileInfoIdIsNotExist() {
        when(fileInfoRepository.existsById(any())).thenReturn(false);

        RuntimeException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> fileAssignmentService.getFileSignerId(UUID.randomUUID().toString())
        );

        assertEquals("provided fileId is not exist", exception.getMessage());
    }

    @Test
    void testGetFileSignersWhenAssignmentNotOccurred() {
        when(fileInfoRepository.existsById(any())).thenReturn(true);
        when(fileInfoSignerRepository.findByFileId(any())).thenReturn(Optional.empty());

        assertEquals(-1, fileAssignmentService.getFileSignerId(
                UUID.randomUUID().toString())
        );
    }
}
