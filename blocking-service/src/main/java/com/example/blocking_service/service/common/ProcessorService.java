package com.example.blocking_service.service.common;

import java.util.List;

public interface ProcessorService <T>{
    void unblock(T processingObject);
    void unblock(List<T> processingObjectList);
}
