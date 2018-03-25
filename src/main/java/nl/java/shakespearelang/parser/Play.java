package nl.java.shakespearelang.parser;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Play {
    private String title;
    private Map<String, Integer> characters = new HashMap<>();
    private List<Act> acts = new ArrayList<>();

    public Play(String input) throws Exception {
        input = input.toLowerCase();
        input = input.replaceAll("\n", "");
        input = input.replaceAll("\t", " ");
        while (input.contains("  ")) {
            input = input.replaceAll("  ", " ");
        }
        if (!input.contains(".")) {
            throw new RuntimeException("Cannot parse program, there is no title!");
        }
        this.title = input.substring(0, input.indexOf("."));
        extractPersonae(input);
        extractActs(input);
    }

    private void extractPersonae(String input) {
        if (!input.contains("act")) {
            throw new RuntimeException("Cannot parse program, there are no acts!");
        }
        String[] personae = input.substring(input.indexOf(".") + 1, input.indexOf("act")).trim().split("\\.");
        for (String aPersonae : personae) {
            if(!aPersonae.contains(",")){
                throw new RuntimeException("Cannot parse program, Character has no description starting with a comma and ending with a dot!");
            }
            characters.put(aPersonae.substring(0, aPersonae.indexOf(",")).toLowerCase(), 0);
        }
    }

    private void extractActs(String input) throws Exception {
        String[] actsString = input.split("act");
        for (int i = 1; i < actsString.length; i++) {
            acts.add(new Act(actsString[i], i));
        }
    }
}