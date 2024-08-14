package com.khesam.papyrus.gateway.di;

import com.khesam.papyrus.common.client.FileInfoRestClient;
import com.khesam.papyrus.common.client.SignatureRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientProvider {

    @Bean
    FileInfoRestClient provideFileInfoRestClient() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8585/papyrus/core/api/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(FileInfoRestClient.class);
    }

    @Bean
    SignatureRestClient provideSignatureRestClient() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8585/papyrus/core/api/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(SignatureRestClient.class);
    }
}
