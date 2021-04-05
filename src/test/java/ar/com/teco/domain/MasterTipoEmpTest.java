package ar.com.teco.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.teco.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MasterTipoEmpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterTipoEmp.class);
        MasterTipoEmp masterTipoEmp1 = new MasterTipoEmp();
        masterTipoEmp1.setId(1L);
        MasterTipoEmp masterTipoEmp2 = new MasterTipoEmp();
        masterTipoEmp2.setId(masterTipoEmp1.getId());
        assertThat(masterTipoEmp1).isEqualTo(masterTipoEmp2);
        masterTipoEmp2.setId(2L);
        assertThat(masterTipoEmp1).isNotEqualTo(masterTipoEmp2);
        masterTipoEmp1.setId(null);
        assertThat(masterTipoEmp1).isNotEqualTo(masterTipoEmp2);
    }
}
