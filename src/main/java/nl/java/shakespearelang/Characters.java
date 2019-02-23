package nl.java.shakespearelang;

import java.util.ArrayList;

public class Characters extends ArrayList<Character> {
	public Character getCharacter(CharacterAsString characterInPlay) {
		for (Character character : this) {
			if (character.getName().equals(characterInPlay.getName())) {
				return character;
			}
		}
		throw new RuntimeException("Character not found: " + characterInPlay.getName());
	}
	
	public Character getCharacter(String name) {
		for (Character character : this) {
			if (character.getName().equals(name)) {
				return character;
			}
		}
		throw new RuntimeException("Character not found: " + name);
	}
}