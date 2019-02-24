package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.Characters;
import nl.java.shakespearelang.Character;
import nl.java.shakespearelang.parser.line.Assignment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AssignmentPerformerTest {

    private static Wordlist wordlist;

    @BeforeClass
    public static void before() throws IOException {
        wordlist = new Wordlist();
    }

    @Test
    public void when_a_complicated_set_of_calculations_is_performed_the_result_is_correct() {
        Assignment assignment = new Assignment("hamlet",
                "you are as small as the difference between the square of the difference between my little pony and your big hairy hound and the cube of your sorry little codpiece");
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, null, wordlist);
        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void when_a_character_is_named_the_value_of_that_character_is_used() {
        Assignment assignment = new Assignment("hamlet", "you are as small as Juliet");
        Characters characters = new Characters();
        characters.add(new Character("juliet", 3));
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, null, wordlist);
        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void when_an_subject_is_named_the_value_of_that_object_is_used() {
        Assignment assignment = new Assignment("hamlet", "you are as small as thyself");
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, 3, wordlist);
        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void remainder_of_quotient_and_key_me_equal_to_zero() {
        Assignment assignment = new Assignment("hamlet", "the remainder of the quotient between juliet and me");

        Characters characters = new Characters();
        characters.add(new Character("juliet", 9));
        characters.add(new Character("hamlet", 3));

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void remainder_of_quotient_and_key_me_larger_than_zero() {
        Assignment assignment = new Assignment("hamlet", "the remainder of the quotient between juliet and me");

        Characters characters = new Characters();
        characters.add(new Character("juliet", 10));
        characters.add(new Character("hamlet", 3));

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void square_root() {
        Assignment assignment = new Assignment("hamlet", "you are as villainous as the square root of romeo");

        Characters characters = new Characters();
        characters.add(new Character("romeo", 9));

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, characters, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void nothing(){
        Assignment assignment = new Assignment("hamlet", "thou art as rotten as the difference between nothing and the sum of a snotty stinking half-witted hog and a small toad");

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(10);

    }
    
    @Test
    public void youAreNothing(){
    	Assignment assignment = new Assignment("hamlet", "you are nothing");

        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, 3, wordlist);

        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(0);

    }
}