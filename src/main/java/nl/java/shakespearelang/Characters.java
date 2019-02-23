package nl.java.shakespearelang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Characters extends HashMap<CharacterInPlay, List<Integer>> {
	public Map<CharacterInPlay, List<Integer>> characters = this;
	
	public void replace(CharacterInPlay characterInPlay, Integer integer) {
		List<Integer> newIntegerList = new ArrayList<Integer>();
		newIntegerList.add(integer);
		this.characters.replace(characterInPlay, newIntegerList);
	}
	
	public void put(CharacterInPlay characterInPlay, Integer integer) {
		List<Integer> newIntegerList = new ArrayList<Integer>();
		newIntegerList.add(integer);
		this.characters.put(characterInPlay, newIntegerList);
	}
	
	public void push(CharacterInPlay characterInPlay, Integer integer) {
		this.characters.get(characterInPlay).add(integer);
	}
	
	public void pop(CharacterInPlay characterInPlay) {
		int size = characters.get(characterInPlay).size();
		this.characters.get(characterInPlay).remove(size - 1);
	}
	
	public Integer getValue(CharacterInPlay characterInPlay) {
		int size = characters.get(characterInPlay).size();
		return characters.get(characterInPlay).get(size - 1);
	}
}
