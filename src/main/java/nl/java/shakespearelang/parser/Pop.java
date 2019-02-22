package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.CharacterInPlay;
import nl.java.shakespearelang.parser.line.Line;

public class Pop extends Line {
    public Pop(CharacterInPlay currentSubject, String line) {
        super(currentSubject, line);
    }
}
