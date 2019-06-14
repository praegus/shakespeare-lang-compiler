package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class Conditional extends Line {
    public Conditional(String subject, String line) {
        super(subject, line);
    }
}
