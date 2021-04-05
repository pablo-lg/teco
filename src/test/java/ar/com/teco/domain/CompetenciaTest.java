package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompetenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competencia.class);
        Competencia competencia1 = new Competencia();
        competencia1.setId(1L);
        Competencia competencia2 = new Competencia();
        competencia2.setId(competencia1.getId());
        assertThat(competencia1).isEqualTo(competencia2);
        competencia2.setId(2L);
        assertThat(competencia1).isNotEqualTo(competencia2);
        competencia1.setId(null);
        assertThat(competencia1).isNotEqualTo(competencia2);
    }
}
