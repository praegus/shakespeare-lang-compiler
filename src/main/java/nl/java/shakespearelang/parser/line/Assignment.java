package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Assignment extends Line {
    private String[] words;

    public Assignment(String subject, String line) {
        super(subject, line);
        line = line.toLowerCase();
        line = line.replaceAll("the difference between", THEDIFFERENCEBETWEEN);
        line = line.replaceAll("the sum of", THESUMOF);
        line = line.replaceAll("the product of", THEPRODUCTOF);
        line = line.replaceAll("the square of", THESQUAREOF);
        line = line.replaceAll("the square root of", THESQUAREROOT);

        line = line.replaceAll("the remainder of the quotient between", THEREMAINDEROFTHEQUOTIENT);
        line = line.replaceAll("the quotient between", THEQUOTIENTBETWEEN);
        line = line.replaceAll("the cube of", THECUBEOF);
        line = line.replaceAll("twice", TWICE);
        line = line.replaceAll("nothing", NOTHING);


        line = line.replaceAll("summer's day", "summer'sday");
        line = line.replaceAll("stone wall", "stonewall");
        line = line.replaceAll("thyself", YOURSELF);
        line = line.replaceAll(" me", " "+ME);
        line = line.replaceAll(" myself", " "+ME);
        this.words = line.split(" ");
    }
}
