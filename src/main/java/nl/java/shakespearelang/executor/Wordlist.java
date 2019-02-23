package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.CharacterAsString;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.*;

public class Wordlist {
    private final List<String> negativeNouns;
    private final List<String> positiveNouns;
    private final List<String> redundants;
    private final List<String> characters;
    private final List<String> positiveComparatives;
    private final List<String> negativeComparatives;
    private final List<String> positiveAdjectives;
    private final List<String> negativeAdjectives;
    private final List<String> neutralAdjectives;

    public Wordlist() throws IOException {
        neutralAdjectives = stream(readFile("neutral_adjective.wordlist").split("\n")).map(String::toLowerCase).collect(Collectors.toList());
        positiveAdjectives = stream(readFile("positive_adjective.wordlist").split("\n")).map(String::toLowerCase).collect(Collectors.toList());
        negativeAdjectives = stream(readFile("negative_adjective.wordlist").split("\n")).map(String::toLowerCase).collect(Collectors.toList());
        negativeNouns = asList(readFile("negative_noun.wordlist").split("\n"));
        positiveNouns = new ArrayList<>(asList(readFile("positive_noun.wordlist").split("\n")));
        positiveNouns.addAll(asList(readFile("neutral_noun.wordlist").split("\n")));

        redundants = new ArrayList<>(asList(readFile("first_person_possessive.wordlist").split("\n")));
        redundants.addAll(asList(readFile("second_person_possessive.wordlist").split("\n")));
        redundants.addAll(asList(readFile("third_person_possessive.wordlist").split("\n")));
        redundants.addAll(asList(readFile("article.wordlist").split("\n")));
        characters = stream(readFile("character.wordlist").split("\n")).map(String::toLowerCase).collect(Collectors.toList());
        positiveComparatives = stream(readFile("positive_comparative.wordlist").split("\n")).map(String::toLowerCase).collect(Collectors.toList());
        negativeComparatives = stream(readFile("negative_comparative.wordlist").split("\n")).map(String::toLowerCase).collect(Collectors.toList());
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }

    public boolean isAdjective(String word) {
        return positiveAdjectives.contains(word) || negativeAdjectives.contains(word) || neutralAdjectives.contains(word);
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

    public boolean isCharacter(String character) {
        return characters.contains(character.toLowerCase());
    }

    public boolean isCharacter(CharacterAsString character) {
        return isCharacter(character.getName());
    }

    public List<String> getPositiveComparatives() {
        return positiveComparatives;
    }

    public List<String> getNegativeComparatives() {
        return negativeComparatives;
    }

    public List<String> getPositiveAdjectives() {
        return positiveAdjectives;
    }

    public List<String> getNegativeAdjectives() {
        return negativeAdjectives;
    }
}
