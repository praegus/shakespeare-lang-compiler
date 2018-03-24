package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.parser.Act;
import nl.java.shakespearelang.parser.Program;
import nl.java.shakespearelang.parser.Scene;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.Statement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Executor {

    private List<String> personaeOnStage = new ArrayList<>();
    private Map<String, Integer> characters;
    private Wordlist wordlist;

    public Executor() throws IOException {
        this.wordlist = new Wordlist();
    }

    public void executeProgram(Program program) throws Exception {
        characters = program.getRollen();
        for (Act act : program.getActs()) {
            for (Scene scene : act.getScenes()) {
                personaeOnStage.addAll(scene.getEnter());
                for (Line line : scene.getLines()) {
                    executeLine(getObject(line.getSubject()), line);
                }
            }
        }
    }

    private void executeLine(String object, Line line) throws Exception {
        Integer objectValue = characters.get(object);
        if (line instanceof Statement) {
            if (((Statement) line).isPrintNumber()) {
                System.out.print(objectValue);
            } else {
                System.out.print(Character.toString((char) objectValue.intValue()));
            }
        } else {
            AssignmentExecutor assignmentExecutor = new AssignmentExecutor(wordlist, (Assignment) line);
            characters.remove(object);
            characters.put(object, assignmentExecutor.getValue());
        }

    }

    private void assignObject(String object, Assignment line) {

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
