package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.executor.assignment.OperatorType;
import nl.java.shakespearelang.parser.line.Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;

public class AssignmentPerformer {
    private final Integer objectValue;
    private final Assignment line;
    private final Map<String, Integer> characters;
    private final Wordlist wordlist;

    public AssignmentPerformer(Assignment line, Map<String, Integer> characters, Integer objectValue, Wordlist wordlist) {
        this.objectValue = objectValue;
        this.line = line;
        this.characters = characters;
        this.wordlist = wordlist;
    }

    public int performAssignment() {
        List<String> words = cleanAssignment(new ArrayList<>(Arrays.asList(line.getWords())));
        List<OperatorType> operators = setOperators(words, wordlist);
        List<Object> summarizedOperators = summarizeOperators(operators);
        return 0;
    }

    static List<String> cleanAssignment(List<String> words) {
        if (words.size() > 4 && words.get(0).equals("you") && words.get(1).equals("are") && words.get(2).equals("as") && words.get(4).equals("as")) {
            for (int i = 0; i < 5; i++) {
                words.remove(0);
            }
        } else if (words.size() > 0 && words.get(0).equals("you")) {
            words.remove(0);
        } else if (words.size() > 4 && words.get(0).equals("thou") && words.get(1).equals("art") && words.get(2).equals("as") && words.get(4).equals("as")) {
            for (int i = 0; i < 5; i++) {
                words.remove(0);
            }
        }
        return words;
    }

    static List<OperatorType> setOperators(List<String> words, Wordlist wordlist) {
        List<OperatorType> operators = new ArrayList<>();
        for (String word : words) {
            if (wordlist.isAdjective(word)) {
                operators.add(OperatorType.ADJECTIVE);
            } else if (wordlist.isNegativeNoun(word)) {
                operators.add(OperatorType.NEGATIVE_NOUN);
            } else if (wordlist.isPositiveNoun(word)) {
                operators.add(OperatorType.POSITIVE_NOUN);
            } else if (wordlist.isRedundant(word)) {
                // do nothing
            } else if (word.equals(AND)) {
                operators.add(OperatorType.AND);
            } else if (word.equals(YOURSELF)) {
                operators.add(OperatorType.OBJECT_VALUE);
            } else if (wordlist.isCharacter(word)) {
                operators.add(OperatorType.CHARACTER.setName(word));
            } else if (word.equals(THEDIFFERENCEBETWEEN)) {
                operators.add(OperatorType.SUBTRACT);
            } else if (word.equals(THESUMOF)) {
                operators.add(OperatorType.ADD);
            } else if (word.equals(THEPRODUCTOF)) {
                operators.add(OperatorType.MULTIPLY);
            } else if (word.equals(THESQUAREOF)) {
                operators.add(OperatorType.SQUARE);
            } else if (word.equals(THEQUOTIENTOF)) {
                operators.add(OperatorType.DIVIDE);
            } else if (word.equals(THECUBEOF)) {
                operators.add(OperatorType.CUBE);
            } else {
                throw new RuntimeException("The word '" + word + "' is unknown!");
            }
        }
        return operators;
    }

    static List<Object> summarizeOperators(List<OperatorType> operators) {
        List<Object> operatorsAndNumbers = new ArrayList<>();
        int numberOfAdjectives = 0;
        for (OperatorType operator : operators) {
            if (operator.equals(OperatorType.ADJECTIVE)) {
                numberOfAdjectives++;
            } else if (operator.equals(OperatorType.POSITIVE_NOUN)) {
                operatorsAndNumbers.add((int) Math.pow(2, numberOfAdjectives));
                numberOfAdjectives = 0;
            } else if (operator.equals(OperatorType.NEGATIVE_NOUN)) {
                operatorsAndNumbers.add((int) Math.pow(2, numberOfAdjectives) * -1);
                numberOfAdjectives = 0;
            } else {
                operatorsAndNumbers.add(operator);
            }
        }
        return operatorsAndNumbers;
    }
}
