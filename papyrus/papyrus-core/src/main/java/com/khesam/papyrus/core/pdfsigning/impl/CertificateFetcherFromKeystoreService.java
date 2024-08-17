package com.khesam.papyrus.core.pdfsigning.impl;

import com.khesam.papyrus.core.pdfsigning.CertificateFetcherService;
import com.khesam.papyrus.core.repository.SignerRepository;
import com.khesam.papyrus.core.repository.entity.SignerEntity;
import jakarta.annotation.PostConstruct;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
            keyStore.load(getClass().getClassLoader().getResourceAsStream(signerEntity.getCertificatePath()), "salamsalam".toCharArray());

            List<Certificate> certificates = new ArrayList<>();
            Enumeration<String> enumeration = keyStore.aliases();
            while(enumeration.hasMoreElements()) {
                certificates.add(
                        keyStore.getCertificate(
                                enumeration.nextElement()
                        )
                );
            }

            return getOrderedChain(certificates).toArray(new Certificate[0]);
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //At the moment just reverse the list, but can be more intelligent
    private List<Certificate> getOrderedChain(List<Certificate> certificates) {
        final int last = certificates.size() - 1;
        return IntStream.rangeClosed(0, last)
                .map(i -> (last - i))
                .mapToObj(certificates::get)
                .collect(Collectors.toList());
    }
}
