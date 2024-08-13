package com.khesam.papyrus.core.controller;

import com.khesam.papyrus.core.repository.SingerRepository;
import com.khesam.papyrus.core.repository.entity.SignerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/signers", produces = "application/vnd.api.v1+json")
public class SignerResourceController {

    private final SingerRepository singerRepository;

    @Autowired
    public SignerResourceController(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    @GetMapping
    ResponseEntity<List<SignerEntity>> getSigners() {
        return ResponseEntity.ok(
                singerRepository.findAll()
        );
    }
}
