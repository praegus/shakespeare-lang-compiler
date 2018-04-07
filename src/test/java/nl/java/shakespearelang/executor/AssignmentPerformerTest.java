package nl.java.shakespearelang.executor;

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
    public void test() {
        Assignment assignment = new Assignment(
            "hamlet",
            "you are as small as the difference between the square of the difference between my little pony and your big hairy hound and the cube of your sorry little codpiece");
        AssignmentPerformer assignmentPerformer = new AssignmentPerformer(assignment, null, null, wordlist);
        int result = assignmentPerformer.performAssignment();
        assertThat(result).isEqualTo(100);
    }
}