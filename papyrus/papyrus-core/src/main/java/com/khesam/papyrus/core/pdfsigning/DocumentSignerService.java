package com.khesam.papyrus.core.pdfsigning;

import com.itextpdf.signatures.ISignatureMechanismParams;

public interface DocumentSignerService {

    void buildSignedDocument(
            String preparedDocumentPath,
            String signedDocumentPath,
            String signatureFieldName,
            byte[] signature,
            String hashAlgorithm,
            String signatureAlgorithm,
            ISignatureMechanismParams signingParams
    );
}
