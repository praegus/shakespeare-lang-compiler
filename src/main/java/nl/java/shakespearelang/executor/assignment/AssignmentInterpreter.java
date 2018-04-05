package nl.java.shakespearelang.executor.assignment;

import nl.java.shakespearelang.executor.Wordlist;
import nl.java.shakespearelang.parser.line.Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;

public class AssignmentInterpreter {
    private final Wordlist wordlist;
    private List<Object> operatorsAndNumbers;

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
            } else if (word.equals(THEQUOTIENTBETWEEN)) {
                operators.add(OperatorType.DIVIDE);
            } else if (word.equals(THECUBEOF)) {
                operators.add(OperatorType.CUBE);
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

    public int getValue(Integer objectValue, Map<String, Integer> characters) {
        while (operatorsAndNumbers.contains(OperatorType.OBJECT_VALUE)) {
            operatorsAndNumbers.set(operatorsAndNumbers.indexOf(OperatorType.OBJECT_VALUE), objectValue);
        }
        while (operatorsAndNumbers.contains(OperatorType.CHARACTER)) {
            int index = operatorsAndNumbers.indexOf(OperatorType.CHARACTER);
            OperatorType character = (OperatorType) operatorsAndNumbers.get(index);
            operatorsAndNumbers.set(index, characters.get(character.getName()));
        }
        return calculateValue();

    }

    private int calculateValue() {
        voerBerekeningenUit();
        if (operatorsAndNumbers.size() == 1) {
            return (int) operatorsAndNumbers.get(0);
        }
        throw new RuntimeException("foutje!");
    }

    private void voerBerekeningenUit() {
        while (operatorsAndNumbers.contains(OperatorType.ADD)
            || operatorsAndNumbers.contains(OperatorType.SUBTRACT)
            || operatorsAndNumbers.contains(OperatorType.MULTIPLY)
            || operatorsAndNumbers.contains(OperatorType.DIVIDE)
            || operatorsAndNumbers.contains(OperatorType.SQUARE)
            || operatorsAndNumbers.contains(OperatorType.CUBE)) {
            for (int i = 0; i < operatorsAndNumbers.size(); i++) {
                executeOperator(OperatorType.SQUARE, i);
                executeOperatorCube(i);
                executeOperator(OperatorType.MULTIPLY, i);
                executeOperator(OperatorType.DIVIDE, i);
                executeOperator(OperatorType.ADD, i);
                executeOperator(OperatorType.SUBTRACT, i);
            }
        }
    }

    private void executeOperatorCube(int index) {
        if (operatorsAndNumbers.get(index).equals(OperatorType.CUBE) && operatorsAndNumbers.get(index + 1) instanceof Integer) {
            operatorsAndNumbers.set(index, (int) operatorsAndNumbers.get(index + 1) * (int) operatorsAndNumbers.get(index + 1));
            operatorsAndNumbers.remove(index + 1);
        }
    }

    private void executeOperator(OperatorType operatorType, int index) {
        if (operatorsAndNumbers.get(index).equals(operatorType) && operatorExecutionIsPossible(operatorType, index)) {
            int result = 0;
            if (operatorType.equals(OperatorType.ADD)) {
                result = (int) operatorsAndNumbers.get(index + 1) + (int) operatorsAndNumbers.get(index + 3);
            } else if (operatorType.equals(OperatorType.SUBTRACT)) {
                result = (int) operatorsAndNumbers.get(index + 1) - (int) operatorsAndNumbers.get(index + 3);
            } else if (operatorType.equals(OperatorType.MULTIPLY)) {
                result = (int) operatorsAndNumbers.get(index + 1) * (int) operatorsAndNumbers.get(index + 3);
            } else if (operatorType.equals(OperatorType.DIVIDE)) {
                result = (int) operatorsAndNumbers.get(index + 1) / (int) operatorsAndNumbers.get(index + 3);
            } else if (operatorType.equals(OperatorType.SQUARE)) {
                result = (int) Math.pow((int) operatorsAndNumbers.get(index + 1), (int) operatorsAndNumbers.get(index + 3));
            }
            operatorsAndNumbers.set(index, result);

            for (int j = 0; j < 3; j++) {
                operatorsAndNumbers.remove(index + 1);
            }
        }
    }

    private boolean operatorExecutionIsPossible(OperatorType operator, int indexOfOperator) {
        if (indexOfOperator == -1) {
            return false;
        }
        if (indexOfOperator + 3 >= operatorsAndNumbers.size()) {
            throw new RuntimeException("Operator " + operator.getType() + " is missing values!");
        }
        Object shouldBeInteger1 = operatorsAndNumbers.get(indexOfOperator + 1);
        Object shouldBeAnd = operatorsAndNumbers.get(indexOfOperator + 2);
        Object shouldBeInteger2 = operatorsAndNumbers.get(indexOfOperator + 3);

        return shouldBeInteger1 instanceof Integer && shouldBeAnd.equals(OperatorType.AND) && shouldBeInteger2 instanceof Integer;
    }
}
