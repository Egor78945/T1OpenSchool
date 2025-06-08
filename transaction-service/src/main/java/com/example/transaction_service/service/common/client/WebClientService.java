package com.example.transaction_service.service.common.client;

import java.io.IOException;

public interface WebClientService<REQ, RES> {
    RES sendRequest(REQ httpRequest) throws IOException;
}
