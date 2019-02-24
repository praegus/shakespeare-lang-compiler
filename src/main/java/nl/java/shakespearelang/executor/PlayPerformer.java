package nl.java.shakespearelang.executor;

import lombok.extern.slf4j.Slf4j;
import nl.java.shakespearelang.Characters;
import nl.java.shakespearelang.Character;
import nl.java.shakespearelang.parser.Play;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Conditional;
import nl.java.shakespearelang.parser.line.Enter;
import nl.java.shakespearelang.parser.line.Exit;
import nl.java.shakespearelang.parser.line.Goto;
import nl.java.shakespearelang.parser.line.InputStatement;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.OutputStatement;
import nl.java.shakespearelang.parser.line.Pop;
import nl.java.shakespearelang.parser.line.Push;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Slf4j
public class PlayPerformer {
    private final Play play;
    private Wordlist wordlist;
    private Characters characters = new Characters();
    private List<String> personaeOnStage = new ArrayList<>();
    private boolean condition = false;
    private Reader characterReader = new BufferedReader(new InputStreamReader(System.in));

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

    private void initializeCharacters(Characters characters) {
        for (Character character : characters) {
            if (!wordlist.isCharacter(character.getName())) {
                throw new RuntimeException("Character " + character.getName() + " is not a Shakespeare personae!");
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
            performStatement(line, characters.getCharacter(getObjectOfLine(line)).getValue());
        } else if (line instanceof Assignment) {
            String object = getObjectOfLine(line);
            AssignmentPerformer assignmentPerformer = new AssignmentPerformer((Assignment) line, characters, characters.getCharacter(object).getValue(), wordlist);
            int newValue = assignmentPerformer.performAssignment();
            characters.getCharacter(object).setValue(newValue);
        } else if (line instanceof InputStatement) {
            String object = getObjectOfLine(line);
            InputStatementPerformer inputStatementPerformer = new InputStatementPerformer((InputStatement) line);
            int newValue = inputStatementPerformer.performInputStatement(characterReader);
            characters.getCharacter(object).setValue(newValue);
        } else if (line instanceof Conditional) {
            String object = getObjectOfLine(line);
            ConditionalPerformer conditionalPerformer = new ConditionalPerformer((Conditional) line, characters, object, wordlist);
            condition = conditionalPerformer.performConditional();
        } else if (line instanceof Goto) {
            Goto gotoStatement = ((Goto) line);
            if (gotoStatement.conditionApplies(condition)) {
                return new ActSceneLine(actSceneLine.getAct(), gotoStatement.getRequestedScene(), 1);
            }
        } else if (line instanceof Push) {
        	String object = getObjectOfLine(line);
        	int newValue = characters.getCharacter(object).getValue();
        	characters.getCharacter(object).pushStack(newValue);
        } else if (line instanceof Pop) {
        	String object = getObjectOfLine(line);
        	characters.getCharacter(object).popStack();
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
            System.out.print((char) objectValue.intValue());
        }
    }

    private String getObjectOfLine(Line line) {
    	String subject = line.getSubject();
        if (personaeOnStage.size() != 2) {
            throw new RuntimeException("Number of characters on stage is not correct!");
        } else if (!personaeOnStage.contains(subject)) {
            throw new RuntimeException("Speaking person is not on stage!");
        }
        if (line.getLine().equals("Remember me")) {
        	return subject;
        } else {
        	int indexOfSubject = personaeOnStage.indexOf(subject);
	        if (indexOfSubject == 0) {
	            return personaeOnStage.get(1);
	        } else {
	            return personaeOnStage.get(0);
	        }
        }
    }
}
