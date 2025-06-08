package com.example.transaction_service.service.common.client.implementation;

import com.example.transaction_service.configuration.client.WebClientConfiguration;
import com.example.transaction_service.service.common.client.WebClientService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebClientServiceManager implements WebClientService<ClassicHttpRequest, CloseableHttpResponse> {
    @Override
    public CloseableHttpResponse sendRequest(ClassicHttpRequest httpRequest) throws IOException {
        try (CloseableHttpClient client = WebClientConfiguration.httpClient()) {
            return client.execute(httpRequest, CloseableHttpResponse::adapt);
        }
    }
}
