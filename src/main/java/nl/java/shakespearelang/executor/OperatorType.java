package nl.java.shakespearelang.executor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.java.shakespearelang.CharacterInPlay;

@Getter
@EqualsAndHashCode
public class OperatorType {
    private String type;
    private CharacterInPlay characterInPlay;

    public static final OperatorType ADD = new OperatorType("ADD");
    public static final OperatorType MULTIPLY = new OperatorType("MULTIPLY");
    public static final OperatorType SUBTRACT = new OperatorType("SUBTRACT");
    public static final OperatorType DIVIDE = new OperatorType("DIVIDE");
    public static final OperatorType SQUARE = new OperatorType("SQUARE");
    public static final OperatorType QUOTIENT = new OperatorType("QUOTIENT");
    public static final OperatorType CUBE = new OperatorType("CUBE");
    public static final OperatorType TWICE = new OperatorType("TWICE");
    public static final OperatorType MODULO = new OperatorType("MODULO");

    public static final OperatorType ADJECTIVE = new OperatorType("AJECTIVE");
    public static final OperatorType NEGATIVE_NOUN = new OperatorType("NEGATIVE_NOUN");
    public static final OperatorType POSITIVE_NOUN = new OperatorType("POSITIVE_NOUN");
    public static final OperatorType OBJECT_VALUE = new OperatorType("OBJECT_VALUE");
    public static final OperatorType SUBJECT_VALUE = new OperatorType("SUBJECT_VALUE");


    public static final OperatorType CHARACTER = new OperatorType("CHARACTER");

    private OperatorType(String type) {
        this.type = type;
    }

    public OperatorType setCharacterInPlay(String characterInPlay) {
        this.characterInPlay = new CharacterInPlay(characterInPlay);
        return this;
    }
}
