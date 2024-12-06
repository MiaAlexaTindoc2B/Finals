package com.three_amigas.LaundryOps;

public class CapitalizeFirstLetters {
    public String capitalizeFirst(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return the input if it's null or empty
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true; // Flag to indicate if the next character should be capitalized

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true; // Next character should be capitalized
                result.append(c); // Append the whitespace
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c)); // Capitalize the character
                capitalizeNext = false; // Reset the flag
            } else {
                result.append(Character.toLowerCase(c)); // Lowercase the character
            }
        }

        return result.toString();
    }
}