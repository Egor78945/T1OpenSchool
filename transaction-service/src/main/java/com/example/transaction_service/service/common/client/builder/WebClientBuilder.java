package com.example.transaction_service.service.common.client.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URISyntaxException;
import java.util.Map;

public class WebClientBuilder {
    public static HttpGet build(String uri, Map<String, String> parameters) {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);

            for (Map.Entry<String, String> par : parameters.entrySet()) {
                uriBuilder.addParameter(par.getKey(), par.getValue());
            }
            return new HttpGet(uriBuilder.build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static HttpPut build(String uri, HttpEntity httpEntity) {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);

            HttpPut httpPut = new HttpPut(uriBuilder.build());
            httpPut.setEntity(httpEntity);

            return httpPut;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
