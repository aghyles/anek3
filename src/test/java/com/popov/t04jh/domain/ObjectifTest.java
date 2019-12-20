package com.popov.t04jh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.popov.t04jh.web.rest.TestUtil;

public class ObjectifTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Objectif.class);
        Objectif objectif1 = new Objectif();
        objectif1.setId(1L);
        Objectif objectif2 = new Objectif();
        objectif2.setId(objectif1.getId());
        assertThat(objectif1).isEqualTo(objectif2);
        objectif2.setId(2L);
        assertThat(objectif1).isNotEqualTo(objectif2);
        objectif1.setId(null);
        assertThat(objectif1).isNotEqualTo(objectif2);
    }
}
