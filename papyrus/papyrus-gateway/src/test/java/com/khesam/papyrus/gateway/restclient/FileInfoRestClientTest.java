package com.khesam.papyrus.gateway.restclient;

import com.khesam.papyrus.common.client.FileInfoRestClient;
import com.khesam.papyrus.common.domain.FileInfo;
import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RestClientTest
public class FileInfoRestClientTest {

    private MockWebServer mockWebServer;
    @Autowired FileInfoRestClient fileInfoRestClient;

    @BeforeEach
    void setMockWebServer() {
        mockWebServer = new MockWebServer();
        mockWebServer.url("http://localhost:8585/papyrus/core/api");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8585/papyrus/core/api"));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        this.fileInfoRestClient = factory.createClient(FileInfoRestClient.class);
    }

//    @Test
//    void test() throws InterruptedException {
//        String id = UUID.randomUUID().toString();
//        mockWebServer.enqueue(
//                new MockResponse().setResponseCode(200)
//                        .setBody(id)
//        );
//
//        fileInfoRestClient.createFileInfo(new SaveFileInfoCommand("", "", 1L, ""));
//
//        RecordedRequest request = mockWebServer.takeRequest();
//
//        assertThat(request.getMethod()).isEqualTo("POST");
//    }
}
