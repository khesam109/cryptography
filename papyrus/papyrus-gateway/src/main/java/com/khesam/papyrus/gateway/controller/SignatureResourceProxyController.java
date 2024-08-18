package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.common.client.SignatureRestClient;
import com.khesam.papyrus.common.dto.GetTbsResponseData;
import com.khesam.papyrus.common.dto.SignDocumentCommand;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/signature", produces = "application/vnd.api.v1+json")
public class SignatureResourceProxyController {

    private final SignatureRestClient signatureRestClient;

    @Autowired
    public SignatureResourceProxyController(
            SignatureRestClient signatureRestClient
    ) {
        this.signatureRestClient = signatureRestClient;
    }

    @GetMapping("/{file-id}/tbs")
    ResponseEntity<GetTbsResponseData> getPdfDigest(
            @PathVariable("file-id") String fileId
    ) {

        return ResponseEntity.ok(
                signatureRestClient.getPdfDigest(fileId)
        );
    }

    @PostMapping("/{file-id}/signature")
    ResponseEntity<Void> createSignedDocument(
            @PathVariable("file-id") String fileId,
            @RequestBody @Valid SignDocumentCommand request
    ) {
        signatureRestClient.createSignedDocument(fileId, request);

        return ResponseEntity.noContent().build();
    }
}
