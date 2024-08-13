package com.khesam.papyrus.common.client;

import com.khesam.papyrus.common.dto.AssignFileToSignerCommand;
import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface FileInfoRestClient {

    @PostExchange("/files-info")
    String createFileInfo(
            @RequestBody SaveFileInfoCommand command
    );

    @PutExchange("/files-info/{file-id}/assign")
    void assignFileToSigner(
            @PathVariable("file-id") String fileId,
            @RequestBody AssignFileToSignerCommand request
    );
}
