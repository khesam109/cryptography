package com.khesam.cryptography.provider;

import java.security.Provider;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;

public class _0010_Provider {

    private static void printAllCipherAlgorithms() {
        Set<String> algorithms = new TreeSet<>();
        for (Provider _provider : Security.getProviders()) {
            _provider.getServices().stream()
                    .filter(s -> "Cipher".equals(s.getType()))
                    .map(Provider.Service::getAlgorithm)
                    .forEach(algorithms::add);
        }
        algorithms.forEach(System.out::println);
    }

    public static void main(String[] args) {
        printAllCipherAlgorithms();
    }
}
