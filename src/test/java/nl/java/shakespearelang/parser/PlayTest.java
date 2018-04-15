package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.CharacterInPlay;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlayTest {

    @Test
    public void if_the_program_is_empty_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Play("")).isInstanceOf(RuntimeException.class).hasMessage("Cannot parse program, there is no title!");
    }

    @Test
    public void if_the_program_has_no_acts_exception_is_thrown() {
        assertThatThrownBy(() -> new Play("title.")).isInstanceOf(RuntimeException.class).hasMessage("Cannot parse program, there are no acts!");
    }

    @Test
    public void if_the_program_has_no_characters_exception_is_thrown() {
        assertThatThrownBy(() -> new Play("title. Act I name."))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Cannot parse program, Character has no description starting with a comma and ending with a dot!");
    }

    @Test
    public void if_the_program_has_a_title_a_character_and_an_act_the_program_is_created() {
        Play play = new Play("title. Romeo, a nice  lad. Act I: name.");
        assertThat(play.getTitle()).isEqualTo("title");
        assertThat(play.getCharacters()).hasSize(1);
        assertThat(play.getCharacters().keySet()).contains(new CharacterInPlay("romeo"));
        assertThat(play.getActs()).hasSize(1);
        assertThat(play.getActs().get(0).getTitle()).isEqualTo("name");
    }

    @Test
    public void parseHelloWorld() throws IOException {
        Play play = new Play(readFile("hello.spl"));

        assertThat(play.getTitle()).isEqualTo("the infamous hello world program");
        assertThat(play.getCharacters()).hasSize(4);
        assertThat(play.getActs()).hasSize(2);
    }

    @Test
    public void parsePrimes() throws IOException {
        Play play = new Play(readFile("primes.spl"));

        assertThat(play.getTitle()).isEqualTo("prime number computation in copenhagen");
        assertThat(play.getCharacters()).hasSize(4);
        assertThat(play.getActs()).hasSize(2);
    }

    @Ignore // fixme!
    @Test
    public void parseFibonacci() throws IOException {
        Play play = new Play(readFile("fibonacci.spl"));

        assertThat(play.getTitle()).isEqualTo("prime number computation in copenhagen");
        assertThat(play.getCharacters()).hasSize(4);
        assertThat(play.getActs()).hasSize(2);
    }

    @Ignore // fixme!
    @Test
    public void parseFibonacci2() throws IOException {
        Play play = new Play(readFile("fibonacci2.spl"));

        assertThat(play.getTitle()).isEqualTo("prime number computation in copenhagen");
        assertThat(play.getCharacters()).hasSize(4);
        assertThat(play.getActs()).hasSize(2);
    }

    @Ignore // fixme!
    @Test
    public void parseGuess() throws IOException {
        Play play = new Play(readFile("guess.spl"));

        assertThat(play.getTitle()).isEqualTo("prime number computation in copenhagen");
        assertThat(play.getCharacters()).hasSize(4);
        assertThat(play.getActs()).hasSize(2);
    }

    @Ignore // fixme!
    @Test
    public void parseReverse() throws IOException {
        Play play = new Play(readFile("reverse.spl"));

        assertThat(play.getTitle()).isEqualTo("prime number computation in copenhagen");
        assertThat(play.getCharacters()).hasSize(4);
        assertThat(play.getActs()).hasSize(2);
    }

    @Ignore // fixme!
    @Test
    public void parseShakesbeer() throws IOException {
        Play play = new Play(readFile("shakesbeer.spl"));

        assertThat(play.getTitle()).isEqualTo("prime number computation in copenhagen");
        assertThat(play.getCharacters()).hasSize(4);
        assertThat(play.getActs()).hasSize(2);
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }

}