package nl.java.shakespearelang.parser;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Act {
    private String title;
    private List<Scene> scenes = new ArrayList<>();
    private int actNumber;

    public Act(String actString, int number) throws Exception {
        this.actNumber = number;
        String[] titleAndScenes = actString.split("Scene ");
        this.title = titleAndScenes[0].trim();
        for (int i = 1; i < titleAndScenes.length; i++) {
            scenes.add(new Scene(titleAndScenes[i].trim(), i));
        }
    }
}
