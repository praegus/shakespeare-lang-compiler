package nl.java.shakespearelang.parser.line;

import lombok.Getter;

@Getter
public class Assignment extends Line {
    private String[] words;

    public Assignment(String subject, String line) {
        super(subject, line);
        line = line.toLowerCase();
        line = line.replaceAll("the difference between a", "THEDIFFERENCEBETWEENA");
        line = line.replaceAll("and thyself", "ANDTHYSELF");
        this.words = line.split(" ");
    }
}
