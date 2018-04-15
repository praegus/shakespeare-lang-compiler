package nl.java.shakespearelang.parser.line;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
@Getter
public class Exit extends Line {
    private List<String> characters;
    private boolean exeunt;

    public Exit(String line) {
        super(null, line);
        if (line.equals("exeunt")) {
            exeunt = true;
        } else {
            String characters = line.replace("exit", "").replace("exeunt", "").replace("and ", "").trim();
            this.characters = Arrays.asList(characters.split(" "));
        }
    }
}
