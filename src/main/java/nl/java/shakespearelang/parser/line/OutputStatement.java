package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OutputStatement extends Line {
    private boolean printNumber;

    public OutputStatement(String subject, String line, boolean printNumber) {
        super(subject, line);
        this.printNumber = printNumber;
    }
}
