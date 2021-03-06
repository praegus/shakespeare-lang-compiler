package nl.java.shakespearelang.parser;

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
        assertThat(play.getCharacters()).contains(new Character("romeo",0));
        assertThat(play.getActs()).hasSize(1);
        assertThat(play.getActs().get(0).getTitle()).isEqualTo("name");
    }

    @Test
    public void parseHelloWorld() throws IOException {
        Play play = new Play(readFile("helloWorld.spl"));

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

    @Test
    public void parseFibonacci() throws IOException {
        Play play = new Play(readFile("fibonacci.spl"));

        assertThat(play.getTitle()).isEqualTo("by peter nillius 20010831<nillius at nada dot kth dot se>fibonacci's numbers.rome");
        assertThat(play.getCharacters()).hasSize(3);
        assertThat(play.getActs()).hasSize(1);
    }

    @Test
    public void parseFibonacci2() throws IOException {
        Play play = new Play(readFile("fibonacci2.spl"));

        assertThat(play.getTitle()).isEqualTo("by bj�rn stenberg and linus nielsen feltzing 20010831should thee wish to bring thyselves to our attention doeth as below,pointeth thy postal delivery appliance untobjorn(at)haxx(dot)se or linus(at)haxx(dot)sethe fibonacci drama.hamlet");
        assertThat(play.getCharacters()).hasSize(5);
        assertThat(play.getActs()).hasSize(1);
    }

//    @Ignore // fixme!
    @Test
    public void parseGuess() throws IOException {
        Play play = new Play(readFile("guess.spl"));

        assertThat(play.getTitle()).isEqualTo("the guessing game, by jonas sj�bergh, jsh@nada<dot>kth<dot>se,think of a number between 1 and 1000, if the program's guess istoo high input \"<\", if it is too low input \">\" and if it is correctinput \"=\".ro");
        assertThat(play.getCharacters()).hasSize(8);
        assertThat(play.getActs()).hasSize(1);
    }

    @Test
    public void parseReverse() throws IOException {
        Play play = new Play(readFile("reverse.spl"));

        assertThat(play.getTitle()).isEqualTo("outputting input reversedly");
        assertThat(play.getCharacters()).hasSize(2);
        assertThat(play.getActs()).hasSize(1);
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