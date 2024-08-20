package com.khesam.papyrus.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssignFileToSignerCommand(

        @NotNull
        @Positive
        Integer signerId
) {
}
