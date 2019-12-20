package nl.java.shakespearelang;

import nl.java.shakespearelang.executor.PlayPerformer;
import nl.java.shakespearelang.parser.Play;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.String.format;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            throw new ParseException("There is no program to load!");
        }
        if (args.length > 1) {
            throw new ParseException("Please give only one argument!");
        }
        Play play = new Play(readFile(args[0]));
        PlayPerformer playPerformer = new PlayPerformer(play);
        playPerformer.performPlay();
    }

    private static String readFile(String filePath) {
        try {
            StringBuilder contentBuilder = new StringBuilder();
            Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8);
            stream.forEach(s -> contentBuilder.append(s).append("\n"));

            return contentBuilder.toString();
        } catch (IOException e) {
            throw new ParseException(format("Cannot read file %s",filePath));
        }
    }
}