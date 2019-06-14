package nl.java.shakespearelang.parser;

import lombok.Getter;
import nl.java.shakespearelang.ParseException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Act {
    private String title;
    private List<Scene> scenes = new ArrayList<>();
    private int actNumber;

    public Act(String actString, int number) {
        String[] titleAndScenes = actString.split("(?=scene\\s\\w*:)");
        this.actNumber = ActSceneHelper.checkNumber(number, titleAndScenes[0]);
        this.title = ActSceneHelper.extractTitle(titleAndScenes[0]);

        for (int i = 1; i < titleAndScenes.length; i++) {
            scenes.add(new Scene(titleAndScenes[i].trim(), i));
        }
    }

    public Scene getScene(int sceneNumber) {
        if (scenes.get(sceneNumber - 1).getSceneNumber() == sceneNumber) {
            return scenes.get(sceneNumber - 1);
        } else {
            throw new ParseException("Scene numbering is not in order!");
        }
    }

    public int getNumberOfScenes() {
        return scenes.size();
    }
}
