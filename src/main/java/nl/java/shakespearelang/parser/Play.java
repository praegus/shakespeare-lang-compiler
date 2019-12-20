package nl.java.shakespearelang.parser;

import lombok.Getter;
import nl.java.shakespearelang.ParseException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Play {
    private String title;
    private Characters characters = new Characters();
    private List<Act> acts = new ArrayList<>();

    public Play(String input) {
        String simplifiedInput = simplifyInput(input);

        if (!simplifiedInput.contains(".")) {
            throw new ParseException("Cannot parse program, there is no title!");
        }
        this.title = simplifiedInput.substring(0, input.indexOf('.'));
        extractPersonae(simplifiedInput);
        extractActs(simplifiedInput);
    }

    public Act getAct(int actNumber) {
        if (acts.get(actNumber - 1).getActNumber() == actNumber) {
            return acts.get(actNumber - 1);
        } else {
            throw new ParseException("Act numbering is not in order!");
        }
    }

    public int getNumberOfActs() {
        return acts.size();
    }

    private String simplifyInput(String input) {
        String output = input;
        output = output.toLowerCase();
        output = output.replaceAll("\n", "");
        output = output.replaceAll("\t", " ");
        output = output.replaceAll("\r", "");
        output = output.replaceAll("!", ".");
        output = output.replaceAll("\\?", ".");
        output = output.replaceAll("\\[", "");
        output = output.replaceAll("]", ".");
        output = output.replaceAll("-", "");

        while (output.contains("  ")) {
            output = output.replaceAll("  ", " ");
        }
        return output;
    }

    private void extractPersonae(String input) {
        if (!input.contains("act ")) {
            throw new ParseException("Cannot parse program, there are no acts!");
        }
        String[] personae = input.substring(input.indexOf('.') + 1, input.indexOf(" act ")).trim().split("\\.");
        for (String aPersonae : personae) {
            if (!aPersonae.contains(",")) {
                throw new ParseException("Cannot parse program, Character has no description starting with a comma and ending with a dot!");
            }
            characters.add(new Character(aPersonae.substring(0, aPersonae.indexOf(',')).toLowerCase(), 0));
        }
    }

    private void extractActs(String input) {
        String[] actsString = input.split("(?=act\\s\\w*:)");
        for (int i = 1; i < actsString.length; i++) {
            acts.add(new Act(actsString[i], i));
        }
    }
}
