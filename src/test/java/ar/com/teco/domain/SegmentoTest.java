package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Segmento.class);
        Segmento segmento1 = new Segmento();
        segmento1.setId(1L);
        Segmento segmento2 = new Segmento();
        segmento2.setId(segmento1.getId());
        assertThat(segmento1).isEqualTo(segmento2);
        segmento2.setId(2L);
        assertThat(segmento1).isNotEqualTo(segmento2);
        segmento1.setId(null);
        assertThat(segmento1).isNotEqualTo(segmento2);
    }
}
