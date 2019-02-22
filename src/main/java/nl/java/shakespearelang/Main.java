package nl.java.shakespearelang;

import nl.java.shakespearelang.executor.PlayPerformer;
import nl.java.shakespearelang.parser.Play;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length==0) {
            throw new RuntimeException("There is no program to load!");
        }
        if (args.length > 1) {
            throw new RuntimeException("Please give only one argument!");
        }
        Play play = new Play(readFile(args[0]));
        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();
    }

    private static String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }
}
