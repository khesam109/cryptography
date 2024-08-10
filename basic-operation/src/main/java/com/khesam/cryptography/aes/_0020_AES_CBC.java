package com.khesam.cryptography.aes;

import com.khesam.cryptography.common.AesPair;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Scanner;

public class _0020_AES_CBC {

    private static AesPair encrypt(Cipher cipher, String plainText, Key key) {
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

    private static String decrypt(Cipher cipher, AesPair aesPair, Key key) {
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
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher aes_cbc = Cipher.getInstance("AES/CBC/PKCS5Padding");

        System.out.print("Enter plaintext: ");
        Scanner scanner = new Scanner(System.in);
        String plainText = scanner.nextLine();

        AesPair firstPair = encrypt(aes_cbc, plainText, secretKey);
        AesPair secondPair = encrypt(aes_cbc, plainText, secretKey);

        System.out.printf("First ciphertext: %s\n", Hex.toHexString(firstPair.cipherText()));
        System.out.printf("First ciphertext length: %d\n", firstPair.cipherText().length);

        System.out.printf("Second ciphertext: %s\n", Hex.toHexString(secondPair.cipherText()));
        System.out.printf("Second ciphertext length: %d\n", secondPair.cipherText().length);

        System.out.println("First ciphertext and second ciphertext equals? " + Arrays.equals(firstPair.cipherText(), secondPair.cipherText()));

        System.out.printf("Decrypt first ciphertext: %s\n", decrypt(aes_cbc, firstPair, secretKey));
        System.out.printf("Decrypt second ciphertext: %s\n", decrypt(aes_cbc, secondPair, secretKey));
    }
}
