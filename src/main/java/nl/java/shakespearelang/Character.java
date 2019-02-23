package nl.java.shakespearelang;

import java.util.List;

import lombok.Data;

@Data
public class Character {
	private String name;
	private Integer value;
	private List<Integer> stack;
	
	public Character(final String name, final Integer value) {
		this.name = name;
		this.value = value;
	}
	
	public void pushStack(final int newValue) {
		stack.add(newValue);
	}
	
	public void popStack() {
		stack.remove(stack.size()-1);
	}
}