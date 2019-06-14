package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@Getter
public class Exit extends Line {
    private List<String> characters = new ArrayList<>();
    private boolean exeunt;

    public Exit(String line) {
        super(null, line);
        if (line.equals("exeunt")) {
            exeunt = true;
        } else {
            String[] _characters = line.replace("exit", "").replace("exeunt", "").split("and");
            for (String character : _characters) {
                this.characters.add(character.trim());
            }
        }
    }
}
