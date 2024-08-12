package com.khesam.papyrus.common.client;

import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface FileInfoRestClient {

    @PostExchange("/files-info")
    String createFileInfo(@RequestBody SaveFileInfoCommand command);
}
