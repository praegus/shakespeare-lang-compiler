package nl.java.shakespearelang.executor;

import lombok.extern.slf4j.Slf4j;
import nl.java.shakespearelang.CharacterInPlay;
import nl.java.shakespearelang.parser.Play;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Conditional;
import nl.java.shakespearelang.parser.line.Enter;
import nl.java.shakespearelang.parser.line.Exit;
import nl.java.shakespearelang.parser.line.InputStatement;
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
    private Map<CharacterInPlay, Integer> characters;
    private List<CharacterInPlay> personaeOnStage = new ArrayList<>();

    public PlayPerformer(Play play) throws IOException {
        this.play = play;
        this.wordlist = new Wordlist();
        initializeCharacters(play.getCharacters());
    }

    public void performPlay() throws IOException {
        ActSceneLine actSceneLine = new ActSceneLine(1, 1, 1);

        while (!actSceneLine.isExeunt()) {
            actSceneLine = performLine(actSceneLine);
        }
    }

//    private ActSceneLine performScene(ActSceneLine actSceneLine) throws IOException {
//        ActSceneLine newActSceneLine;
//
//        Scene scene = play.getAct(actSceneLine.getAct()).getScene(actSceneLine.getScene());
//        for (Line line : scene.getLines()) {
//            newActSceneLine = performLine(line);
//            if (newActSceneLine != null) {
//                return newActSceneLine;
//            }
//        }
//
//        return findNextLine(actSceneLine);
//    }

    private ActSceneLine findNextLine(ActSceneLine actSceneLine) {
        if ((play.getAct(actSceneLine.getAct()).getScene(actSceneLine.getScene()).getNumberOflines()) > actSceneLine.getLine()) {
            return ActSceneLine.next(actSceneLine);
        } else if (play.getAct(actSceneLine.getAct()).getNumberOfScenes() > actSceneLine.getScene()) {
            return new ActSceneLine(actSceneLine.getAct(), actSceneLine.getScene() + 1, 1);
        } else if (play.getNumberOfActs() > actSceneLine.getAct()) {
            return new ActSceneLine(actSceneLine.getAct() + 1, 1, 1);
        } else {
            return new ActSceneLine();
        }
    }

    private void initializeCharacters(Map<CharacterInPlay, Integer> characters) {
        for (CharacterInPlay character : characters.keySet()) {
            if (!wordlist.isCharacter(character)) {
                throw new RuntimeException("Character " + character + " is not a Shakespeare personae!");
            }
        }
        this.characters = characters;
    }

    private ActSceneLine performLine(ActSceneLine actSceneLine) throws IOException {
        Line line = play.getAct(actSceneLine.getAct()).getScene(actSceneLine.getScene()).getLine(actSceneLine.getLine());
        if (line instanceof Enter) {
            personaeOnStage.addAll(((Enter) line).getCharacters());
        } else if (line instanceof Exit) {
            exitPersonae((Exit) line);
        } else if (line instanceof OutputStatement) {
            performStatement(line, characters.get(getObjectOfLine(line.getSubject())));
        } else if (line instanceof Assignment) {
            CharacterInPlay object = getObjectOfLine(line.getSubject());
            AssignmentPerformer assignmentPerformer = new AssignmentPerformer((Assignment) line, characters, characters.get(object), wordlist);
            characters.replace(object, assignmentPerformer.performAssignment());
        } else if (line instanceof InputStatement) {
            CharacterInPlay object = getObjectOfLine(line.getSubject());
            InputStatementPerformer inputStatementPerformer = new InputStatementPerformer((InputStatement) line);
            characters.replace(object, inputStatementPerformer.performInputStatement());
        } else if (line instanceof Conditional) {
            ConditionalPerformer conditionalPerformer = new ConditionalPerformer((Conditional) line, characters);
            conditionalPerformer.performConditional();
        } else {
            throw new RuntimeException("unknown line type: " + line.getClass().getSimpleName());
        }
        return findNextLine(actSceneLine);
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

    private CharacterInPlay getObjectOfLine(CharacterInPlay subject) {
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
