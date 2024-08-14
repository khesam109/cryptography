package com.khesam.papyrus.core.pdfsigning;

import java.security.cert.Certificate;

public interface CertificateFetcherService {

    Certificate[] getCertificates(int signerId);
}
