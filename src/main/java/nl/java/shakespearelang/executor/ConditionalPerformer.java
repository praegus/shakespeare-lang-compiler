package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.CharacterInPlay;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Conditional;

import java.util.Map;

public class ConditionalPerformer {
    private final Conditional conditional;
    private final Map<CharacterInPlay, Integer> characters;
    private CharacterInPlay object;
    private Wordlist wordlist;

    public ConditionalPerformer(Conditional line, Map<CharacterInPlay, Integer> characters, CharacterInPlay object, Wordlist wordlist) {
        this.conditional = line;
        this.characters = characters;
        this.object = object;
        this.wordlist = wordlist;
    }

    public boolean performConditional() {
        int firstvalue = decideFirstParameter();
        int secondValue = decideLastParameter();
        Comparator comparator = decideComparator();

        if (conditional.getLine().contains(" not ")) {
            throw new RuntimeException("implement me!");
        }

        if (comparator.equals(Comparator.GREATER_THAN)) {
            return firstvalue > secondValue;
        } else if (comparator.equals(Comparator.SMALLER_THAN)) {
            return firstvalue < secondValue;
        } else if (comparator.equals(Comparator.EQUALS)) {
            return firstvalue == secondValue;
        } else {
            throw new RuntimeException("not implemented");
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

        throw new RuntimeException("cannot decide comparator");
    }

    private int decideFirstParameter() {
        if (conditional.getLine().startsWith("is the")) {
            return computeValue(conditional.getLine().substring(0, conditional.getLine().indexOf("as")).trim().replace("is ", ""));
        }

        if (conditional.getLine().startsWith("art thou")) {
            return characters.get(object);
        } else if (conditional.getLine().startsWith("am i")) {
            return characters.get(conditional.getSubject());
        } else {
            throw new RuntimeException("cannot decide value of first parameter!");
        }
    }

    private int decideLastParameter() {
        String lastParameter = getLastParameterText();
        if (lastParameter.equals("you")) {
            return characters.get(object);
        } else if (characters.get(new CharacterInPlay(lastParameter)) != null) {
            return characters.get(new CharacterInPlay(lastParameter));
        } else if (lastParameter.equals("nothing")) {
            return 0;
        } else {
            throw new RuntimeException("cannot decide value of last parameter!");
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
            throw new RuntimeException("cannot decide value of last parameter!");
        }
        return lastParameter;
    }

    enum Comparator {
        GREATER_THAN,
        SMALLER_THAN,
        EQUALS
    }

    private int computeValue(String line) {
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(new Assignment(conditional.getSubject(), line), characters, characters.get(object), wordlist);
        return assignmentPerformer.performAssignment();
    }
}
