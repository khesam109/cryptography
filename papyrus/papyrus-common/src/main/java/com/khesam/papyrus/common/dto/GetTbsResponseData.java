package com.khesam.papyrus.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record GetTbsResponseData(
        @JsonProperty("encoded-tbs")
        String encodedTbs,

        @JsonProperty("signature-algorithm")
        String signatureAlgorithm,

        @JsonProperty("timestamp")
        @JsonFormat(pattern = "yyy-MM-dd HH:mm")
        LocalDateTime timestamp
) {
}
