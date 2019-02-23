package nl.java.shakespearelang.parser.line;

import nl.java.shakespearelang.CharacterAsString;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {

    @Test
    public void create_assignment_with_the_difference_between() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "the difference between");
        assertThat(line.getWords()).containsExactly("THEDIFFERENCEBETWEEN");
        assertThat(line.getSubject()).isEqualTo(new CharacterAsString("romeo"));
        assertThat(line.getLine()).isEqualTo("the difference between");
    }

    @Test
    public void create_assignment_with_sum_of() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "the sum of");
        assertThat(line.getWords()).containsExactly("THESUMOF");
    }

    @Test
    public void create_assignment_with_product_of() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "the product of");
        assertThat(line.getWords()).containsExactly("THEPRODUCTOF");
    }

    @Test
    public void create_assignment_with_square_of() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "the square of");
        assertThat(line.getWords()).containsExactly("THESQUAREOF");
    }

    @Test
    public void create_assignment_with_quotient_of() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "the quotient between");
        assertThat(line.getWords()).containsExactly("THEQUOTIENTBETWEEN");
    }

    @Test
    public void create_assignment_with_cube_of() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "the cube of");
        assertThat(line.getWords()).containsExactly("THECUBEOF");
    }

    @Test
    public void create_assignment_with_twice() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "twice");
        assertThat(line.getWords()).containsExactly("TWICE");
    }

    @Test
    public void create_assignment_with_summers_day() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "summer's day");
        assertThat(line.getWords()).containsExactly("summer'sday");
    }

    @Test
    public void create_assignment_with_thyself() {
        Assignment line = new Assignment(new CharacterAsString("romeo"), "thyself");
        assertThat(line.getWords()).containsExactly("yourself");
    }

    @Test
    public void create_goto_scene_with_condition() {
        Goto gotoStatement = new Goto(new CharacterAsString("romeo"), "If so, let us proceed to scene V", true);
        assertThat(gotoStatement.getRequestedScene()).isEqualTo(5);
        assertThat(gotoStatement.conditionApplies(true)).isTrue();
    }

    @Test
    public void create_goto_with_false_condition() {
        Goto gotoStatement = new Goto(new CharacterAsString("romeo"), "If so, let us proceed to scene V", false);
        assertThat(gotoStatement.getRequestedScene()).isEqualTo(5);
        assertThat(gotoStatement.conditionApplies(true)).isFalse();
    }

    @Test
    public void create_goto_scene_no_condition() {
        Goto gotoStatement = new Goto(new CharacterAsString("romeo"), "If so, let us proceed to scene V", null);
        assertThat(gotoStatement.getRequestedScene()).isEqualTo(5);
        assertThat(gotoStatement.conditionApplies(true)).isTrue();
    }
}