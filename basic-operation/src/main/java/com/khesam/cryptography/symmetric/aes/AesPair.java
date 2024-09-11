package com.khesam.cryptography.symmetric.aes;

public record AesPair(
        byte[] iv,
        byte[] cipherText
) {

}
