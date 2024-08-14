package com.khesam.papyrus.core.controller;

import com.khesam.papyrus.common.dto.GetTbsResponseData;
import com.khesam.papyrus.common.dto.SignDocumentCommand;
import com.khesam.papyrus.core.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/signature", produces = "application/vnd.api.v1+json")
public class SignatureResourceController {

    private final SignatureService signatureService;

    @Autowired
    public SignatureResourceController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @GetMapping("/{file-id}/tbs")
    ResponseEntity<GetTbsResponseData> getPdfDigest(
            @PathVariable("file-id") String fileId
    ) {
        return ResponseEntity.ok(
                new GetTbsResponseData(
                        signatureService.getFileDigest(fileId),
                        "SHA256WithRSA"
                )
        );
    }

    @PostMapping("/{file-id}/signature")
    ResponseEntity<Void> createSignedDocument(
            @PathVariable("file-id") String fileId,
            @RequestBody SignDocumentCommand request
    ) {
        signatureService.addSignatureToFile(fileId, request.encodedSignature());

        return ResponseEntity.noContent().build();
    }
}
