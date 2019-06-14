package nl.java.shakespearelang.parser.line;

import nl.java.shakespearelang.ParseException;

public class InputStatement extends Line {
    private final boolean readNumber;

    public InputStatement(String subject, String line) {
        super(subject, line);
        if (line.equals("listen to your heart")) {
            this.readNumber = true;
        } else if (line.equals("open your mind")) {
            this.readNumber = false;
        } else {
            throw new ParseException("Unknown Input statement: " + line);
        }
    }

    public boolean readNumber() {
        return readNumber;
    }

    public boolean readCharacter() {
        return !readNumber;
    }
}
