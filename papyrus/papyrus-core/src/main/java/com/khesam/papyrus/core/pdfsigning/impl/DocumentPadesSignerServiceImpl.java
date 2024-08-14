package com.khesam.papyrus.core.pdfsigning.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.*;
import com.itextpdf.signatures.cms.CMSContainer;
import com.khesam.papyrus.core.pdfsigning.DocumentSignerService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;

@Service
class DocumentPadesSignerServiceImpl implements DocumentSignerService {

    @Override
    public void buildSignedDocument(
            String preparedDocumentPath,
            String signedDocumentPath,
            String signatureFieldName,
            byte[] signature,
            String hashAlgorithm,
            String signatureAlgorithm,
            ISignatureMechanismParams signingParams
    ) {
        CMSContainer cmsContainer = extractCmsContainer(preparedDocumentPath, signatureFieldName);

        // Second phase of signing.
        PadesTwoPhaseSigningHelper helper = new PadesTwoPhaseSigningHelper();
//        helper.setTrustedCertificates(getTrustedStore());
//        helper.setTSAClient(getTsaClient());
//        helper.setOcspClient(getOcspClient());
//        helper.setCrlClient(getCrlClient());

        try (PdfReader reader = new PdfReader(preparedDocumentPath);
             OutputStream outputStream = Files.newOutputStream(Paths.get(signedDocumentPath))
        ) {
            // An external signature implementation that starts from an existing signature.
            IExternalSignature externalSignature = new ExternalSignatureProvider(
                    signature, hashAlgorithm, signatureAlgorithm, signingParams
            );

            helper.signCMSContainerWithBaselineBProfile(externalSignature, reader, outputStream, signatureFieldName, cmsContainer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extract the prepared CMS container from the prepared document.
     */
    private CMSContainer extractCmsContainer(String preparedDocumentPath, String signatureFieldName) {
        try (PdfReader reader = new PdfReader(preparedDocumentPath);
             PdfDocument document = new PdfDocument(reader)) {
            SignatureUtil signatureUtil = new SignatureUtil(document);
            PdfSignature preparedSignature = signatureUtil.getSignature(signatureFieldName);
            return new CMSContainer(preparedSignature.getContents().getValueBytes());
        } catch (IOException | CertificateException | CRLException e) {
            throw new RuntimeException(e);
        }
    }
}
