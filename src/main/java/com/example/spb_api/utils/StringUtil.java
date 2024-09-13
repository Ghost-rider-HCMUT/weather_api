package com.example.spb_api.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
    // Clean String
    public static String cleanString(String input) {
        String noDiacritics = removeDiacritics(input);
        // Delete " "; "-"
        String cleanedString = noDiacritics.replaceAll("[- ]", "");

        if (cleanedString.isEmpty()) {
            return cleanedString;
        }

        String result = cleanedString.toLowerCase();
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    // Remove accent marks
    private static String removeDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

}
