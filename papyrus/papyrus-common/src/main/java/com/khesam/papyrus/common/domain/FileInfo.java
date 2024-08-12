package com.khesam.papyrus.common.domain;

import java.util.UUID;

public record FileInfo(
        String id,
        String name,
        String contentType,
        long size,
        UUID signerId

) {
}
