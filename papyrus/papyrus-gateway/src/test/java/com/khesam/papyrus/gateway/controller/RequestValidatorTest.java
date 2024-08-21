package com.khesam.papyrus.gateway.controller;

import com.khesam.papyrus.common.dto.AssignFileToSignerCommand;
import com.khesam.papyrus.common.dto.SignDocumentCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RequestValidatorTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void assignFileToSignerSignerIdIsNullTest() {
        AssignFileToSignerCommand request = new AssignFileToSignerCommand(null);
        Set<ConstraintViolation<AssignFileToSignerCommand>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(
                ConstraintViolation::getMessage
        ).containsExactly(
                "must not be null"
        );
    }

    @Test
    void assignFileToSignerSignerIdIsZeroTest() throws Exception {
        AssignFileToSignerCommand request = new AssignFileToSignerCommand(0);
        Set<ConstraintViolation<AssignFileToSignerCommand>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(
                ConstraintViolation::getMessage
        ).containsExactly(
                "must be greater than 0"
        );
    }

    @Test
    void assignFileToSignerSignerIdIsNegativeTest() throws Exception {
        AssignFileToSignerCommand request = new AssignFileToSignerCommand(-1);
        Set<ConstraintViolation<AssignFileToSignerCommand>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(
                ConstraintViolation::getMessage
        ).containsExactly(
                "must be greater than 0"
        );
    }

    @Test
    void createSignedDocumentEncodedSignatureIsNullTest() {
        SignDocumentCommand request = new SignDocumentCommand(null, LocalDateTime.now());
        Set<ConstraintViolation<SignDocumentCommand>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(
                ConstraintViolation::getMessage
        ).containsExactlyInAnyOrder(
                "must not be null", "must not be empty"
        );
    }

    @Test
    void createSignedDocumentEncodedSignatureIsEmptyTest() {
        SignDocumentCommand request = new SignDocumentCommand("", LocalDateTime.now());
        Set<ConstraintViolation<SignDocumentCommand>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(
                ConstraintViolation::getMessage
        ).containsExactly("must not be empty");
    }
}
