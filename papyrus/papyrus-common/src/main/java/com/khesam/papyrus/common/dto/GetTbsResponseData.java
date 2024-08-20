package com.khesam.papyrus.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetTbsResponseData(
        @JsonProperty("encoded-tbs") String encodedTbs,
        @JsonProperty("signature-algorithm") String signatureAlgorithm
) {
}
