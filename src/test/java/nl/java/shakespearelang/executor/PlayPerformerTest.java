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
    public void fibonacci() throws Exception {
        String program = readFile("fibonacci.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo("1 1 2 4 8 16 32 64 128 256 512 1024 2048 4096 8192 16384 32768 65536 131072 262144 524288 1048576 2097152 4194304 8388608 16777216 33554432 67108864 134217728 268435456 536870912 1073741824");
    }

    @Test
    public void fibonacci2() throws Exception {
        String program = readFile("fibonacci2.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo("0\n1\n1\n2\n3\n5\n8\n13\n21\n34\n55\n89\n144\n233\n377\n610\n987\n1597\n2584\n4181\n6765\n10946\n17711\n28657\n46368\n75025\n121393\n196418\n317811\n514229\n832040\n1346269\n2178309\n3524578\n5702887\n9227465\n14930352\n24157817\n39088169\n63245986\n102334155\n165580141\n267914296\n433494437\n701408733");
    }

    @Ignore
    @Test
    public void guess() throws Exception {
        String program = readFile("guess.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo("");
    }

    @Test
    public void helloWorld() throws Exception {
        String program = readFile("helloWorld.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo("Hello World!");
    }

    @Test
    public void primes() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("10".getBytes());
        System.setIn(in);

        String program = readFile("primes.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo(
                ">2\n" +
                        "3\n" +
                        "5\n" +
                        "7");
    }

    @Test
    public void reverse() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("Hello world!".getBytes());
        System.setIn(in);

        String program = readFile("reverse.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo("!dlrow olleH");
    }

    @Ignore
    @Test
    public void shakesbeer() throws Exception {
        String program = readFile("shakesbeer.spl");

        Play play = new Play(program);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();

        assertThat(outContent.toString().trim()).isEqualTo("");
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }
}