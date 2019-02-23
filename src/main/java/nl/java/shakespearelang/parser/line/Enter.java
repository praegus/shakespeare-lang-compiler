package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.java.shakespearelang.CharacterAsString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
@Getter
public class Enter extends Line {
    private List<CharacterAsString> characters = new ArrayList<>();

    public Enter(String line) {
        super(null, line);
        String[] _characters = line.replace("enter", "").trim().split("and");
        for (String character : _characters) {
            this.characters.add(new CharacterAsString(character.trim()));
        }
    }
}
