package com.khesam.papyrus.common.dto;

public record SaveFileInfoCommand(
        String name,
        String contentType,
        long size,
        String path
) {
}
