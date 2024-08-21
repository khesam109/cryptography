package com.khesam.papyrus.gateway.controller.dto;

import com.khesam.papyrus.common.dto.GetTbsResponseData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class GetTbsResponseDataTest {

    @Autowired
    private JacksonTester<GetTbsResponseData> jacksonTester;

    @Test
    void serializeGetTbsResponseDataTimestampSuccessTest() throws IOException {
        GetTbsResponseData response = new GetTbsResponseData(
                "fake-encoded-tbs",
                "fake-signature-algorithm",
                LocalDateTime.of(2222, 2, 22, 2, 22, 22)
        );

        JsonContent<GetTbsResponseData> json = jacksonTester.write(response);
        assertThat(json).extractingJsonPathStringValue("$.timestamp").isEqualTo("2222-02-22 02:22");
    }
}
