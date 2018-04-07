package nl.java.shakespearelang.executor;

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
        List<String> words = removeAssignmentOperator(new ArrayList<>(Arrays.asList(line.getWords())), line.getLine());
        List<OperatorType> operators = setOperators(words, wordlist);
        List<Object> summarizedOperators = summarizeOperators(operators);
        replaceObjectWithValue(summarizedOperators);
        replaceCharacterWithValue(summarizedOperators);
        return executeOperations(summarizedOperators);
    }

    private void replaceCharacterWithValue(List<Object> summarizedOperators) {
        for (int i = 0; i < summarizedOperators.size(); i++) {
            if (summarizedOperators.get(i).equals(OperatorType.CHARACTER)) {
                OperatorType character = (OperatorType) summarizedOperators.get(i);
                summarizedOperators.set(i, characters.get(character.getName()));
            }
        }
    }

    private void replaceObjectWithValue(List<Object> summarizedOperators) {
        for (int i = 0; i < summarizedOperators.size(); i++) {
            if (summarizedOperators.get(i).equals(OperatorType.OBJECT_VALUE)) {
                summarizedOperators.set(i, objectValue);
            }
        }
    }

    static List<String> removeAssignmentOperator(List<String> words, String line) {
        if (line.matches("you are as \\w+ as.*")) {
            words = words.subList(5, words.size());
        } else if (line.matches("thou art as \\w+ as.*")) {
            words = words.subList(5, words.size());
        } else if (line.startsWith("you")) {
            words.remove(0);
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
                // do nothing operators.add(OperatorType.AND);
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
            } else if (word.equals(THEQUOTIENTBETWEEN)) {
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

    static int executeOperations(List<Object> objects) {
        int i = objects.size() - 1;
        while (objects.size() != 1) {
            if (objects.get(i) instanceof OperatorType) {
                if (objects.get(i).equals(OperatorType.SQUARE)) {
                    objects.set(i, (int) Math.pow((Integer) objects.get(i + 1), 2));
                } else if (objects.get(i).equals(OperatorType.CUBE)) {
                    objects.set(i, (int) Math.pow((Integer) objects.get(i + 1), 3));
                } else if (objects.get(i).equals(OperatorType.ADD)) {
                    objects.set(i, (int) objects.get(i + 1) + (int) objects.get(i + 2));
                    objects.remove(i + 1);
                } else if (objects.get(i).equals(OperatorType.SUBTRACT)) {
                    objects.set(i, (int) objects.get(i + 1) - (int) objects.get(i + 2));
                    objects.remove(i + 1);
                } else if (objects.get(i).equals(OperatorType.MULTIPLY)) {
                    objects.set(i, (int) objects.get(i + 1) * (int) objects.get(i + 2));
                    objects.remove(i + 1);
                } else if (objects.get(i).equals(OperatorType.DIVIDE)) {
                    objects.set(i, (int) objects.get(i + 1) / (int) objects.get(i + 2));
                    objects.remove(i + 1);
                }
                objects.remove(i + 1);
                i = objects.size() - 1;
            } else if (objects.get(i) instanceof Integer) {
                i--;
            } else {
                throw new RuntimeException("Unknown operator type!");
            }
        }
        return (int) objects.get(0);
    }
}
