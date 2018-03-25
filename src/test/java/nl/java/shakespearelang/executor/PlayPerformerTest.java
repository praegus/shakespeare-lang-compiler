package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.parser.Play;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class PlayPerformerTest {

    @Test
    public void playExecutorTest() throws Exception {
        String theString = readFile("hello.spl");

        Play play = new Play(theString);

        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }
}