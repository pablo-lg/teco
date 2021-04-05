package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoUsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoUsuario.class);
        GrupoUsuario grupoUsuario1 = new GrupoUsuario();
        grupoUsuario1.setId(1L);
        GrupoUsuario grupoUsuario2 = new GrupoUsuario();
        grupoUsuario2.setId(grupoUsuario1.getId());
        assertThat(grupoUsuario1).isEqualTo(grupoUsuario2);
        grupoUsuario2.setId(2L);
        assertThat(grupoUsuario1).isNotEqualTo(grupoUsuario2);
        grupoUsuario1.setId(null);
        assertThat(grupoUsuario1).isNotEqualTo(grupoUsuario2);
    }
}
