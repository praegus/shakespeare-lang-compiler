package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.CharacterInPlay;
import nl.java.shakespearelang.parser.line.Line;

public class Push extends Line {
    public Push(CharacterInPlay currentSubject, String line) {
        super(currentSubject, line);
    }
}
