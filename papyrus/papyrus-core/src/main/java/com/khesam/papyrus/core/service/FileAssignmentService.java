package com.khesam.papyrus.core.service;

public interface FileAssignmentService {

    void assignFileToSigner(String fileId, int signerId);
    int getFileSignerId(String fileId);

}
