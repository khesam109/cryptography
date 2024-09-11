package com.khesam.cryptography.symmetric.des;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class _0010_DES {

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
        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keygenerator.generateKey();

        Cipher des_ecb = Cipher.getInstance("DES/ECB/PKCS5Padding");

        System.out.print("Enter plaintext: ");
        Scanner scanner = new Scanner(System.in);
        String plainText = scanner.nextLine();

        byte[] cipherText = encrypt(des_ecb, plainText, secretKey);

        System.out.printf("Ciphertext: %s\n", Hex.toHexString(cipherText));

        System.out.printf("Decrypt ciphertext: %s\n", decrypt(des_ecb, cipherText, secretKey));
    }
}
