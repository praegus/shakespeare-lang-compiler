package nl.java.shakespearelang.parser.line;

import lombok.Getter;

import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;

@Getter
public class Assignment extends Line {
    private String[] words;

    public Assignment(String subject, String line) {
        super(subject, line);
        line = line.toLowerCase();
        line = line.replaceAll("the difference between a", THEDIFFERENCEBETWEEN);
        line = line.replaceAll("the difference between the", THEDIFFERENCEBETWEEN);
        line = line.replaceAll("the sum of your ", THESUMOF+" ");
        line = line.replaceAll("the sum of", THESUMOF);
        line = line.replaceAll("sum of the", THESUMOF);
        line = line.replaceAll("sum of", THESUMOF);
        line = line.replaceAll("and a", AND);
        line = line.replaceAll("summer's day", "summer'sday");
        line = line.replaceAll("thyself", YOURSELF);
        this.words = line.split(" ");
    }
}
