package com.khesam.papyrus.common.dto;

public record FileInfo(
        String id,
        String name,
        String contentType,
        long size

) {
}
