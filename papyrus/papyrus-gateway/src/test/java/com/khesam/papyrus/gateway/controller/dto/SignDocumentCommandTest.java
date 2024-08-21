package com.khesam.papyrus.gateway.controller.dto;

import com.khesam.papyrus.common.dto.SignDocumentCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class SignDocumentCommandTest {

    @Autowired
    private JacksonTester<SignDocumentCommand> jacksonTester;

    @Test
    void deserializeSignDocumentCommandTimeStampSuccessTest() throws IOException {
        String json = "{\"encoded-signature\": \"fake-encoded-signature\", \"timestamp\": \"2222-02-22 02:22\"}";

        SignDocumentCommand request = jacksonTester.parseObject(json);

        assertThat(request.timestamp()).isEqualTo(LocalDateTime.of(2222, 2, 22, 2, 22));
    }
}
