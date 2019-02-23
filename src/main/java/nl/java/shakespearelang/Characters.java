package nl.java.shakespearelang;

import java.util.ArrayList;

import lombok.Data;

public class Characters extends ArrayList<Character> {
	public Character getCharacter(CharacterInPlay characterInPlay) {
		for (Character character : this) {
			if (character.getName().equals(characterInPlay.getName())) {
				return character;
			}
		}
		throw new RuntimeException("Character not found: " + characterInPlay.getName());
	}
}