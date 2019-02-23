package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.java.shakespearelang.CharacterAsString;

@Getter
@EqualsAndHashCode
public class OutputStatement extends Line {
    private boolean printNumber;

    public OutputStatement(CharacterAsString subject, String line, boolean printNumber) {
        super(subject, line);
        this.printNumber = printNumber;
    }
}
