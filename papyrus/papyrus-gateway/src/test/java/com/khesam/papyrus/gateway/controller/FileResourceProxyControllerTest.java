package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.common.client.FileInfoRestClient;
import com.khesam.papyrus.gateway.service.StorageService;
import com.khesam.papyrus.gateway.validator.FileNameValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileResourceProxyController.class)
class FileResourceProxyControllerTest {

    @MockBean
    private FileNameValidator fileNameValidator;
    @MockBean
    private StorageService storageService;
    @MockBean
    private FileInfoRestClient fileInfoRestClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postFileSuccessResponseTest() throws Exception {
        UUID fileId = UUID.randomUUID();
        doNothing().when(fileNameValidator).validate(any());
        when(storageService.store(any(), any())).thenReturn("/fake/destination");
        when(fileInfoRestClient.createFileInfo(any())).thenReturn(fileId.toString());

        MockMultipartFile file = new MockMultipartFile(
                "file",
                fileId.toString(),
                "pdf",
                "dummy".getBytes()
        );

        mockMvc.perform(
                multipart("/files")
                        .file(file)
                        .param("file-name", "test")
        ).andExpect(
                status().isCreated()
        ).andExpect(
                header().string(
                        HttpHeaders.LOCATION,
                        "/papyrus/gateway/api/files/" + fileId
                )
        );
    }

    @Test
    void assignFileToSignerSuccessResponseTest() throws Exception {
        doNothing().when(fileInfoRestClient).assignFileToSigner(any(), any());

        mockMvc.perform(
                put("/files/${file-id}/assign", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"signerId\": 1}")

        ).andExpect(
                status().isNoContent()
        );
    }

    @Test
    void assignFileToSignerSignerIdIsNullTest() throws Exception {
        mockMvc.perform(
                put("/files/${file-id}/assign", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")

        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    void postFileIOExceptionTest() throws Exception {
        MockMultipartFile file = new FaultyMultipartFile(
                "file",
                UUID.randomUUID().toString(),
                "pdf",
                "dummy".getBytes()
        );

        mockMvc.perform(
                multipart("/files")
                        .file(file)
                        .param("file-name", "test")
        ).andExpect(
                status().isInternalServerError()
        );
    }

    private static class FaultyMultipartFile extends MockMultipartFile {

        public FaultyMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
            super(name, originalFilename, contentType, content);
        }


        @Override
        public InputStream getInputStream() throws IOException {
            throw new IOException();
        }
    }
}
