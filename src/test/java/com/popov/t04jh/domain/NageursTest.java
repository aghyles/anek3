package com.popov.t04jh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.popov.t04jh.web.rest.TestUtil;

public class NageursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nageurs.class);
        Nageurs nageurs1 = new Nageurs();
        nageurs1.setId(1L);
        Nageurs nageurs2 = new Nageurs();
        nageurs2.setId(nageurs1.getId());
        assertThat(nageurs1).isEqualTo(nageurs2);
        nageurs2.setId(2L);
        assertThat(nageurs1).isNotEqualTo(nageurs2);
        nageurs1.setId(null);
        assertThat(nageurs1).isNotEqualTo(nageurs2);
    }
}
