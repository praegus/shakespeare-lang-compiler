package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.java.shakespearelang.CharacterInPlay;

@Getter
@EqualsAndHashCode
public class OutputStatement extends Line {
    private boolean printNumber;

    public OutputStatement(CharacterInPlay subject, String line, boolean printNumber) {
        super(subject, line);
        this.printNumber = printNumber;
    }
}
