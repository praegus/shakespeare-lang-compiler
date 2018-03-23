package nl.java.shakespearelang;

import java.util.List;

public class Compiler {
    private final String input;
    private String title;
    private String[] dramaticPersonae;
    private List<Act> acts;


    public Compiler(String input) {
        this.input = input;
        this.title = subtractTitle();
        this.dramaticPersonae = extractPersonae();
        this.acts = extractActs();
    }

    private List<Act> extractActs() {
        String[] acts = input.substring(input.indexOf("Act")).trim().split("Act");
        return null;
    }

    private String subtractTitle() {
        return input.substring(0, input.indexOf("."));

    }

    private String[] extractPersonae() {
        String[] personae = input.substring(input.indexOf(".")+1, input.indexOf("Act")).trim().split("\n");

        return null;
    }

}
