package nl.java.shakespearelang.parser;

import lombok.Getter;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Enter;
import nl.java.shakespearelang.parser.line.Exit;
import nl.java.shakespearelang.parser.line.InputStatement;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.OutputStatement;

import java.util.ArrayList;
import java.util.List;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

@Getter
public class Scene {
    private String title;
    private int sceneNumber;
    private List<Line> lines = new ArrayList<>();

    public Scene(String titleAndScene, int sceneNumber) {
        this.sceneNumber = sceneNumber;
        if (!titleAndScene.contains(".")) {
            throw new RuntimeException("Title of scene is not ended properly with a dot!");
        }
        setTitleAndsceneNumber(titleAndScene.substring(0, titleAndScene.indexOf(".")).trim());

        String content = titleAndScene.substring(titleAndScene.indexOf(".") + 1).trim();
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
        String[] linesString = content.split("\\.");

        String currentSubject = "";
        for (String line : linesString) {
            if (line.contains("enter")) {
                lines.add(new Enter(line));
            } else if (line.contains("exit") || line.contains("exeunt")) {
                lines.add(new Exit(line));
            } else if (line.contains(":")) {
                currentSubject = line.substring(0, line.indexOf(":"));
                addLine(currentSubject, line.substring(line.indexOf(":") + 1).trim());
            } else {
                addLine(currentSubject, line);
            }
        }
    }

    private void addLine(String currentSubject, String line) {
        line = line.trim();
        if (line.equals("listen to your heart")) {
            lines.add(new InputStatement(currentSubject, line));
        } else if (line.equals("speak your mind") || line.equals("speak thy mind")) {
            lines.add(new OutputStatement(currentSubject, line, false));
        } else if (line.startsWith("you")) {
            lines.add(new Assignment(currentSubject, line));
        } else if (line.startsWith("thou")) {
            lines.add(new Assignment(currentSubject, line));
        } else {
            throw new RuntimeException("type of line is unclear!");
        }
    }
}
