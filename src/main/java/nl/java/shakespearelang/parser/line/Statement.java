package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Statement extends Line {
    private boolean printNumber;

    public Statement(String subject, String line, boolean printNumber) {
        super(subject, line);
        this.printNumber = printNumber;
    }
}
