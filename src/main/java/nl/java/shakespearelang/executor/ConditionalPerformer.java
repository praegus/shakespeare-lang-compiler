package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.CharacterInPlay;
import nl.java.shakespearelang.parser.line.Conditional;

import java.util.Map;

public class ConditionalPerformer {
    private final Conditional conditional;
    private final Map<CharacterInPlay, Integer> characters;

    public ConditionalPerformer(Conditional line, Map<CharacterInPlay, Integer> characters) {
        this.conditional = line;
        this.characters = characters;
    }

    public void performConditional() {


    }
}
