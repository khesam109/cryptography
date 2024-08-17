package com.khesam.papyrus.core.pdfsigning.impl;

import com.itextpdf.forms.form.element.SignatureFieldAppearance;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.signatures.SignerProperties;
import com.khesam.papyrus.core.repository.SignerRepository;
import com.khesam.papyrus.core.repository.entity.SignerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SignerPropertiesService {

    private final SignerRepository signerRepository;

    @Autowired
    public SignerPropertiesService(SignerRepository signerRepository) {
        this.signerRepository = signerRepository;
    }

    public SignerProperties getSignerProperties(int signerId, String signatureFieldName) {
        SignerEntity signerEntity = signerRepository.findById(signerId).orElseThrow();
        return createSignerProperties(signatureFieldName, signerEntity.getWetSignaturePath());
    }

    private SignerProperties createSignerProperties(String signatureFieldName, String wetSignaturePath) {
        SignerProperties signerProperties = new SignerProperties().setFieldName(signatureFieldName);
        SignatureFieldAppearance appearance = getAppearance(
                signatureFieldName, wetSignaturePath
        );

        signerProperties
                .setPageNumber(1)
                .setPageRect(new Rectangle(0, 650, 200, 150))
                .setSignatureAppearance(appearance)
                .setReason("Reason")
                .setLocation("Location");
        return signerProperties;
    }

    private SignatureFieldAppearance getAppearance(String signatureFieldName, String wetSignatureImagePath) {
        SignatureFieldAppearance appearance;
        Div div = new Div();
        //FIXME: replace
        ImageData imageData = ImageDataFactory.create(getClass().getClassLoader().getResource(wetSignatureImagePath));
        Image wetSignature = new Image(imageData);
        wetSignature.setWidth(100);
        wetSignature.setHeight(100);
        wetSignature.setAutoScale(true);
        div.add(wetSignature);
        appearance = new SignatureFieldAppearance("Sadegh");
        appearance.setInteractive(true);
        appearance.setSignerName(signatureFieldName);
        appearance.setContent(div);
        appearance.setPageNumber(1);

        return appearance;
    }

//    private Optional<byte[]> getWetImageIfExists(String wetSignatureImagePath) {
//
//    }
}
