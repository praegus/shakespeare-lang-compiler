package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
public class Enter extends Line {
    private List<String> characters = new ArrayList<>();

    public Enter(String line) {
        super(null, line);
        String[] _characters = line.replace("enter", "").trim().split("and");
        for (String character : _characters) {
            this.characters.add(character.trim());
        }
    }
}
