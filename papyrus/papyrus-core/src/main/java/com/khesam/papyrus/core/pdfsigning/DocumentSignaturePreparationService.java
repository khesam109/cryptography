package com.khesam.papyrus.core.pdfsigning;

public interface DocumentSignaturePreparationService {

    byte[] prepareDocument(
            String originDocumentPath,
            String workingDocumentPath,
            String preparedDocumentPath,
            String digestAlgorithm,
            int signerId,
            String signatureFieldName
    );
}
