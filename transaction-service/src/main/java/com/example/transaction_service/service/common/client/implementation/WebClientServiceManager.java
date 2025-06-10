package com.example.transaction_service.service.common.client.implementation;

import com.example.transaction_service.configuration.client.WebClientConfiguration;
import com.example.transaction_service.service.common.client.WebClientService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebClientServiceManager implements WebClientService<ClassicHttpRequest, String> {
    @Override
    public String sendRequest(ClassicHttpRequest httpRequest) throws IOException {
        try (CloseableHttpClient client = WebClientConfiguration.httpClient()) {
            try(CloseableHttpResponse c = client.execute(httpRequest)) {
                return EntityUtils.toString(c.getEntity());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
