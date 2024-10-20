package com.khesam.cryptography.symmetric.mac;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.Security;

public class _0010_HMAC {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        var names = new String[]{
                "HmacMD5",
                "HmacSHA1",
                "HmacSHA256",
                "HmacSHA512/256",
                "HmacSHA512",
                "HMACKECCAK256",
                "HMACKECCAK512"
        };

        System.out.printf("%-12s\t%s\t%s\t%s\n",
                "Algorithm", "Length (bits)", "Time (seconds)", "Output");
        System.out.println("-".repeat(174));

        byte[] bytes = new byte[1 << 28];
        long start, end;

        for (var name : names) {
            Mac mac = Mac.getInstance(name);
            KeyGenerator kg = KeyGenerator.getInstance(name);
            SecretKey key = kg.generateKey();
            mac.init(key);

            start = System.nanoTime();
            var out = mac.doFinal(bytes);
            end = System.nanoTime();

            System.out.printf("%-12s\t%-13s\t%-14s\t%s\n",
                    mac.getAlgorithm(),
                    mac.getMacLength() * 8,
                    (end - start) / 1E9,
                    Hex.toHexString(out));
        }

    }
}
