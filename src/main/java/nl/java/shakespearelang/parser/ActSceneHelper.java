package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

public class ActSceneHelper {

    public static int checkNumber(int number, String titleRaw) {
        if (!titleRaw.contains("act ") || !titleRaw.contains(":")) {
            throw new ParseException("Title of act does not contain 'act' or a semicolumn!");
        }
        String romanNumeral = titleRaw.substring(0, titleRaw.indexOf(':')).replace("act", "").trim();
        if (romanToArabic(romanNumeral) != number) {
            throw new ParseException("Act numbering is not in sequence!");
        }
        return number;
    }

    public static String extractTitle(String title) {
        Pattern pattern = Pattern.compile("act\\s\\w.*:(.*?)\\.");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        throw new ParseException("Not title in act found!");
    }
}
