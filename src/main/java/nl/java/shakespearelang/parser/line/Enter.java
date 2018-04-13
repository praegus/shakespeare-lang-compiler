package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
@Getter
public class Enter extends Line {
    private List<String> characters;

    public Enter(String line) {
        super(null, line);
        String characters = line.replace("enter", "").replace("and ","").trim();
        this.characters = Arrays.asList(characters.split(" "));
    }
}
