package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
public class Enter extends Line {
    private List<String> characters = new ArrayList<>();

    public Enter(String line) {
        super(null, line);
        String[] characterStrings = line.replace("enter", "").trim().split("and");
        for (String character : characterStrings) {
            this.characters.add(character.trim());
        }
    }
}
