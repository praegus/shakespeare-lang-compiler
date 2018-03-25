package nl.java.shakespearelang.executor.assignment;

import nl.java.shakespearelang.executor.Wordlist;
import nl.java.shakespearelang.parser.line.Assignment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class AssignmentInterpreterTest {
    private static Wordlist wordlist;

    @BeforeClass
    public static void before() throws IOException {
        wordlist = new Wordlist();
    }

    @Test
    public void test() {
        AssignmentInterpreter assignmentInterpreter = new AssignmentInterpreter(
            wordlist,
            new Assignment(
                "subject",
                "you are as small as the difference between the square of the difference between my little pony and your big hairy hound and the cube of your sorry little codpiece"));
        int result = assignmentInterpreter.getValue(0, null);

    }

}