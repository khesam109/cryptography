package com.khesam.papyrus.gateway.validator.impl;

import com.khesam.papyrus.gateway.validator.FileNameValidator;
import org.springframework.stereotype.Component;

@Component
public class PdfFileNameValidator implements FileNameValidator {

    @Override
    public void validate(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name is null or empty");
        }

        if (!fileName.endsWith(".pdf")) {
            throw new IllegalArgumentException("File extension is not PDF");
        }

        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            throw new IllegalArgumentException("File name contains invalid characters");
        }
    }
}
