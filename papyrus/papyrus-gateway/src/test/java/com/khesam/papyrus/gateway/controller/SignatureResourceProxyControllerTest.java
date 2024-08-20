package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.common.client.SignatureRestClient;
import com.khesam.papyrus.common.dto.GetTbsResponseData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignatureResourceProxyController.class)
class SignatureResourceProxyControllerTest {

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
    void getPdfDigestSerializationTest() throws Exception {
        GetTbsResponseData response = new GetTbsResponseData(
                "fake-encoded-tbs",
                "fake-signature-algorithm"
        );

        when(signatureRestClient.getPdfDigest(any())).thenReturn(response);

        mockMvc.perform(get("/signature/{file-id}/tbs", UUID.randomUUID().toString()))
                .andExpect(jsonPath("$.encoded-tbs").isNotEmpty())
                .andExpect(jsonPath("$.encoded-tbs").value("fake-encoded-tbs"))
                .andExpect(jsonPath("$.signature-algorithm").isNotEmpty())
                .andExpect(jsonPath("$.signature-algorithm").value("fake-signature-algorithm"));
    }
}
