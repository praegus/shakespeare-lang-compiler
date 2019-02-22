package nl.java.shakespearelang.parser;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

@Getter
public class Act {
    private String title;
    private List<Scene> scenes = new ArrayList<>();
    private int actNumber;

    public Act(String actString, int number) {
        String[] titleAndScenes = actString.split("(?=scene\\s\\w*:)");
        this.actNumber = checkActNumber(number, titleAndScenes[0]);
        this.title = extractTitle(titleAndScenes[0]);

        for (int i = 1; i < titleAndScenes.length; i++) {
            scenes.add(new Scene(titleAndScenes[i].trim(), i));
        }
    }

    public Scene getScene(int sceneNumber){
        if(scenes.get(sceneNumber-1).getSceneNumber()==sceneNumber){
            return scenes.get(sceneNumber-1);
        } else {
            throw new RuntimeException("Scene numbering is not in order!");
        }
    }

    public int getNumberOfScenes(){
        return scenes.size();
    }

    private int checkActNumber(int number, String titleRaw) {
        if(!titleRaw.contains("act ") || !titleRaw.contains(":")){
            throw new RuntimeException("Title of act does not contain 'act' or a semicolumn!");
        }
        String romanNumeral = titleRaw.substring(0, titleRaw.indexOf(":")).replace("act", "").trim();
        if (romanToArabic(romanNumeral) != number) {
            throw new RuntimeException("Act numbering is not in sequence!");
        }
        return number;
    }

    private String extractTitle(String title) {
        Pattern pattern = Pattern.compile("act\\s\\w.*:(.*?)\\.");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        throw new RuntimeException("Not title in act found!");
    }
}
