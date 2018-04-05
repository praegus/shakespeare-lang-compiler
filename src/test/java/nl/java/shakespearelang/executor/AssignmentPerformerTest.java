package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.executor.assignment.OperatorType;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static nl.java.shakespearelang.executor.assignment.OperatorType.*;
import static nl.java.shakespearelang.parser.line.TextSimplifiers.AND;
import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssignmentPerformerTest {

    @Test
    public void if_no_assignment_is_present_nothing_happens() {
        List<String> result = AssignmentPerformer.cleanAssignment(new ArrayList<>());
        assertThat(result).hasSize(0);
    }

    @Test
    public void if_you_are_as_as_is_present_it_is_cleaned() {
        List<String> result = AssignmentPerformer.cleanAssignment(new ArrayList<>(asList("you", "are", "as", "idiotic", "as", "a", "pear")));
        assertThat(result).containsExactly("a", "pear");
    }

    @Test
    public void if_you_is_present_it_is_cleaned() {
        List<String> result = AssignmentPerformer.cleanAssignment(new ArrayList<>(asList("you", "pear")));
        assertThat(result).containsExactly("pear");
    }

    @Test
    public void if_thou_art_as_as_is_present_it_is_cleaned() {
        List<String> result = AssignmentPerformer.cleanAssignment(new ArrayList<>(asList("thou", "art", "as", "idiotic", "as", "a", "pear")));
        assertThat(result).containsExactly("a", "pear");
    }

    @Test
    public void if_a_word_is_unknown_an_exception_is_thrown() {
        assertThatThrownBy(() -> AssignmentPerformer.setOperators(singletonList("unknown"), new Wordlist()))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("The word 'unknown' is unknown!");
    }

    @Test
    public void adjectives_and_nouns_are_converted_to_operators() throws IOException {
        List<OperatorType> result = AssignmentPerformer.setOperators(asList("fat", "little", "fine", "pig", "King", "father"), new Wordlist());
        assertThat(result).containsExactly(ADJECTIVE, ADJECTIVE, ADJECTIVE, NEGATIVE_NOUN, POSITIVE_NOUN, POSITIVE_NOUN);
    }

    @Test
    public void calculation_keywords_are_converted_to_operators() throws IOException {
        List<OperatorType> result = AssignmentPerformer.setOperators(asList(THEDIFFERENCEBETWEEN, THESUMOF, THEPRODUCTOF, THESQUAREOF, THEQUOTIENTBETWEEN, THECUBEOF), new Wordlist());
        assertThat(result).containsExactly(SUBTRACT, ADD, MULTIPLY, SQUARE, DIVIDE, CUBE);
    }

    @Test
    public void and_and_yourself_are_converted_to_operators() throws IOException {
        List<OperatorType> result = AssignmentPerformer.setOperators(asList(AND, YOURSELF), new Wordlist());
        assertThat(result).containsExactly(OperatorType.AND, OBJECT_VALUE);
    }

    @Test
    public void characters_are_converted_to_operators() throws IOException {
        List<OperatorType> result = AssignmentPerformer.setOperators(singletonList("Romeo"), new Wordlist());
        assertThat(result).containsExactly(OperatorType.CHARACTER.setName("Romeo"));
    }
}