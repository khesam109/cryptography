package com.khesam.papyrus.core.pdfsigning.impl;

import com.khesam.papyrus.core.pdfsigning.CertificateFetcherService;
import com.khesam.papyrus.core.repository.SignerRepository;
import com.khesam.papyrus.core.repository.entity.SignerEntity;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

@Service
class CertificateFetcherFromKeystoreService implements CertificateFetcherService {


    private final SignerRepository signerRepository;

    @PostConstruct
    public void init() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Autowired
    public CertificateFetcherFromKeystoreService(SignerRepository signerRepository) {
        this.signerRepository = signerRepository;
    }

    @Override
    public Certificate[] getCertificates(int signerId) {
        SignerEntity signerEntity = signerRepository.findById(signerId).orElseThrow();

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(Files.newInputStream(Paths.get(signerEntity.getCertificatePath())), "salamsalam".toCharArray());
            String alias = keyStore.aliases().nextElement();
            return new Certificate[]{keyStore.getCertificate(alias)};
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
