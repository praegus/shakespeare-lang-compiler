package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.java.shakespearelang.CharacterInPlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
@Getter
public class Exit extends Line {
    private List<CharacterInPlay> characters = new ArrayList<>();
    private boolean exeunt;

    public Exit(String line) {
        super(null, line);
        if (line.equals("exeunt")) {
            exeunt = true;
        } else {
            String[] _characters = line.replace("exit", "").replace("exeunt", "").split("and");
            for (String character : _characters) {
                this.characters.add(new CharacterInPlay(character.trim()));
            }
        }
    }
}
