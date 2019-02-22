package nl.java.shakespearelang.parser.line;

import nl.java.shakespearelang.CharacterInPlay;

public class InputStatement extends Line {
    private final boolean readNumber;

    public InputStatement(CharacterInPlay subject, String line) {
        super(subject, line);
        if (line.equals("listen to your heart")) {
            this.readNumber = true;
        } else if (line.equals("open your mind")) {
            this.readNumber = false;
        } else {
            throw new RuntimeException("Unknown Input statement: " + line);
        }
    }

    public boolean readNumber() {
        return readNumber;
    }

    public boolean readCharacter() {
        return !readNumber;
    }
}
