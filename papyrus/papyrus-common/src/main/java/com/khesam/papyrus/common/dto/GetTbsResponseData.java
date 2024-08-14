package com.khesam.papyrus.common.dto;

public record GetTbsResponseData(
        String encodedTbs,
        String signatureAlgorithm
) {
}
