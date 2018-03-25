package nl.java.shakespearelang.executor;

import lombok.extern.slf4j.Slf4j;
import nl.java.shakespearelang.executor.assignment.AssignmentInterpreter;
import nl.java.shakespearelang.parser.Act;
import nl.java.shakespearelang.parser.Play;
import nl.java.shakespearelang.parser.Scene;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.Statement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProgramExecutor {
    private List<String> personaeOnStage = new ArrayList<>();
    private Map<String, Integer> characters;
    private Wordlist wordlist;

    public ProgramExecutor() throws IOException {
        this.wordlist = new Wordlist();
    }

    public void executeProgram(Play play) throws Exception {
        this.characters = play.getCharacters();
        for (String character : characters.keySet()) {
            if (!wordlist.isCharacter(character)) {
                throw new RuntimeException("Character " + character + " is not supported!");
            }
        }
        for (Act act : play.getActs()) {
            log.info("Act "+act.getTitle()+" is executed!");
            for (Scene scene : act.getScenes()) {
                log.info("Scene "+scene.getTitle()+" is executed!");
                personaeOnStage.addAll(scene.getEnter());
                for (Line line : scene.getLines()) {
                    log.info("Line "+line.getLine()+" is executed!");
                    executeLine(getObject(line.getSubject()), line);
                }
                personaeOnStage.removeAll(scene.getExit());
            }
        }
        System.out.println();
        System.out.println();
    }

    private void executeLine(String object, Line line) {
        Integer objectValue = characters.get(object);
        if (line instanceof Statement) {
            if (((Statement) line).isPrintNumber()) {
                System.out.print(objectValue);
            } else {
                System.out.print(Character.toString((char) objectValue.intValue()));
            }
        } else {
            AssignmentInterpreter assignmentInterpreter = new AssignmentInterpreter(wordlist, (Assignment) line);
            objectValue = assignmentInterpreter.getValue(objectValue, characters);
            characters.remove(object);
            characters.put(object, objectValue);
        }

    }

    private String getObject(String subject) throws Exception {
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
