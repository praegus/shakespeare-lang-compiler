package nl.java.shakespearelang.parser;

import lombok.Getter;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.Statement;

import java.util.ArrayList;
import java.util.List;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

@Getter
public class Scene {
    private String title;
    private int sceneNumber;
    private List<String> enter;
    private List<String> exit;
    private List<Line> lines = new ArrayList<>();

    public Scene(String titleAndScene, int sceneNumber) {
        this.sceneNumber = sceneNumber;
        if (!titleAndScene.contains(".")) {
            throw new RuntimeException("Title of scene is not ended properly with a dot!");
        }
        setTitleAndsceneNumber(titleAndScene.substring(0, titleAndScene.indexOf(".")).trim());

        if (!titleAndScene.contains("]") || !titleAndScene.contains("[enter") || !titleAndScene.contains("[exit") && !titleAndScene.contains("[exeunt")) {
            throw new RuntimeException("Scene should contain an enter and exit or exeunt surrounded by square brackets ([])!");
        }
        this.enter = setEnterExit(titleAndScene.substring(titleAndScene.indexOf("[enter"), titleAndScene.indexOf("]")).trim());
        if (titleAndScene.contains("[exit")) {
            this.exit = setEnterExit(titleAndScene.substring(titleAndScene.indexOf("[exit")).replaceAll("]", ""));
        } else {
            this.exit = setEnterExit(titleAndScene.substring(titleAndScene.indexOf("[exeunt")).replaceAll("]", ""));
        }
        String content = titleAndScene.substring(titleAndScene.indexOf("]") + 1);
        content = content.substring(0, content.indexOf("[")).trim();
        setLines(content);
    }

    private void setTitleAndsceneNumber(String titleRaw) {
        if (!titleRaw.contains(":")) {
            throw new RuntimeException("Title of scene does not contain an act number with a semicolumn!");
        }
        String romanNumeral = titleRaw.substring(0, titleRaw.indexOf(":"));
        if (romanToArabic(romanNumeral) != sceneNumber) {
            throw new RuntimeException("Scene numbering is not in sequence!");
        }
        this.title = titleRaw.substring(titleRaw.indexOf(":") + 1).trim();
    }

    private void setLines(String content) {
        content = content.replace("!", ".");
        String[] linesString = content.split("\\.");

        String currentSubject = "";
        for (String line : linesString) {
            if (line.contains(":")) {
                currentSubject = line.substring(0, line.indexOf(":"));
                addLine(currentSubject, line.substring(line.indexOf(":") + 1));
            } else {
                addLine(currentSubject, line);
            }
        }
    }

    private void addLine(String currentSubject, String line) {
        line = line.trim().replace("\n", "");
        if (line.toLowerCase().equals("speak your mind") || line.toLowerCase().equals("speak thy mind")) {
            lines.add(new Statement(currentSubject, line, false));
        } else if (line.toLowerCase().startsWith("you")) {
            lines.add(new Assignment(currentSubject, line));
        } else if (line.toLowerCase().startsWith("thou")) {
            lines.add(new Assignment(currentSubject, line));
        } else {
            throw new RuntimeException("type of line is unclear!");
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
