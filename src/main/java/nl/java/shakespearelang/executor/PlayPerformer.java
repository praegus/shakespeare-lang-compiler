package nl.java.shakespearelang.executor;

import lombok.extern.slf4j.Slf4j;
import nl.java.shakespearelang.parser.Act;
import nl.java.shakespearelang.parser.Play;
import nl.java.shakespearelang.parser.Scene;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.OutputStatement;

import java.io.IOException;
import java.util.ArrayList;
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

    public void performPlay() throws Exception {
        for (Act act : play.getActs()) {
            for (Scene scene : act.getScenes()) {
                personaeOnStage.addAll(scene.getEnter());
                for (Line line : scene.getLines()) {
                    performLine(getObjectOfLine(line.getSubject()), line);
                }
                personaeOnStage.removeAll(scene.getExit());
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

    private void performLine(String object, Line line) {
        Integer objectValue = characters.get(object);
        if (line instanceof OutputStatement) {
            performStatement(line, objectValue);
        } else if (line instanceof Assignment) {
            AssignmentPerformer assignmentPerformer = new AssignmentPerformer((Assignment) line, characters, objectValue, wordlist);
            characters.replace(object, assignmentPerformer.performAssignment());
        } else {
            throw new RuntimeException("unknown line type: " + line.getClass().getSimpleName());
        }
    }

    private void performStatement(Line line, Integer objectValue) {
        if (((OutputStatement) line).isPrintNumber()) {
            System.out.print(objectValue);
        } else {
            System.out.print(Character.toString((char) objectValue.intValue()));
        }
    }

    private String getObjectOfLine(String subject) throws Exception {
        if (personaeOnStage.size() != 2) {
            throw new Exception("aantal personen on stage is niet goed!");
        } else if (!personaeOnStage.contains(subject)) {
            throw new Exception("Sprekende persoon is niet on stage!");
        }
        int indexOfSubject = personaeOnStage.indexOf(subject);
        if (indexOfSubject == 0) {
            return personaeOnStage.get(1);
        } else {
            return personaeOnStage.get(0);
        }
    }

}
