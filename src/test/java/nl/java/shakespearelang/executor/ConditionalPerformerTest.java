package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.Characters;
import nl.java.shakespearelang.Character;
import nl.java.shakespearelang.parser.line.Conditional;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionalPerformerTest {

    @Test
    public void art_thou_more_cunning() throws Exception {
    	Characters characters = new Characters();
        characters.add(new Character("romeo", 10));
        characters.add(new Character("the ghost", 0));
        Conditional conditional = new Conditional("juliet", "art thou more cunning than the ghost");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, characters, "romeo", new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isTrue();
    }

    @Test
    public void am_i_better_than_you() throws Exception {
    	Characters characters = new Characters();
        characters.add(new Character("juliet", 10));
        characters.add(new Character("the ghost", 0));
        Conditional conditional = new Conditional("juliet", "am i better than you");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, characters, "the ghost", new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isTrue();
    }

    @Test
    public void art_thou_more_fat() throws Exception {
    	Characters characters = new Characters();
        characters.add(new Character("romeo", 10));
        characters.add(new Character("the ghost", 0));
        Conditional conditional = new Conditional("juliet", "art thou more fat than the ghost");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, characters, "romeo", new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isFalse();
    }

    @Test
    public void am_i_smaller_than_you() throws Exception {
    	Characters characters = new Characters();
        characters.add(new Character("juliet", 10));
        characters.add(new Character("the ghost", 0));
        Conditional conditional = new Conditional("juliet", "am i smaller than you");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, characters, "the ghost", new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isFalse();
    }

    @Test
    public void calculations_in_conditionals_and_equals_equal() throws Exception {
    	Characters characters = new Characters();
        characters.add(new Character("romeo", 9));
        characters.add(new Character("juliet", 3));
        characters.add(new Character("the ghost", 0));
        Conditional conditional = new Conditional("juliet", "is the remainder of the quotient between Romeo and me as good as nothing");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, characters, "the ghost", new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isTrue();
    }

    @Test
    public void calculations_in_conditionals_and_equals_not_equal() throws Exception {
    	Characters characters = new Characters();
        characters.add(new Character("romeo", 10));
        characters.add(new Character("juliet", 3));
        characters.add(new Character("the ghost", 0));
        Conditional conditional = new Conditional("juliet", "is the remainder of the quotient between Romeo and me as good as nothing");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, characters, "the ghost", new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isFalse();
    }
}