package nl.java.shakespearelang.executor;

import lombok.extern.slf4j.Slf4j;
import nl.java.shakespearelang.parser.Act;
import nl.java.shakespearelang.parser.Play;
import nl.java.shakespearelang.parser.Scene;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Enter;
import nl.java.shakespearelang.parser.line.Exit;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.OutputStatement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class PlayPerformer {
    private final Play play;
    private Wordlist wordlist;
    private Map<String, Integer> characters;
    private List<String> personaeOnStage = new ArrayList<>();

    public PlayPerformer(Play play) throws IOException {
        this.play = play;
        this.wordlist = new Wordlist();
        initializeCharacters(play.getCharacters());
    }

    public void performPlay() {
        for (Act act : play.getActs()) {
            for (Scene scene : act.getScenes()) {
                for (Line line : scene.getLines()) {
                    performLine(line);
                }
            }
        }
        System.out.println();
        System.out.println();
    }

    private void initializeCharacters(Map<String, Integer> characters) {
        for (String character : characters.keySet()) {
            if (!wordlist.isCharacter(character)) {
                throw new RuntimeException("Character " + character + " is not a Shakespeare personae!");
            }
        }
        this.characters = characters;
    }

    private void performLine(Line line) {
        if (line instanceof Enter) {
            personaeOnStage.addAll(((Enter) line).getCharacters());
        } else if (line instanceof Exit) {
            exitPersonae((Exit) line);
        } else if (line instanceof OutputStatement) {
            performStatement(line, characters.get(getObjectOfLine(line.getSubject())));
        } else if (line instanceof Assignment) {
            String object = getObjectOfLine(line.getSubject());
            AssignmentPerformer assignmentPerformer = new AssignmentPerformer((Assignment) line, characters, characters.get(object), wordlist);
            characters.replace(object, assignmentPerformer.performAssignment());
        } else {
            throw new RuntimeException("unknown line type: " + line.getClass().getSimpleName());
        }
    }

    private void exitPersonae(Exit line) {
        if (line.isExeunt()) {
            personaeOnStage = Collections.emptyList();
        } else {
            personaeOnStage.removeAll(line.getCharacters());
        }
    }

    private void performStatement(Line line, Integer objectValue) {
        if (((OutputStatement) line).isPrintNumber()) {
            System.out.print(objectValue);
        } else {
            System.out.print(Character.toString((char) objectValue.intValue()));
        }
    }

    private String getObjectOfLine(String subject) {
        if (personaeOnStage.size() != 2) {
            throw new RuntimeException("Number of characters on stage is not correct!");
        } else if (!personaeOnStage.contains(subject)) {
            throw new RuntimeException("Speaking person is not on stage!");
        }
        int indexOfSubject = personaeOnStage.indexOf(subject);
        if (indexOfSubject == 0) {
            return personaeOnStage.get(1);
        } else {
            return personaeOnStage.get(0);
        }
    }

}
