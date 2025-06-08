package com.example.transaction_service.configuration.client;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfiguration {
    public static CloseableHttpClient httpClient() {
        return HttpClients.createDefault();
    }
}
