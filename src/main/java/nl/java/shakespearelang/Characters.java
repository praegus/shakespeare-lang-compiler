package nl.java.shakespearelang;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Characters extends LinkedHashMap<CharacterInPlay, Integer[]> {
	Map<CharacterInPlay, Integer[]> characters = new HashMap<>();
	
	public void replace(CharacterInPlay characterInPlay, Integer integer) {
		this.characters.replace(characterInPlay, new Integer[] { integer });
	}
	
	public void put(CharacterInPlay characterInPlay, Integer integer) {
		this.characters.put(characterInPlay, new Integer[] { integer });
	}
	
	public Integer getValue(CharacterInPlay characterInPlay) {
		int length = characters.get(characterInPlay).length;
		return characters.get(characterInPlay)[length-1];
	}
}
