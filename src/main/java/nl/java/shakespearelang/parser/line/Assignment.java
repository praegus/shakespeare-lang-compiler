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
        line = line.replaceAll("the difference between", THEDIFFERENCEBETWEEN);
        line = line.replaceAll("the sum of", THESUMOF);
        line = line.replaceAll("the product of", THEPRODUCTOF);
        line = line.replaceAll("the square of", THESQUAREOF);
        line = line.replaceAll("the remainder of the quotient between", THEREMAINDEROFTHEQUOTIENT);
        line = line.replaceAll("the quotient between", THEQUOTIENTBETWEEN);
        line = line.replaceAll("the cube of", THECUBEOF);
        line = line.replaceAll("twice", TWICE);


        line = line.replaceAll("summer's day", "summer'sday");
        line = line.replaceAll("thyself", YOURSELF);
        line = line.replaceAll(" me", " "+ME);
        this.words = line.split(" ");
    }
}
