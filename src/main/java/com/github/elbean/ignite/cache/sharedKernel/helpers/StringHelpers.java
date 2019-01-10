package com.github.elbean.ignite.cache.sharedKernel.helpers;

public class StringHelpers {

    /**
     * Remove all whitespace characters from a given string.
     *
     * @param input String to remove whitespace from
     * @return String without whitespace
     */
    public static String removeWhiteSpace(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
