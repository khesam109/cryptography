package com.khesam.papyrus.common.dto;

public record PostFileCommand(
        String fileName,
        String signerId
) {
}
