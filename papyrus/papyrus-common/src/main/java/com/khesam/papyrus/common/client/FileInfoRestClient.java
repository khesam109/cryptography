package com.khesam.papyrus.common.client;

import com.khesam.papyrus.common.domain.FileInfo;
import com.khesam.papyrus.common.dto.AssignFileToSignerCommand;
import com.khesam.papyrus.common.dto.SaveFileInfoCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange(url = "/files-info")
public interface FileInfoRestClient {

    @PostExchange
    String createFileInfo(
            @RequestBody SaveFileInfoCommand command
    );

    @GetExchange
    List<FileInfo> getAllFilesInfo();

    @GetExchange("/{file-id}")
    FileInfo getFileInfo(
            @PathVariable("file-id") String fileId
    );

    @PutExchange("/{file-id}/assign")
    void assignFileToSigner(
            @PathVariable("file-id") String fileId,
            @RequestBody AssignFileToSignerCommand request
    );
}
