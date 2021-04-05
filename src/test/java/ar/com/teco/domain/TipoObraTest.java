package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoObraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoObra.class);
        TipoObra tipoObra1 = new TipoObra();
        tipoObra1.setId(1L);
        TipoObra tipoObra2 = new TipoObra();
        tipoObra2.setId(tipoObra1.getId());
        assertThat(tipoObra1).isEqualTo(tipoObra2);
        tipoObra2.setId(2L);
        assertThat(tipoObra1).isNotEqualTo(tipoObra2);
        tipoObra1.setId(null);
        assertThat(tipoObra1).isNotEqualTo(tipoObra2);
    }
}
