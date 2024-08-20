package com.khesam.papyrus.gateway.validator;

import com.khesam.papyrus.gateway.validator.impl.PdfFileNameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PdfFileNameValidatorTest {

    @InjectMocks
    private PdfFileNameValidator pdfFileNameValidator;


    @Test
    void testFileNameIsNull() {
        RuntimeException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pdfFileNameValidator.validate(null)
        );

        assertEquals("File name is null or empty", exception.getMessage());
    }

    @Test
    void testFileNameIsEmpty() {
        RuntimeException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pdfFileNameValidator.validate("")
        );

        assertEquals("File name is null or empty", exception.getMessage());
    }

    @Test
    void testFileNameNotEndsWithPdf() {
        RuntimeException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pdfFileNameValidator.validate("this.is.not.a.pdf.file")
        );

        assertEquals("File extension is not PDF", exception.getMessage());
    }

    @Test
    void testFileNameContainsDoubleDots() {
        RuntimeException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pdfFileNameValidator.validate("double..dot.pdf")
        );

        assertEquals("File name contains invalid characters", exception.getMessage());
    }

    @Test
    void testValidPdfFileName() {
        assertDoesNotThrow(
                () -> pdfFileNameValidator.validate("valid-file.name.pdf")
        );
    }
}
