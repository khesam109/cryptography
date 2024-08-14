package com.khesam.papyrus.gateway.service;

import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface StorageService {

    String store(String filename, InputStream inputStream);
    Resource loadAsResource(String filename);
}
