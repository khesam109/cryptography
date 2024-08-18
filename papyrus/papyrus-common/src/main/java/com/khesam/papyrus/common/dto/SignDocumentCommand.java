package com.khesam.papyrus.common.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SignDocumentCommand(

        @NotNull
        @NotEmpty
        String encodedSignature
) {
}
