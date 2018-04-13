package nl.java.shakespearelang.parser.line;

import lombok.Getter;

@Getter
public abstract class Line {
    private String subject;
    private String line;

    public Line(String subject, String line) {
        this.subject = subject != null ? subject.trim() : null;
        this.line = line.trim().replace("\n", " ");
    }
}
