package nl.java.shakespearelang.parser.line;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

import lombok.Getter;
import nl.java.shakespearelang.CharacterInPlay;

@Getter
public class Goto extends Line {
	private boolean conditionBased;
	private int requestedScene;
	
    public Goto(CharacterInPlay subject, String line, boolean conditionBased) {
        super(subject, line);
        this.conditionBased = conditionBased;
        String romanNumeral = line.split("to scene")[1].trim();
        requestedScene = romanToArabic(romanNumeral);
    }
}
