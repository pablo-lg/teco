package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DireccionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Direccion.class);
        Direccion direccion1 = new Direccion();
        direccion1.setId("id1");
        Direccion direccion2 = new Direccion();
        direccion2.setId(direccion1.getId());
        assertThat(direccion1).isEqualTo(direccion2);
        direccion2.setId("id2");
        assertThat(direccion1).isNotEqualTo(direccion2);
        direccion1.setId(null);
        assertThat(direccion1).isNotEqualTo(direccion2);
    }
}
