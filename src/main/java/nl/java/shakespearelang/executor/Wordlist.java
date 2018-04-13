package nl.java.shakespearelang.executor;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Wordlist {
    private final List<String> adjectives;
    private final List<String> negativeNouns;
    private final List<String> positiveNouns;
    private final List<String> redundants;
    private final List<String> characters;

    public Wordlist() throws IOException {
        adjectives = new ArrayList<>();
        adjectives.addAll(Arrays.asList(readFile("negative_adjective.wordlist").split("\r\n")));
        adjectives.addAll(Arrays.asList(readFile("neutral_adjective.wordlist").split("\r\n")));
        adjectives.addAll(Arrays.asList(readFile("positive_adjective.wordlist").split("\r\n")));

        negativeNouns = Arrays.asList(readFile("negative_noun.wordlist").split("\r\n"));
        positiveNouns = new ArrayList<>(Arrays.asList(readFile("positive_noun.wordlist").split("\r\n")));
        positiveNouns.addAll(Arrays.asList(readFile("neutral_noun.wordlist").split("\r\n")));

        redundants = new ArrayList<>(Arrays.asList(readFile("first_person_possessive.wordlist").split("\r\n")));
        redundants.addAll(Arrays.asList(readFile("second_person_possessive.wordlist").split("\r\n")));
        redundants.addAll(Arrays.asList(readFile("third_person_possessive.wordlist").split("\r\n")));
        redundants.addAll(Arrays.asList(readFile("article.wordlist").split("\r\n")));
        characters = Arrays.stream(readFile("character.wordlist").split("\r\n")).map(String::toLowerCase).collect(Collectors.toList());
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }

    public boolean isAdjective(String word) {
        return adjectives.contains(word);
    }

    public boolean isNegativeNoun(String word) {
        return negativeNouns.contains(word);
    }

    public boolean isPositiveNoun(String word) {
        return positiveNouns.contains(word);
    }

    public boolean isRedundant(String word) {
        return redundants.contains(word);
    }

    public boolean isCharacter(String word) {
        return characters.contains(word.toLowerCase());
    }

}
