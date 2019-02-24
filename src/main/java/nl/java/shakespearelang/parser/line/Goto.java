package nl.java.shakespearelang.parser.line;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

import lombok.Getter;

@Getter
public class Goto extends Line {
    private int requestedScene;
    private Boolean expectedvalue;

    public Goto(String subject, String line, Boolean expectedValue) {
        super(subject, line);
        this.expectedvalue = expectedValue;
        String romanNumeral = line.split("to scene")[1].trim();
        requestedScene = romanToArabic(romanNumeral);
    }

    public boolean conditionApplies(boolean condition) {
        return expectedvalue == null || expectedvalue == condition;
    }
}