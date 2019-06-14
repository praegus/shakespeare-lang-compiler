package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

public class ActSceneHelper {

    public static int checkNumber(int number, String titleRaw, String type) {
        if (!titleRaw.contains(type + " ") || !titleRaw.contains(":")) {
            throw new ParseException("Title of " + type + " does not contain '" + type + "' or a semicolumn!");
        }
        String romanNumeral = titleRaw.substring(0, titleRaw.indexOf(':')).replace(type, "").trim();
        if (romanToArabic(romanNumeral) != number) {
            throw new ParseException(type + " numbering is not in sequence!");
        }
        return number;
    }

    public static String extractTitle(String title, String type, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        throw new ParseException("Not title in " + type + " found!");
    }
}
