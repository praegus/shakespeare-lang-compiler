package nl.java.shakespearelang.executor.assignment;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OperatorType {

    private String type;
    private String name;

    public static final OperatorType ADD = new OperatorType("ADD");
    public static final OperatorType MULTIPLY = new OperatorType("MULTIPLY");
    public static final OperatorType SUBTRACT = new OperatorType("SUBTRACT");
    public static final OperatorType DIVIDE = new OperatorType("DIVIDE");
    public static final OperatorType SQUARE = new OperatorType("SQUARE");
    public static final OperatorType QUOTIENT = new OperatorType("QUOTIENT");
    public static final OperatorType CUBE = new OperatorType("CUBE");

    public static final OperatorType ADJECTIVE = new OperatorType("AJECTIVE");
    public static final OperatorType NEGATIVE_NOUN = new OperatorType("NEGATIVE_NOUN");
    public static final OperatorType POSITIVE_NOUN = new OperatorType("POSITIVE_NOUN");
    public static final OperatorType OBJECT_VALUE = new OperatorType("OBJECT_VALUE");
    public static final OperatorType AND = new OperatorType("AND");

    public static final OperatorType CHARACTER = new OperatorType("CHARACTER");

    private OperatorType(String type) {
        this.type = type;
    }

    public OperatorType setName(String name) {
        this.name = name;
        return this;
    }
}
