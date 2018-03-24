package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.parser.line.Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignmentExecutor {
    private final Wordlist wordlist;
    private List<OperatorType> operators = new ArrayList<>();

    public AssignmentExecutor(Wordlist wordlist, Assignment line) throws Exception {
        this.wordlist = wordlist;
        List<String> words = new ArrayList<>(Arrays.asList(line.getWords()));
        if (words.size() > 5 && words.get(0).equals("you") && words.get(1).equals("are")) {
            words.remove(0);
            words.remove(0);
            words.remove(0);
            words.remove(0);
            words.remove(0);
            setOperators(words);

        } else if (words.get(0).equals("you")) {
            words.remove(0);
            setOperators(words);
        }

    }

    private void setOperators(List<String> words) throws Exception {
        for (String word : words) {
            if (wordlist.isAdjective(word)) {
                operators.add(OperatorType.ADJECTIVE);
            } else if (wordlist.isNegativeNoun(word)) {
                operators.add(OperatorType.NEGATIVE_NOUN);
            } else if (wordlist.isPositiveNoun(word)) {
                operators.add(OperatorType.POSITIVE_NOUN);
            } else if (word.equals("THEDIFFERENCEBETWEENA")) {
                operators.add(OperatorType.SUBTRACT);
            } else if (word.equals("ANDTHYSELF")) {
                operators.add(OperatorType.OBJECT_VALUE);
            } else {
                throw new Exception("The word " + word + " is unknown!");
            }
        }
    }

    public int getValue() throws Exception {
        Operation operation = new Operation(operators);

        return 0;
    }
}
