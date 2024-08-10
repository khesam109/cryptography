package com.khesam.cryptography.random;

import com.khesam.cryptography.common.ChronometerService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class _0010_Randomness {

    private static void computeTime(Random randomGenerator) {
        // warm-up
        for (int i = 0; i < 10000; i++) {
            randomGenerator.nextBytes(new byte[10]);
        }

        byte[] random = new byte[1 << 26];

        ChronometerService chronometerService = ChronometerService.getInstance();
        chronometerService.start();
        randomGenerator.nextBytes(random);
        chronometerService.stop();

        //java.util.Random dose not have getAlgorithm()
        String name = (randomGenerator instanceof SecureRandom) ?
                " with algorithm " + ((SecureRandom) randomGenerator).getAlgorithm() : "";

        System.out.printf(
                "%s took %f seconds\n", randomGenerator.getClass() + name, chronometerService.durationInSec());
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        computeTime(new Random());
        computeTime(new SecureRandom());

        // getInstanceStrong() added in Java 1.8
        // Extremely fast on Windows - even faster than Random()
        // Stalled on Linux - don't know why?!
        computeTime(SecureRandom.getInstanceStrong());
    }
}
