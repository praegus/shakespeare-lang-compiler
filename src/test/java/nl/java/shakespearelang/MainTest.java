package nl.java.shakespearelang;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test
    public void if_a_program_is_given_as_argument_the_program_is_run() throws Exception {
        String helloWorld = "helloWorld.spl";
        Main.main(new String[]{helloWorld});

        assertThat(outContent.toString().trim()).isEqualTo("Hello World!");
    }

    @Test
    public void if_no_arguments_are_given_an_exception_is_thrown() {
        assertThatThrownBy(() -> Main.main(new String[0])).isInstanceOf(RuntimeException.class).hasMessage("There is no program to load!");
    }

    @Test
    public void if_too_many_arguments_are_given_an_exception_is_thrown() {
        assertThatThrownBy(() -> Main.main(new String[2])).isInstanceOf(RuntimeException.class).hasMessage("Please give only one argument!");
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
}