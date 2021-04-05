package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EjecCuentasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EjecCuentas.class);
        EjecCuentas ejecCuentas1 = new EjecCuentas();
        ejecCuentas1.setId(1L);
        EjecCuentas ejecCuentas2 = new EjecCuentas();
        ejecCuentas2.setId(ejecCuentas1.getId());
        assertThat(ejecCuentas1).isEqualTo(ejecCuentas2);
        ejecCuentas2.setId(2L);
        assertThat(ejecCuentas1).isNotEqualTo(ejecCuentas2);
        ejecCuentas1.setId(null);
        assertThat(ejecCuentas1).isNotEqualTo(ejecCuentas2);
    }
}
