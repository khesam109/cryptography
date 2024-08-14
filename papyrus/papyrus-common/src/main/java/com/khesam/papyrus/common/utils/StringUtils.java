package com.khesam.papyrus.common.utils;

public class StringUtils {

    private StringUtils() {}

    public static String removePostfix(String input, String postfix) {
        return input.substring(
                0, input.lastIndexOf(postfix)
        );
    }
}
