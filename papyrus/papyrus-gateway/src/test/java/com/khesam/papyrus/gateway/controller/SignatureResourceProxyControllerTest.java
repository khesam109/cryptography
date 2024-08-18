package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.common.client.SignatureRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignatureResourceProxyController.class)
public class SignatureResourceProxyControllerTest {

    @MockBean
    private SignatureRestClient signatureRestClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPdfDigestSuccessResponseTest() {

    }

    @Test
    void createSignedDocumentSuccessResponseTest() throws Exception {
        doNothing().when(signatureRestClient).createSignedDocument(any(), any());

        mockMvc.perform(
                post("/signature/{file-id}/signature", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"encodedSignature\": \"faked-encoded-signature\"}")
        ).andExpect(
                status().isNoContent()
        );
    }

    @Test
    void createSignedDocumentEncodedSignatureIsNullTest() throws Exception {
        mockMvc.perform(
                post("/signature/{file-id}/signature", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        ).andExpect(
                status().isBadRequest()
        );
    }

    @Test
    void createSignedDocumentEncodedSignatureIsEmptyTest() throws Exception {
        mockMvc.perform(
                post("/signature/{file-id}/signature", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"encodedSignature\": \"\"}")
        ).andExpect(
                status().isBadRequest()
        );
    }
}
