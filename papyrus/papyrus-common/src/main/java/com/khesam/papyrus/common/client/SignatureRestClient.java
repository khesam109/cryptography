package com.khesam.papyrus.common.client;

import com.khesam.papyrus.common.dto.GetTbsResponseData;
import com.khesam.papyrus.common.dto.SignDocumentCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface SignatureRestClient {

    @GetExchange("/signature/{file-id}/tbs")
    GetTbsResponseData getPdfDigest(
            @PathVariable("file-id") String fileId
    );

    @PostExchange("/signature/{file-id}/signature")
    void createSignedDocument(
            @PathVariable("file-id") String fileId,
            @RequestBody SignDocumentCommand request
    );
}
