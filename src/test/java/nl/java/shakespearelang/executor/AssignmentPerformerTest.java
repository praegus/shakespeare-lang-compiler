package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.CharacterInPlay;
import nl.java.shakespearelang.parser.line.Assignment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AssignmentPerformerTest {

    private static Wordlist wordlist;

    @BeforeClass
    public static void before() throws IOException {
        wordlist = new Wordlist();
    }

    @Test
    public void when_a_complicated_set_of_calculations_is_performed_the_result_is_correct() {
        Assignment assignment = new Assignment(
                new CharacterInPlay("hamlet"),
                "you are as small as the difference between the square of the difference between my little pony and your big hairy hound and the cube of your sorry little codpiece");
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, null, wordlist);
        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void when_a_character_is_named_the_value_of_that_character_is_used() {
        Assignment assignment = new Assignment(new CharacterInPlay("hamlet"), "you are as small as Juliet");
        Map<CharacterInPlay, Integer> characters = new HashMap<>();
        characters.put(new CharacterInPlay("juliet"), 3);
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, null, wordlist);
        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void when_an_subject_is_named_the_value_of_that_object_is_used() {
        Assignment assignment = new Assignment(new CharacterInPlay("hamlet"), "you are as small as thyself");
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, 3, wordlist);
        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void remainder_of_quotient_and_key_me_equal_to_zero() {
        Assignment assignment = new Assignment(new CharacterInPlay("hamlet"), "the remainder of the quotient between juliet and me");

        Map<CharacterInPlay, Integer> characters = new HashMap<>();
        characters.put(new CharacterInPlay("juliet"), 9);
        characters.put(new CharacterInPlay("hamlet"), 3);

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void remainder_of_quotient_and_key_me_larger_than_zero() {
        Assignment assignment = new Assignment(new CharacterInPlay("hamlet"), "the remainder of the quotient between juliet and me");

        Map<CharacterInPlay, Integer> characters = new HashMap<>();
        characters.put(new CharacterInPlay("juliet"), 10);
        characters.put(new CharacterInPlay("hamlet"), 3);

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void square_root() {
        Assignment assignment = new Assignment(new CharacterInPlay("hamlet"), "you are as villainous as the square root of romeo");

        Map<CharacterInPlay, Integer> characters = new HashMap<>();
        characters.put(new CharacterInPlay("romeo"), 9);

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void nothing(){
        Assignment assignment = new Assignment(new CharacterInPlay("hamlet"), "thou art as rotten as the difference between nothing and the sum of a snotty stinking half-witted hog and a small toad");

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(10);

    }
}