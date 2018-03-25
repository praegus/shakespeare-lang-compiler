package nl.java.shakespearelang.parser;

import org.junit.Test;

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
    public void if_the_program_has_a_title_a_character_and_an_act_the_program_is_created() throws Exception {
        Play play = new Play("title. Romeo, a nice lad. Act I: name.");
        assertThat(play.getTitle()).isEqualTo("title");
        assertThat(play.getCharacters()).hasSize(1);
        assertThat(play.getCharacters().keySet()).contains("romeo");
        assertThat(play.getActs()).hasSize(1);
        assertThat(play.getActs().get(0).getTitle()).isEqualTo("name");
    }

}