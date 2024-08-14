package com.khesam.papyrus.core.pdfsigning.impl;

import com.itextpdf.signatures.IExternalSignature;
import com.itextpdf.signatures.ISignatureMechanismParams;

import java.util.Arrays;

class ExternalSignatureProvider implements IExternalSignature {

    private final byte[] signature;
    private final String digestAlgorithm;
    private final String signatureAlgorithm;
    private final ISignatureMechanismParams signatureMechanismParameters;

    public ExternalSignatureProvider(byte[] signature, String digestAlgorithm, String signatureAlgorithm,
                                     ISignatureMechanismParams signatureMechanismParameters) {
        this.signature = Arrays.copyOf(signature, signature.length);
        this.digestAlgorithm = digestAlgorithm;
        this.signatureAlgorithm = signatureAlgorithm;
        this.signatureMechanismParameters = signatureMechanismParameters;
    }

    @Override
    public String getDigestAlgorithmName() {
        return digestAlgorithm;
    }

    @Override
    public String getSignatureAlgorithmName() {
        return signatureAlgorithm;
    }

    @Override
    public ISignatureMechanismParams getSignatureMechanismParameters() {
        return signatureMechanismParameters;
    }

    @Override
    public byte[] sign(byte[] message) {
        return signature;
    }
}
