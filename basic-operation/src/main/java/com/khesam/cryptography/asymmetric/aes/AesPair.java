package com.khesam.cryptography.asymmetric.aes;

public record AesPair(
        byte[] iv,
        byte[] cipherText
) {

}
