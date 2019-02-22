package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.CharacterInPlay;
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

    // "art thou more cunning than the ghost"
    public boolean performConditional() {
        int firstvalue = decideFirstParameter();
        Comparator comparator = decideComparator();
        int secondValue = decideLastParameter();

        if (comparator.equals(Comparator.GREATER_THAN)) {
            return firstvalue > secondValue;
        } else if (comparator.equals(Comparator.SMALLER_THAN)) {
            return firstvalue < secondValue;
        } else {
            throw new RuntimeException("not implemented");
        }
    }

    private Comparator decideComparator() {
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
        if (conditional.getLine().startsWith("art thou")) {
            return characters.get(object);
        } else if (conditional.getLine().startsWith("am i")) {
            return characters.get(conditional.getSubject());
        } else {
            throw new RuntimeException("cannot decide value of first parameter!");
        }
    }

    private int decideLastParameter() {
        String lastParameter = conditional.getLine().substring(conditional.getLine().indexOf("than ") + 5);
        if (lastParameter.equals("you")) {
            return characters.get(object);
        } else if (characters.get(new CharacterInPlay(lastParameter)) != null) {
            return characters.get(new CharacterInPlay(lastParameter));
        } else {
            throw new RuntimeException("cannot decide value of last parameter!");
        }
    }

    enum Comparator {
        GREATER_THAN,
        SMALLER_THAN
    }
}
