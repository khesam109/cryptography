package com.khesam.papyrus.core.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SIGNER", schema = "PAPYRUS")
public class SignerEntity {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "WET_SIGNATURE_PATH")
    private String wetSignaturePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getWetSignaturePath() {
        return wetSignaturePath;
    }

    public void setWetSignaturePath(String wetSignaturePath) {
        this.wetSignaturePath = wetSignaturePath;
    }
}
