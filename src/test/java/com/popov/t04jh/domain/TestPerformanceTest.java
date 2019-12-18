package com.popov.t04jh.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.popov.t04jh.web.rest.TestUtil;

public class TestPerformanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestPerformance.class);
        TestPerformance testPerformance1 = new TestPerformance();
        testPerformance1.setId(1L);
        TestPerformance testPerformance2 = new TestPerformance();
        testPerformance2.setId(testPerformance1.getId());
        assertThat(testPerformance1).isEqualTo(testPerformance2);
        testPerformance2.setId(2L);
        assertThat(testPerformance1).isNotEqualTo(testPerformance2);
        testPerformance1.setId(null);
        assertThat(testPerformance1).isNotEqualTo(testPerformance2);
    }
}
