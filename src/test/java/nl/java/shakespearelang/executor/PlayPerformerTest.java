package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.parser.Play;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayPerformerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

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

    @Test
    public void helloWorld() throws Exception {
        String program = readFile("hello.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo("Hello World!");
    }

    @Ignore // work in progress
    @Test
    public void prime() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("10".getBytes());
        System.setIn(in);

        String program = readFile("primes.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();
    }


    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }
}