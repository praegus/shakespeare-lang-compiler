package nl.java.shakespearelang.parser.line;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {

    @Test
    public void create_assignment_with_the_difference_between() {
        Assignment line = new Assignment("romeo", "difference between");
        assertThat(line.getWords()).containsExactly("THEDIFFERENCEBETWEEN");
        assertThat(line.getSubject()).isEqualTo("romeo");
        assertThat(line.getLine()).isEqualTo("difference between");
    }

    @Test
    public void create_assignment_with_sum_of() {
        Assignment line = new Assignment("romeo", "sum of");
        assertThat(line.getWords()).containsExactly("THESUMOF");
    }

    @Test
    public void create_assignment_with_product_of() {
        Assignment line = new Assignment("romeo", "product of");
        assertThat(line.getWords()).containsExactly("THEPRODUCTOF");
    }

    @Test
    public void create_assignment_with_square_of() {
        Assignment line = new Assignment("romeo", "square of");
        assertThat(line.getWords()).containsExactly("THESQUAREOF");
    }

    @Test
    public void create_assignment_with_quotient_of() {
        Assignment line = new Assignment("romeo", "quotient of");
        assertThat(line.getWords()).containsExactly("THEQUOTIENTOF");
    }

    @Test
    public void create_assignment_with_cube_of() {
        Assignment line = new Assignment("romeo", "cube of");
        assertThat(line.getWords()).containsExactly("THECUBEOF");
    }

    @Test
    public void create_assignment_with_summers_day() {
        Assignment line = new Assignment("romeo", "summer's day");
        assertThat(line.getWords()).containsExactly("summer'sday");
    }

    @Test
    public void create_assignment_with_thyself() {
        Assignment line = new Assignment("romeo", "thyself");
        assertThat(line.getWords()).containsExactly("yourself");
    }
}