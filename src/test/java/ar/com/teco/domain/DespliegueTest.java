package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DespliegueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Despliegue.class);
        Despliegue despliegue1 = new Despliegue();
        despliegue1.setId(1L);
        Despliegue despliegue2 = new Despliegue();
        despliegue2.setId(despliegue1.getId());
        assertThat(despliegue1).isEqualTo(despliegue2);
        despliegue2.setId(2L);
        assertThat(despliegue1).isNotEqualTo(despliegue2);
        despliegue1.setId(null);
        assertThat(despliegue1).isNotEqualTo(despliegue2);
    }
}
