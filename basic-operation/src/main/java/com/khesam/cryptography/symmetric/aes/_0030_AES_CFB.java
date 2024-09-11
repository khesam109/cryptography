package com.khesam.cryptography.symmetric.aes;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class _0030_AES_CFB {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] iv = new byte[16];
        (new SecureRandom()).nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();

        Cipher aes_cfb = Cipher.getInstance("AES/CFB8/NoPadding");
        aes_cfb.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a character to encrypt: ");
            char c = scanner.next().charAt(0);
            System.out.println(c);
            if (c == 13)    // Enter pressed
                break;

            byte ptxt = (byte) c;
            byte[] ctxt = aes_cfb.update(new byte[]{ptxt});

            System.out.printf("Ciphertext: %s\n", Hex.toHexString(ctxt));
        }
    }
}
