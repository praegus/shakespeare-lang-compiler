package nl.java.shakespearelang.parser;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ActTest {

    @Test
    public void als_een_act_met_een_ongeldige_titel_aangemaakt_wordt_dan_komt_er_een_foutmelding_terug() {
        assertThatThrownBy(()->new Act("act i name.", 1)).isInstanceOf(RuntimeException.class).hasMessage("Title of act does not contain 'act' or a semicolumn!");
    }

    @Test
    public void als_een_act_met_een_verkeerd_nummer_aangemaakt_wordt_dan_komt_er_een_foutmelding_terug() {
        assertThatThrownBy(()->new Act("act ii: name.", 1)).isInstanceOf(RuntimeException.class).hasMessage("act numbering is not in sequence!");
    }

    @Test
    public void als_een_act_zonder_scenes_aangemaakt_wordt_heeft_deze_een_titel_en_geen_scense() throws Exception {
        Act act = new Act("act i: name.", 1);
        assertThat(act.getTitle()).isEqualTo("name");
        assertThat(act.getScenes()).hasSize(0);
        assertThat(act.getActNumber()).isEqualTo(1);
    }

    @Test
    public void als_een_act_met_scenes_aangemaakt_wordt_heeft_deze_een_titel_en_scenses() throws Exception {
        Act act = new Act("act i: name. scene i: name. [enter romeo] romeo: speak your mind. [exit romeo]", 1);
        assertThat(act.getTitle()).isEqualTo("name");
        assertThat(act.getScenes()).hasSize(1);
        assertThat(act.getActNumber()).isEqualTo(1);
    }
}