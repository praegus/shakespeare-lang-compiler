package nl.java.shakespearelang.parser;

import lombok.Getter;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.Statement;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Scene {
    private String title;
    private List<String> enter;
    private List<String> exit;
    private List<Line> lines = new ArrayList<>();
    private int sceneNumber;

    public Scene(String titleAndScene, int sceneNumber) throws Exception {
        this.title = titleAndScene.substring(0, titleAndScene.indexOf("[")).trim();
        this.enter = setEnterExit(titleAndScene.substring(titleAndScene.indexOf("[Enter"), titleAndScene.indexOf("]")).trim());
        if (titleAndScene.contains("[Exit")) {
            this.exit = setEnterExit(titleAndScene.substring(titleAndScene.indexOf("[Exit")).replaceAll("]",""));
        } else {
            this.exit = setEnterExit(titleAndScene.substring(titleAndScene.indexOf("[Exeunt")).replaceAll("]",""));
        }
        String content = titleAndScene.substring(titleAndScene.indexOf("]") + 1);
        content = content.substring(0, content.indexOf("[")).trim();
        setLines(content);
        this.sceneNumber = sceneNumber;
    }

    private void setLines(String content) throws Exception {
        content = content.replace("!", ".");
        String[] linesString = content.split("\\.");

        String currentSubject = "";
        for (String line : linesString) {
            if (line.contains(":")) {
                currentSubject = line.substring(0, line.indexOf(":"));
                addLine(currentSubject, line.substring(line.indexOf(":")+1));
            } else {
                addLine(currentSubject, line);
            }
        }
    }

    private void addLine(String currentSubject, String line) throws Exception {
        line = line.trim().replace("\n","");
        if (line.toLowerCase().equals("speak your mind") ||line.toLowerCase().equals("speak thy mind")) {
            lines.add(new Statement(currentSubject, line, false));
        } else if (line.toLowerCase().startsWith("you")) {
            lines.add(new Assignment(currentSubject, line));
        } else if (line.toLowerCase().startsWith("thou")) {
            lines.add(new Assignment(currentSubject, line));
        } else {
            throw new Exception("type line onduidelijk!");
        }
    }

    private List<String> setEnterExit(String enterExit) {
        String[] words = enterExit.split(" ");
        List<String> players = new ArrayList<>();
        for (int i = 1; i < words.length; i++) {
            if (!words[i].equals("and")) {
                players.add(words[i]);
            }
        }
        return players;
    }
}
