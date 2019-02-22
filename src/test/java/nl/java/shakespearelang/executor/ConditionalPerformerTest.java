package nl.java.shakespearelang.executor;

import nl.java.shakespearelang.CharacterInPlay;
import nl.java.shakespearelang.parser.line.Conditional;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionalPerformerTest {

    @Test
    public void art_thou_more_cunning() throws Exception {
        Map<CharacterInPlay, Integer> map = new HashMap<>();
        map.put(new CharacterInPlay("romeo"), 10);
        map.put(new CharacterInPlay("the ghost"), 0);
        Conditional conditional = new Conditional(new CharacterInPlay("juliet"), "art thou more cunning than the ghost");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, map, new CharacterInPlay("romeo"), new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isTrue();
    }

    @Test
    public void am_i_better_than_you() throws Exception {
        Map<CharacterInPlay, Integer> map = new HashMap<>();
        map.put(new CharacterInPlay("juliet"), 10);
        map.put(new CharacterInPlay("the ghost"), 0);
        Conditional conditional = new Conditional(new CharacterInPlay("juliet"), "am i better than you");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, map, new CharacterInPlay("the ghost"), new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isTrue();
    }

    @Test
    public void art_thou_more_fat() throws Exception {
        Map<CharacterInPlay, Integer> map = new HashMap<>();
        map.put(new CharacterInPlay("romeo"), 10);
        map.put(new CharacterInPlay("the ghost"), 0);
        Conditional conditional = new Conditional(new CharacterInPlay("juliet"), "art thou more fat than the ghost");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, map, new CharacterInPlay("romeo"), new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isFalse();
    }

    @Test
    public void am_i_smaller_than_you() throws Exception {
        Map<CharacterInPlay, Integer> map = new HashMap<>();
        map.put(new CharacterInPlay("juliet"), 10);
        map.put(new CharacterInPlay("the ghost"), 0);
        Conditional conditional = new Conditional(new CharacterInPlay("juliet"), "am i smaller than you");

        ConditionalPerformer conditionalPerformer = new ConditionalPerformer(conditional, map, new CharacterInPlay("the ghost"), new Wordlist());

        assertThat(conditionalPerformer.performConditional()).isFalse();
    }
}