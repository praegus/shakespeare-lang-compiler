package nl.java.shakespearelang.executor;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wordlist {
    private final List<String> adjectives;
    private final List<String> negativeNouns;
    private final List<String> positiveNouns;


    public Wordlist() throws IOException {
        adjectives = new ArrayList<>();
        adjectives.addAll(Arrays.asList(readFile("negative_adjective.wordlist").split("\n")));
        adjectives.addAll(Arrays.asList(readFile("neutral_adjective.wordlist").split("\n")));
        adjectives.addAll(Arrays.asList(readFile("positive_adjective.wordlist").split("\n")));

        negativeNouns = Arrays.asList(readFile("negative_noun.wordlist").split("\n"));
        positiveNouns = Arrays.asList(readFile("positive_noun.wordlist").split("\n"));
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }

    public boolean isAdjective(String word){
        return adjectives.contains(word);
    }

    public boolean isNegativeNoun(String word){
        return negativeNouns.contains(word);
    }

    public boolean isPositiveNoun(String word){
        return positiveNouns.contains(word);
    }
}
