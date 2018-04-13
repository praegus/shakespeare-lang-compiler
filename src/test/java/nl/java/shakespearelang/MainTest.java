package nl.java.shakespearelang;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test
    public void if_a_program_is_given_as_argument_the_program_is_run() throws Exception {
        String helloWorld = readFile("hello.spl");
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

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
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