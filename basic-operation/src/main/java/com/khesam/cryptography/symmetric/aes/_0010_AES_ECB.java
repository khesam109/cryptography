package com.khesam.cryptography.symmetric.aes;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

class _0010_AES_ECB {

    private final Cipher cipher;
    private final Key key;

    _0010_AES_ECB(Key key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        this.key = key;
    }

    byte[] encrypt(String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    String decrypt(byte[] cipherText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(
                    cipher.doFinal(
                            cipherText
                    )
            );
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
        _0010_AES_ECB aes_ecb = new _0010_AES_ECB(AesKeyGeneratorService.key(256));

        System.out.print("Enter plaintext: ");
        Scanner scanner = new Scanner(System.in);
        String plainText = scanner.nextLine();

        byte[] firstCipherText = aes_ecb.encrypt(plainText);
        byte[] secondCipherText = aes_ecb.encrypt(plainText);

        System.out.printf("First ciphertext: %s\n", Hex.toHexString(firstCipherText));
        System.out.printf("First ciphertext length: %d\n", firstCipherText.length);

        System.out.printf("Second ciphertext: %s\n", Hex.toHexString(secondCipherText));
        System.out.printf("Second ciphertext length: %d\n", secondCipherText.length);

        System.out.println("First ciphertext and second ciphertext equals? " + Arrays.equals(firstCipherText, secondCipherText));
        System.out.printf("Decrypt first ciphertext: %s\n", aes_ecb.decrypt(firstCipherText));
        System.out.printf("Decrypt second ciphertext: %s\n", aes_ecb.decrypt(secondCipherText));
    }
}
