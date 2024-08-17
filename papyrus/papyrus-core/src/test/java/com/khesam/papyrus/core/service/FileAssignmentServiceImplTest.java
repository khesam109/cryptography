package com.khesam.papyrus.core.service;

import com.khesam.papyrus.core.repository.FileInfoRepository;
import com.khesam.papyrus.core.repository.FileInfoSignerRepository;
import com.khesam.papyrus.core.repository.SignerRepository;
import com.khesam.papyrus.core.service.impl.FileAssignmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileAssignmentServiceImplTest {

    @Mock private FileInfoRepository fileInfoRepository;
    @Mock private SignerRepository signerRepository;
    @Mock private FileInfoSignerRepository fileInfoSignerRepository;

    @InjectMocks
    private FileAssignmentService fileAssignmentService = new FileAssignmentServiceImpl(
            fileInfoRepository,
            signerRepository,
            fileInfoSignerRepository
    );

    @Test
    void test() {
        when(fileInfoRepository.existsById(any())).thenReturn(false);

        when(fileAssignmentService.getFileSignerId())
    }
}
