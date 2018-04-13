package nl.java.shakespearelang.parser;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

@Getter
public class Act {
    private String title;
    private List<Scene> scenes = new ArrayList<>();
    private int actNumber;

    public Act(String actString, int number) {
        actNumber = number;
        String[] titleAndScenes = actString.split("scene ");
        setTitleAndActNumber(titleAndScenes[0].replaceFirst("\\.", "").trim());
        for (int i = 1; i < titleAndScenes.length; i++) {
            scenes.add(new Scene(titleAndScenes[i].trim(), i));
        }
    }

    private void setTitleAndActNumber(String titleRaw) {
        if (!titleRaw.contains(":")) {
            throw new RuntimeException("Title of act does not contain an act number with a semicolumn!");
        }
        String romanNumeral = titleRaw.substring(0, titleRaw.indexOf(":"));
        if (romanToArabic(romanNumeral) != actNumber) {
            throw new RuntimeException("Act numbering is not in sequence!");
        }
        this.title = titleRaw.substring(titleRaw.indexOf(":")+1).trim();
    }
}
