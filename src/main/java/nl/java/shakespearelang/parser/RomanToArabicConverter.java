package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.ParseException;

public class RomanToArabicConverter {

    public static int romanToArabic(String number) {
        number = number.toLowerCase();
        if (number.isEmpty()) {
            return 0;
        }
        if (number.startsWith("m")) {
            return 1000 + romanToArabic(number.replaceFirst("m", ""));
        }
        if (number.startsWith("cm")) {
            return 900 + romanToArabic(number.replaceFirst("cm", ""));
        }
        if (number.startsWith("d")) {
            return 500 + romanToArabic(number.replaceFirst("d", ""));
        }
        if (number.startsWith("cd")) {
            return 400 + romanToArabic(number.replaceFirst("cd", ""));
        }
        if (number.startsWith("c")) {
            return 100 + romanToArabic(number.replaceFirst("c", ""));
        }
        if (number.startsWith("xc")) {
            return 90 + romanToArabic(number.replaceFirst("xc", ""));
        }
        if (number.startsWith("l")) {
            return 50 + romanToArabic(number.replaceFirst("l", ""));
        }
        if (number.startsWith("xl")) {
            return 40 + romanToArabic(number.replaceFirst("xl", ""));
        }
        if (number.startsWith("x")) {
            return 10 + romanToArabic(number.replaceFirst("x", ""));
        }
        if (number.startsWith("ix")) {
            return 9 + romanToArabic(number.replaceFirst("ix", ""));
        }
        if (number.startsWith("v")) {
            return 5 + romanToArabic(number.replaceFirst("v", ""));
        }
        if (number.startsWith("iv")) {
            return 4 + romanToArabic(number.replaceFirst("iv", ""));
        }
        if (number.startsWith("i")) {
            return 1 + romanToArabic(number.replaceFirst("i", ""));
        }
        throw new ParseException("Not a valid Roman numeral!");
    }
}
