package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.OutputStatement;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SceneTest {

    @Test
    public void if_a_scene_with_a_title_that_does_not_end_in_a_dot_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Scene("i name", 1)).isInstanceOf(RuntimeException.class).hasMessage("Title of scene is not ended properly with a dot!");
    }

    @Test
    public void if_a_scene_with_an_invalid_title_is_created_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Scene("i name.", 1)).isInstanceOf(RuntimeException.class).hasMessage("Title of scene does not contain an act number with a semicolumn!");
    }

    @Test
    public void if_a_scene_with_an_invalid_numberø_is_created_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Scene("ii: name.", 1)).isInstanceOf(RuntimeException.class).hasMessage("Scene numbering is not in sequence!");
    }

    @Test
    public void if_a_scene_does_not_have_a_valid_enter_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Scene("i: name. [nter romeo] romeo: speak your mind. [exit romeo]", 1))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Scene should contain an enter and exit or exeunt surrounded by square brackets ([])!");
    }

    @Test
    public void if_a_scene_does_not_have_a_valid_exit_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Scene("i: name. [nter romeo] romeo: speak your mind. [eit romeo]", 1))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Scene should contain an enter and exit or exeunt surrounded by square brackets ([])!");
    }

    @Test
    public void if_a_scene_does_not_have_a_valid_enter_end_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Scene("i: name. [enter romeo romeo: speak your mind. [exit romeo", 1))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Scene should contain an enter and exit or exeunt surrounded by square brackets ([])!");
    }

    @Test
    public void if_a_scene_is_valid_enter_and_exit_are_filled_and_ands_are_ignored() {
        Scene scene = new Scene("i: name. [enter romeo and juliet] romeo: speak your mind. [exit romeo and juliet]", 1);
        assertThat(scene.getTitle()).isEqualTo("name");
        assertThat(scene.getEnter()).containsOnly("romeo", "juliet");
        assertThat(scene.getExit()).containsOnly("romeo", "juliet");
        assertThat(scene.getSceneNumber()).isEqualTo(1);
    }

    @Test
    public void if_a_scene_is_valid_enter_and_exeunt_are_filled_and_ands_are_ignored() {
        Scene scene = new Scene("i: name. [enter romeo and juliet] romeo: speak your mind. [exeunt romeo and juliet]", 1);
        assertThat(scene.getExit()).containsOnly("romeo", "juliet");
    }


    @Test
    public void an_exception_is_thrown_if_the_type_of_line_is_not_clear() {
        assertThatThrownBy(() -> new Scene("i: name. [enter romeo and juliet] romeo: unclear line. [exit romeo and juliet]", 1))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("type of line is unclear!");
    }

    @Test
    public void if_a_scene_is_valid_with_a_statement_the_line_is_a_statement() {
        Scene scene = new Scene("i: name. [enter romeo and juliet] romeo: speak your mind. [exit romeo and juliet]", 1);
        assertThat(scene.getLines()).containsOnly(new OutputStatement("romeo", "speak your mind", false));
    }

    @Test
    public void if_a_scene_is_valid_with_a_statement_and_an_assignment_the_scene_is_created_with_the_lines() throws Exception {
        Scene scene = new Scene("i: name. [enter romeo and juliet] romeo: speak your mind. you kingdom. thou other thing. [exit romeo and juliet]", 1);
        assertThat(scene.getLines()).containsOnly(
            new OutputStatement("romeo", "speak your mind", false),
            new Assignment("romeo", "you kingdom"),
            new Assignment("romeo", "thou other thing"));
    }

}