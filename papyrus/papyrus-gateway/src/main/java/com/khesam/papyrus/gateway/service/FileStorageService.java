package com.khesam.papyrus.gateway.service;

import java.io.InputStream;

public interface FileStorageService {

    void store(String filename, InputStream inputStream);
}
