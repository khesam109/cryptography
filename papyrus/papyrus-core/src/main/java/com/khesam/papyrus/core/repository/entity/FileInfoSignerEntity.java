package com.khesam.papyrus.core.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FILE_INFO_SIGNER", schema = "PAPYRUS")
public class FileInfoSignerEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "FILE_ID")
    private String fileId;

    @Column(name = "SIGNER_ID")
    private int singerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }
}
