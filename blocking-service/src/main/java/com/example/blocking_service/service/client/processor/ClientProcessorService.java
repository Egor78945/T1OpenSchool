package com.example.blocking_service.service.client.processor;

import com.example.blocking_service.model.client.entity.Client;
import com.example.blocking_service.service.common.ProcessorService;

import java.util.List;

public interface ClientProcessorService <C extends Client> extends ProcessorService<C> {
    @Override
    void unblock(C processingObject);
    @Override
    void unblock(List<C> processingObjectList);
}
