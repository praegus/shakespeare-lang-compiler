package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.ParseException;

import java.util.ArrayList;

public class Characters extends ArrayList<Character> {

    public Character getCharacter(String name) {
        for (Character character : this) {
            if (character.getName().equals(name)) {
                return character;
            }
        }
        throw new ParseException("Character not found: " + name);
    }

    public boolean isCharacter(String word) {
        return this.stream().map(Character::getName).anyMatch(e -> e.equals(word));
    }
}