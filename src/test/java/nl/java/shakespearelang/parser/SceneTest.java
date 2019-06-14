package nl.java.shakespearelang.parser;

import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Conditional;
import nl.java.shakespearelang.parser.line.Enter;
import nl.java.shakespearelang.parser.line.Exit;
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
        assertThatThrownBy(() -> new Scene("scene i name.", 1)).isInstanceOf(RuntimeException.class).hasMessage("Title of scene does not contain 'scene' or a semicolumn!");
    }

    @Test
    public void if_a_scene_with_an_invalid_numberø_is_created_an_exception_is_thrown() {
        assertThatThrownBy(() -> new Scene("scene ii: name.", 1)).isInstanceOf(RuntimeException.class).hasMessage("scene numbering is not in sequence!");
    }

    @Test
    public void if_a_scene_is_valid_with_a_statement_the_line_is_a_statement() {
        Scene scene = new Scene("scene i: name. romeo: speak your mind.", 1);
        assertThat(scene.getLines()).containsOnly(new OutputStatement("romeo", "speak your mind", false));
    }

    @Test
    public void strange_conditional() {
        Scene scene = new Scene("scene i: name. emilia: am i as good as the sum of bianca and a flower. if so, thou art as fine as bianca", 1);
        assertThat(scene.getLines()).containsExactly(
                new Conditional("emilia","am i as good as the sum of bianca and a flower"),
                new Assignment("emilia","thou art as fine as bianca"));
    }

    @Test
    public void if_a_scene_is_valid_with_a_statement_and_an_assignment_the_scene_is_created_with_the_lines() {
        Scene scene = new Scene("scene i: name. enter romeo and juliet. romeo: speak your mind. you kingdom. thou other thing. exit romeo and juliet.", 1);
        assertThat(scene.getLines()).containsOnly(
                new Enter("enter romeo and juliet"),
                new OutputStatement("romeo", "speak your mind", false),
                new Assignment("romeo", "you kingdom"),
                new Assignment("romeo", "thou other thing"),
                new Exit("exit romeo and juliet"));
    }
}