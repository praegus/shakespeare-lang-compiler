package nl.java.shakespearelang.executor;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static nl.java.shakespearelang.executor.OperatorType.*;
import static nl.java.shakespearelang.parser.line.TextSimplifiers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssignmentPerformerStaticFunctionsTest {

    @Test
    public void if_no_assignment_is_present_nothing_happens() {
        List<String> result = AssignmentPerformer.removeAssignmentOperator(new ArrayList<>(), "");
        assertThat(result).hasSize(0);
    }

    @Test
    public void if_you_are_as_as_is_present_it_is_cleaned() {
        List<String> result =
                AssignmentPerformer.removeAssignmentOperator(new ArrayList<>(asList("you", "are", "as", "idiotic", "as", "a", "pear")), "you are as idiotic as a pear");
        assertThat(result).containsExactly("a", "pear");
    }

    @Test
    public void if_you_is_present_it_is_cleaned() {
        List<String> result = AssignmentPerformer.removeAssignmentOperator(new ArrayList<>(asList("you", "pear")), "you pear");
        assertThat(result).containsExactly("pear");
    }

    @Test
    public void if_thou_art_as_as_is_present_it_is_cleaned() {
        List<String> result =
                AssignmentPerformer.removeAssignmentOperator(new ArrayList<>(asList("thou", "art", "as", "idiotic", "as", "a", "pear")), "thou art as idiotic as a pear");
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
        List<OperatorType> result = AssignmentPerformer.setOperators(asList("fat", "little", "fine", "pig", "king", "father"), new Wordlist());
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
        assertThat(result).containsExactly(OBJECT_VALUE);
    }

    @Test
    public void characters_are_converted_to_operators() throws IOException {
        List<OperatorType> result = AssignmentPerformer.setOperators(singletonList("Romeo"), new Wordlist());
        assertThat(result).containsExactly(OperatorType.CHARACTER.setCharacterInPlay("Romeo"));
    }

    @Test
    public void if_operators_are_summarizeed_calculations_with_adjectives_and_nouns_are_performed_correctly_and_other_operators_are_left_in_place() {
        List<Object> result = AssignmentPerformer.summarizeOperators(asList(
                SUBTRACT,
                SQUARE,
                SUBTRACT,
                ADJECTIVE,
                POSITIVE_NOUN,
                ADJECTIVE,
                ADJECTIVE,
                NEGATIVE_NOUN,
                CUBE,
                ADJECTIVE,
                ADJECTIVE,
                NEGATIVE_NOUN));
        assertThat(result.get(0)).isEqualTo(SUBTRACT);
        assertThat(result.get(1)).isEqualTo(SQUARE);
        assertThat(result.get(2)).isEqualTo(SUBTRACT);
        assertThat(result.get(3)).isEqualTo(2);
        assertThat(result.get(4)).isEqualTo(-4);
        assertThat(result.get(5)).isEqualTo(CUBE);
        assertThat(result.get(6)).isEqualTo(-4);
    }
    //the difference between the square of the difference between my little pony and your big hairy hound and the cube of your sorry little codpiece.

    @Test
    public void operations_are_performed_from_back_to_front() {
        int result = AssignmentPerformer.executeOperations(new ArrayList<>(asList(SUBTRACT, SQUARE, SUBTRACT, 2, -4, CUBE, -4)));
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void if_a_square_operation_is_performed_the_result_is_the_square() {
        int result = AssignmentPerformer.executeOperations(new ArrayList<>(asList(SQUARE, 3)));
        assertThat(result).isEqualTo(9);
    }

    @Test
    public void if_a_cube_operation_is_performed_the_result_is_the_cube() {
        int result = AssignmentPerformer.executeOperations(new ArrayList<>(asList(CUBE, 3)));
        assertThat(result).isEqualTo(27);
    }

    @Test
    public void if_a_add_operation_is_performed_the_result_is_the_addition() {
        int result = AssignmentPerformer.executeOperations(new ArrayList<>(asList(OperatorType.ADD, 3, 4)));
        assertThat(result).isEqualTo(7);
    }

    @Test
    public void if_a_subtract_operation_is_performed_the_result_is_the_cube() {
        int result = AssignmentPerformer.executeOperations(new ArrayList<>(asList(OperatorType.SUBTRACT, 3, 4)));
        assertThat(result).isEqualTo(-1);
    }

    @Test
    public void if_a_multiply_operation_is_performed_the_result_is_the_cube() {
        int result = AssignmentPerformer.executeOperations(new ArrayList<>(asList(OperatorType.MULTIPLY, 3, 4)));
        assertThat(result).isEqualTo(12);
    }

    @Test
    public void if_a_divide_operation_is_performed_the_result_is_the_cube() {
        int result = AssignmentPerformer.executeOperations(new ArrayList<>(asList(OperatorType.DIVIDE, 4, 4)));
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void if_the_operator_is_unknown_an_exception_is_expected() {
        assertThatThrownBy(() -> AssignmentPerformer.executeOperations(new ArrayList<>(asList(3.4, 4, 4))))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unknown operator type!");
    }
}