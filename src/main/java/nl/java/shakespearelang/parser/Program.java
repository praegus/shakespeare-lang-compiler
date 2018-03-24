package nl.java.shakespearelang.parser;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Program {
    private String title;
    private Map<String, Integer> rollen = new HashMap<>();
    private List<Act> acts = new ArrayList<>();

    public Program(String input) throws Exception {
        this.title = input.substring(0, input.indexOf("."));
        extractPersonae(input);
        extractActs(input);
    }

    private void extractActs(String input) throws Exception {
        String[] actsString = input.split("Act");
        for (int i = 1; i < actsString.length; i++) {
            acts.add(new Act(actsString[i], i));
        }
    }

    private void extractPersonae(String input) {
        String[] personae = input.substring(input.indexOf(".") + 1, input.indexOf("Act")).trim().split("\n");
        for (String aPersonae : personae) {
            rollen.put(aPersonae, null);
        }

    }

}
