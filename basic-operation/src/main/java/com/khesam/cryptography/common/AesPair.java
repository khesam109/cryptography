package com.khesam.cryptography.common;

public record AesPair(
        byte[] iv,
        byte[] cipherText
) {

}
