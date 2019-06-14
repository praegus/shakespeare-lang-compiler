package nl.java.shakespearelang;

import java.util.ArrayList;

public class Characters extends ArrayList<Character> {

    public Character getCharacter(String name) {
        for (Character character : this) {
            if (character.getName().equals(name)) {
                return character;
            }
        }
        throw new RuntimeException("Character not found: " + name);
    }

    public boolean isCharacter(String word) {
        return this.stream().map(Character::getName).anyMatch(e -> e.equals(word));
    }
}