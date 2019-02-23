package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.CharacterAsString;
import nl.java.shakespearelang.parser.line.Line;

public class Push extends Line {
    public Push(CharacterAsString currentSubject, String line) {
        super(currentSubject, line);
    }
}
