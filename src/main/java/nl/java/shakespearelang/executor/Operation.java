package nl.java.shakespearelang.executor;

import java.util.ArrayList;
import java.util.List;

public class Operation {
    private List<Object> operatorsAndNumbers = new ArrayList<>();

    public Operation(List<OperatorType> operators) {

        int numberOfAdjectives = 0;
        for (OperatorType operator : operators) {
            if (operator.equals(OperatorType.ADJECTIVE)) {
                numberOfAdjectives++;
            } else if (operator.equals(OperatorType.POSITIVE_NOUN)) {
                operatorsAndNumbers.add((int) Math.pow(2, numberOfAdjectives));
                numberOfAdjectives = 0;
            } else if (operator.equals(OperatorType.NEGATIVE_NOUN)) {
                operatorsAndNumbers.add((int) Math.pow(-2, numberOfAdjectives));
                numberOfAdjectives = 0;
            } else {
                operatorsAndNumbers.add(operator);
            }
        }
    }
}
