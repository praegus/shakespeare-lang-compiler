package nl.java.shakespearelang.executor.assignment;

import nl.java.shakespearelang.executor.Wordlist;
import nl.java.shakespearelang.parser.line.Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;

public class AssignmentInterpreter {
    private final Wordlist wordlist;
    List<Object> operatorsAndNumbers;

    public AssignmentInterpreter(Wordlist wordlist, Assignment line) {
        this.wordlist = wordlist;
        List<String> words = new ArrayList<>(Arrays.asList(line.getWords()));
        if (words.size() > 5 && words.get(0).equals("you") && words.get(1).equals("are")) {
            for (int i = 0; i < 5; i++) {
                words.remove(0);
            }
        } else if (words.get(0).equals("you")) {
            words.remove(0);
        } else if (words.get(0).equals("thou") && words.get(1).equals("art")) {
            for (int i = 0; i < 5; i++) {
                words.remove(0);
            }
        }
        List<OperatorType> operators = setOperators(words);
        this.operatorsAndNumbers = summarizeOperators(operators);
    }

    private List<OperatorType> setOperators(List<String> words) {
        List<OperatorType> operators = new ArrayList<>();
        for (String word : words) {
            if (wordlist.isAdjective(word)) {
                operators.add(OperatorType.ADJECTIVE);
            } else if (wordlist.isNegativeNoun(word)) {
                operators.add(OperatorType.NEGATIVE_NOUN);
            } else if (wordlist.isPositiveNoun(word)) {
                operators.add(OperatorType.POSITIVE_NOUN);
            } else if (wordlist.isPossessive(word)) {
                // do nothing
            } else if (word.equals(THEDIFFERENCEBETWEEN)) {
                operators.add(OperatorType.SUBTRACT);
            } else if (word.equals(THESUMOF)) {
                operators.add(OperatorType.ADD);
            } else if (word.equals(AND)) {
                operators.add(OperatorType.AND);
            } else if (word.equals(YOURSELF)) {
                operators.add(OperatorType.OBJECT_VALUE);
            } else {
                throw new RuntimeException("The word " + word + " is unknown!");
            }
        }
        return operators;
    }

    private List<Object> summarizeOperators(List<OperatorType> operators) {
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

    public int getValue(Integer objectValue) {
        while (operatorsAndNumbers.contains(OperatorType.OBJECT_VALUE)) {
            operatorsAndNumbers.set(operatorsAndNumbers.indexOf(OperatorType.OBJECT_VALUE), objectValue);
        }
        return calculateValue();

    }

    private int calculateValue() {
        try {
            if (operatorsAndNumbers.size() == 1) {
                return (int) operatorsAndNumbers.get(0);
            } else if (subtractOrAdditionIsPossible(OperatorType.ADD)) {
                return add();
            } else if (subtractOrAdditionIsPossible(OperatorType.SUBTRACT)) {
                return subtract();
            } else {
                throw new RuntimeException("unsupported operation in line! Operators are: " + operatorsAndNumbers.toString());
            }
        } catch (ClassCastException e) {
            throw new RuntimeException("Waardes in de operators kunnen niet gecast worden naar nummers! " + operatorsAndNumbers.toString());
        }
    }

    private int subtract() {
        while (subtractOrAdditionIsPossible(OperatorType.SUBTRACT)) {
            int indexOfSubtract = operatorsAndNumbers.indexOf(OperatorType.SUBTRACT);
            operatorsAndNumbers.set(indexOfSubtract, (int) operatorsAndNumbers.get(indexOfSubtract + 1) - (int) operatorsAndNumbers.get(indexOfSubtract + 3));
            for (int i = 0; i < 3; i++) {
                operatorsAndNumbers.remove(indexOfSubtract + 1);
            }
        }
        return calculateValue();
    }

    private int add() {
        while (subtractOrAdditionIsPossible(OperatorType.ADD)) {
            int indexOfSubtract = operatorsAndNumbers.indexOf(OperatorType.ADD);
            operatorsAndNumbers.set(indexOfSubtract, (int) operatorsAndNumbers.get(indexOfSubtract + 1) + (int) operatorsAndNumbers.get(indexOfSubtract + 3));
            for (int i = 0; i < 3; i++) {
                operatorsAndNumbers.remove(indexOfSubtract + 1);
            }
        }
        return calculateValue();
    }

    private boolean subtractOrAdditionIsPossible(OperatorType operator) {
        int indexOfOperator = operatorsAndNumbers.indexOf(operator);
        if (indexOfOperator == -1) {
            return false;
        }
        if (indexOfOperator + 3 >= operatorsAndNumbers.size()) {
            throw new RuntimeException("Operator " + operator + " is missing values!");
        }
        Object shouldBeInteger1 = operatorsAndNumbers.get(indexOfOperator + 1);
        Object shouldBeAnd = operatorsAndNumbers.get(indexOfOperator + 2);
        Object shouldBeInteger2 = operatorsAndNumbers.get(indexOfOperator + 3);

        return shouldBeInteger1 instanceof Integer && shouldBeAnd.equals(OperatorType.AND) && shouldBeInteger2 instanceof Integer;
    }
}
