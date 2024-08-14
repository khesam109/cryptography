package com.khesam.papyrus.core.pdfsigning.impl;

import com.itextpdf.commons.utils.FileUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.PdfTwoPhaseSigner;
import com.itextpdf.signatures.cms.CMSContainer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

@Service
class DocumentFormatterService {

    /**
     * This is optional, the CMS container can be stored in any way.
     * Adding the prepared CMS container to the prepared document
     */
    public void addCmsContainerToDocument(
            String workingDocumentPath,
            String preparedPath,
            CMSContainer cmsContainer,
            String signatureFieldName
    ) {
        try (PdfReader reader = new PdfReader(workingDocumentPath);
             PdfDocument doc = new PdfDocument(reader);
             OutputStream outputStream = Files.newOutputStream(Paths.get(preparedPath))
        ) {
            PdfTwoPhaseSigner.addSignatureToPreparedDocument(doc, signatureFieldName, outputStream, cmsContainer);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        } finally {
            //TODO: should we delete working file in finally?!
            FileUtil.deleteFile(new File(workingDocumentPath));
        }
    }
}
