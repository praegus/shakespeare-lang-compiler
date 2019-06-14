package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.ExecutionException;
import nl.java.shakespearelang.parser.Characters;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Conditional;

public class ConditionalPerformer {
    private final Conditional conditional;
    private final Characters characters;
    private String object;
    private Wordlist wordlist;

    public ConditionalPerformer(Conditional line, Characters characters, String object, Wordlist wordlist) {
        this.conditional = line;
        this.characters = characters;
        this.object = object;
        this.wordlist = wordlist;
    }

    public boolean performConditional() {
        int firstvalue = decideFirstParameter();
        int secondValue = decideLastParameter();
        Comparator comparator = decideComparator();

        if (comparator.equals(Comparator.GREATER_THAN)) {
            return firstvalue > secondValue;
        } else if (comparator.equals(Comparator.SMALLER_THAN)) {
            return firstvalue < secondValue;
        } else if (comparator.equals(Comparator.EQUALS)) {
            return firstvalue == secondValue;
        } else {
            throw new ExecutionException("not implemented");
        }
    }

    private Comparator decideComparator() {

        if (conditional.getLine().contains("as")) {
            return Comparator.EQUALS;
        }

        if (wordlist.getPositiveComparatives().stream().anyMatch(e -> conditional.getLine().contains(e))) {
            return Comparator.GREATER_THAN;
        }
        if (wordlist.getPositiveAdjectives().stream().anyMatch(e -> conditional.getLine().contains(e))) {
            return Comparator.GREATER_THAN;
        }
        if (wordlist.getNegativeComparatives().stream().anyMatch(e -> conditional.getLine().contains(e))) {
            return Comparator.SMALLER_THAN;
        }

        if (wordlist.getNegativeAdjectives().stream().anyMatch(e -> conditional.getLine().contains(e))) {
            return Comparator.SMALLER_THAN;
        }

        throw new ExecutionException("cannot decide comparator");
    }

    private int decideFirstParameter() {
        if (conditional.getLine().startsWith("is the")) {
            return computeValue(conditional.getLine().substring(0, conditional.getLine().indexOf("as")).trim().replace("is ", ""));
        }

        if (conditional.getLine().startsWith("art thou") || conditional.getLine().startsWith("are you")) {
            return characters.getCharacter(object).getValue();
        } else if (conditional.getLine().startsWith("am i")) {
            return characters.getCharacter(conditional.getSubject()).getValue();
        } else if (conditional.getLine().startsWith("is")) {
            String possibleCharacter = conditional.getWords()[1];
            if (characters.isCharacter(possibleCharacter)) {
                return characters.getCharacter(possibleCharacter).getValue();
            }
        }
        throw new ExecutionException("cannot decide value of first parameter!");
    }

    private int decideLastParameter() {
        String lastParameter = getLastParameterText();
        if (lastParameter.equals("nothing")) {
            return 0;
        } else if (lastParameter.startsWith("a")) {
            return computeValue(lastParameter);
        } else if (lastParameter.equals("you")) {
            return characters.getCharacter(object).getValue();
        } else if (characters.isCharacter(lastParameter)) {
            return characters.getCharacter(lastParameter).getValue();
        } else {
            Assignment a = new Assignment(conditional.getSubject(), lastParameter);
            AssignmentPerformer performer = new AssignmentPerformer(a, characters, characters.getCharacter(object).getValue(), wordlist);
            return performer.performAssignment();
        }
    }

    private String getLastParameterText() {
        String lastParameter;
        if (conditional.getLine().contains("than")) {
            lastParameter = conditional.getLine().substring(conditional.getLine().indexOf("than ") + 5);
        } else if (conditional.getLine().contains("as")) {
            String vanafEersteAs = conditional.getLine().substring(conditional.getLine().indexOf("as ") + 3);
            lastParameter = vanafEersteAs.substring(vanafEersteAs.indexOf("as ") + 3);
        } else {
            throw new ExecutionException("cannot decide value of last parameter!");
        }
        return lastParameter;
    }

    enum Comparator {
        GREATER_THAN,
        SMALLER_THAN,
        EQUALS
    }

    private int computeValue(String line) {
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(new Assignment(conditional.getSubject(), line), characters, characters.getCharacter(object).getValue(), wordlist);
        return assignmentPerformer.performAssignment();
    }
}
