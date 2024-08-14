package com.khesam.papyrus.core.service;

public interface SignatureService {

    String getFileDigest(String fileId);
    void addSignatureToFile(String fileId, String signature);
}
