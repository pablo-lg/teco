package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TecnologiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tecnologia.class);
        Tecnologia tecnologia1 = new Tecnologia();
        tecnologia1.setId(1L);
        Tecnologia tecnologia2 = new Tecnologia();
        tecnologia2.setId(tecnologia1.getId());
        assertThat(tecnologia1).isEqualTo(tecnologia2);
        tecnologia2.setId(2L);
        assertThat(tecnologia1).isNotEqualTo(tecnologia2);
        tecnologia1.setId(null);
        assertThat(tecnologia1).isNotEqualTo(tecnologia2);
    }
}
