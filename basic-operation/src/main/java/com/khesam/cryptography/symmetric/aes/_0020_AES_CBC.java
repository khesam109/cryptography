package com.khesam.cryptography.symmetric.aes;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Scanner;

class _0020_AES_CBC {

    private final Cipher cipher;
    private final Key key;

    public _0020_AES_CBC(Key key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        this.key = key;
    }

    AesPair encrypt(String plainText) {
        //https://en.wikipedia.org/wiki/Initialization_vector
        //The IV size depends on the cryptographic primitive used;
        //For block ciphers it is generally the cipher's block-size
        byte[] iv = new byte[16]; // 128 bits
        (new SecureRandom()).nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return new AesPair(iv, cipherText);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    String decrypt(AesPair aesPair) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(aesPair.iv());

        try {
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);

            return new String(
                    cipher.doFinal(aesPair.cipherText())
            );
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException {
        _0020_AES_CBC aes_cbc = new _0020_AES_CBC(AesKeyGeneratorService.key(256));


        System.out.print("Enter plaintext: ");
        Scanner scanner = new Scanner(System.in);
        String plainText = scanner.nextLine();

        AesPair firstPair = aes_cbc.encrypt(plainText);
        AesPair secondPair = aes_cbc.encrypt(plainText);

        System.out.printf("First ciphertext: %s\n", Hex.toHexString(firstPair.cipherText()));
        System.out.printf("First ciphertext length: %d\n", firstPair.cipherText().length);

        System.out.printf("Second ciphertext: %s\n", Hex.toHexString(secondPair.cipherText()));
        System.out.printf("Second ciphertext length: %d\n", secondPair.cipherText().length);

        System.out.println("First ciphertext and second ciphertext equals? " + Arrays.equals(firstPair.cipherText(), secondPair.cipherText()));

        System.out.printf("Decrypt first ciphertext: %s\n", aes_cbc.decrypt(firstPair));
        System.out.printf("Decrypt second ciphertext: %s\n", aes_cbc.decrypt(secondPair));
    }
}
