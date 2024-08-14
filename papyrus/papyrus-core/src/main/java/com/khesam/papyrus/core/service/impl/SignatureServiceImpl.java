package com.khesam.papyrus.core.service.impl;

import com.khesam.papyrus.common.utils.StringUtils;
import com.khesam.papyrus.core.pdfsigning.DocumentSignaturePreparationService;
import com.khesam.papyrus.core.pdfsigning.DocumentSignerService;
import com.khesam.papyrus.core.repository.FileInfoSignerRepository;
import com.khesam.papyrus.core.repository.entity.FileInfoEntity;
import com.khesam.papyrus.core.service.FileInfoService;
import com.khesam.papyrus.core.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class SignatureServiceImpl implements SignatureService {

    private static final String SIGNATURE_FIELD_NAME = "sig_name";


    private static final String WORKING_FILE_POSTFIX = ".working";
    private static final String PREPARED_FILE_POSTFIX = "-prep.pdf";
    private static final String SIGNED_FILE_POSTFIX = "-final.pdf";


    private final FileInfoService fileInfoService;
    private final FileInfoSignerRepository fileInfoSignerRepository;
    private final DocumentSignaturePreparationService documentSignaturePreparationService;
    private final DocumentSignerService documentSignerService;

    @Autowired
    public SignatureServiceImpl(
            FileInfoService fileInfoService,
            FileInfoSignerRepository fileInfoSignerRepository,
            DocumentSignaturePreparationService documentSignaturePreparationService,
            DocumentSignerService documentSignerService
    ) {
        this.fileInfoService = fileInfoService;
        this.fileInfoSignerRepository = fileInfoSignerRepository;
        this.documentSignaturePreparationService = documentSignaturePreparationService;
        this.documentSignerService = documentSignerService;
    }

    @Override
    public String getFileDigest(String fileId) {
        FileInfoEntity fileInfoEntity = fileInfoService.getFileInfo(UUID.fromString(fileId));
        int signerId = fileInfoSignerRepository.findByFileId(fileId).orElseThrow().getSingerId();

        byte[] tbs = documentSignaturePreparationService.prepareDocument(
                fileInfoEntity.getPath(),
                buildWorkingFilePath(fileInfoEntity.getPath()),
                buildPreparedFilePath(fileInfoEntity.getPath()),
                "SHA256",
                signerId,
                SIGNATURE_FIELD_NAME
        );

        return Base64.getEncoder().encodeToString(tbs);
    }

    @Override
    public void addSignatureToFile(String fileId, String signature) {
        FileInfoEntity fileInfoEntity = fileInfoService.getFileInfo(UUID.fromString(fileId));

        documentSignerService.buildSignedDocument(
                buildPreparedFilePath(fileInfoEntity.getPath()),
                buildDestinationFilePath(fileInfoEntity.getPath()),
                SIGNATURE_FIELD_NAME,
                Base64.getDecoder().decode(signature.getBytes(StandardCharsets.UTF_8)),
                "SHA256",
                "SHA256WithRSA",
                null
        );
    }

    private String buildWorkingFilePath(String filePath) {
        return StringUtils.removePostfix(filePath, ".pdf")
                + PREPARED_FILE_POSTFIX + WORKING_FILE_POSTFIX;
    }

    private String buildPreparedFilePath(String filePath) {
        return StringUtils.removePostfix(filePath, ".pdf")
                + PREPARED_FILE_POSTFIX;
    }

    private String buildDestinationFilePath(String filePath) {
        return StringUtils.removePostfix(filePath, ".pdf")
                + SIGNED_FILE_POSTFIX;
    }
}
