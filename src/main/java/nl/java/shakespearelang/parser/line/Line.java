package nl.java.shakespearelang.parser.line;

import lombok.Getter;
import nl.java.shakespearelang.CharacterInPlay;

@Getter
public abstract class Line {
    private CharacterInPlay subject;
    private String line;

    public Line(CharacterInPlay subject, String line) {
        this.subject = subject;
        this.line = line.trim().replace("\n", " ");
    }
}
