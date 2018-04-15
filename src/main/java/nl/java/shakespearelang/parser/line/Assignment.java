package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.java.shakespearelang.CharacterInPlay;

import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;

@Getter
@EqualsAndHashCode
public class Assignment extends Line {
    private String[] words;

    public Assignment(CharacterInPlay subject, String line) {
        super(subject, line);
        line = line.toLowerCase();
        line = line.replaceAll("difference between", THEDIFFERENCEBETWEEN);
        line = line.replaceAll("sum of", THESUMOF);
        line = line.replaceAll("product of", THEPRODUCTOF);
        line = line.replaceAll("square of", THESQUAREOF);
        line = line.replaceAll("quotient between", THEQUOTIENTBETWEEN);
        line = line.replaceAll("cube of", THECUBEOF);
        line = line.replaceAll("twice", TWICE);


        line = line.replaceAll("summer's day", "summer'sday");
        line = line.replaceAll("thyself", YOURSELF);
        this.words = line.split(" ");
    }
}
