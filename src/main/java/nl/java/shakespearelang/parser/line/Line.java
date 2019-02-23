package nl.java.shakespearelang.parser.line;

import lombok.Getter;
import nl.java.shakespearelang.CharacterAsString;

@Getter
public abstract class Line {
    private CharacterAsString subject;
    private String line;

    public Line(CharacterAsString subject, String line) {
        this.subject = subject;
        this.line = line.trim().replace("\n", " ");
    }
}
