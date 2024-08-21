package com.khesam.papyrus.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SignDocumentCommand(

        @NotNull
        @NotEmpty
        @JsonProperty("encoded-signature")
        String encodedSignature,

        @JsonProperty("timestamp")
        @JsonFormat(pattern = "yyy-MM-dd HH:mm")
        LocalDateTime timestamp
) {
}
