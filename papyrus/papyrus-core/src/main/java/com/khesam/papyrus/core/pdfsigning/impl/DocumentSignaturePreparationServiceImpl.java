package com.khesam.papyrus.core.pdfsigning.impl;

import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.PadesTwoPhaseSigningHelper;
import com.itextpdf.signatures.SignerProperties;
import com.itextpdf.signatures.cms.CMSContainer;
import com.khesam.papyrus.core.pdfsigning.CertificateFetcherService;
import com.khesam.papyrus.core.pdfsigning.DocumentSignaturePreparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.util.Arrays;

@Service
class DocumentSignaturePreparationServiceImpl implements DocumentSignaturePreparationService {

    private final DocumentFormatterService documentFormatterService;
    private final CertificateFetcherService certificateFetcherService;
    private final SignerPropertiesService signerPropertiesService;


    @Autowired
    public DocumentSignaturePreparationServiceImpl(
            DocumentFormatterService documentFormatterService,
            CertificateFetcherService certificateFetcherService,
            SignerPropertiesService signerPropertiesService
    ) {
        this.documentFormatterService = documentFormatterService;
        this.certificateFetcherService = certificateFetcherService;
        this.signerPropertiesService = signerPropertiesService;
    }

    @Override
    public byte[] prepareDocument(
            String originDocumentPath, String workingDocumentPath, String preparedDocumentPath,
            String digestAlgorithm,
            int signerId,
            String signatureFieldName
    ) {
        try (PdfReader reader = new PdfReader(originDocumentPath);
             OutputStream outputStream = Files.newOutputStream(
                     Paths.get(workingDocumentPath)
             )
        ) {
            Certificate[] certificates = getCertificateChain(signerId);
            SignerProperties signerProperties = getSignerProperties(signerId, signatureFieldName);

            PadesTwoPhaseSigningHelper padesTwoPhaseSigningHelper = new PadesTwoPhaseSigningHelper();
            padesTwoPhaseSigningHelper.setEstimatedSize(99999); //TODO: should be considered!!
            padesTwoPhaseSigningHelper.setTrustedCertificates(Arrays.asList(certificates));
            CMSContainer cmsContainer = padesTwoPhaseSigningHelper.createCMSContainerWithoutSignature(
                    certificates,
                    digestAlgorithm,
                    reader,
                    outputStream,
                    signerProperties
            );
            documentFormatterService.addCmsContainerToDocument(
                    workingDocumentPath,
                    preparedDocumentPath,
                    cmsContainer,
                    signerProperties.getFieldName()
            );
            return cmsContainer.getSerializedSignedAttributes();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private Certificate[] getCertificateChain(int signerId) {
        return certificateFetcherService.getCertificates(signerId);
    }

    private SignerProperties getSignerProperties(int signerId, String signatureFieldName) {
        return signerPropertiesService.getSignerProperties(signerId, signatureFieldName);
    }
}
