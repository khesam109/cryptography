package com.khesam.cryptography.aes;

import com.khesam.cryptography.common.AesPair;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class _0010_AES_ECB {

    private static byte[] encrypt(Cipher cipher, String plainText, Key key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static String decrypt(Cipher cipher, byte[] cipherText, Key key) {
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
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher aes_ecb = Cipher.getInstance("AES/ECB/PKCS5Padding");

        System.out.print("Enter plaintext: ");
        Scanner scanner = new Scanner(System.in);
        String plainText = scanner.nextLine();

        byte[] firstCipherText = encrypt(aes_ecb, plainText, secretKey);
        byte[] secondCipherText = encrypt(aes_ecb, plainText, secretKey);

        System.out.printf("First ciphertext: %s\n", Hex.toHexString(firstCipherText));
        System.out.printf("First ciphertext length: %d\n", firstCipherText.length);

        System.out.printf("Second ciphertext: %s\n", Hex.toHexString(secondCipherText));
        System.out.printf("Second ciphertext length: %d\n", secondCipherText.length);

        System.out.println("First ciphertext and second ciphertext equals? " + Arrays.equals(firstCipherText, secondCipherText));

        System.out.printf("Decrypt first ciphertext: %s\n", decrypt(aes_ecb, firstCipherText, secretKey));
        System.out.printf("Decrypt second ciphertext: %s\n", decrypt(aes_ecb, secondCipherText, secretKey));

    }
}
