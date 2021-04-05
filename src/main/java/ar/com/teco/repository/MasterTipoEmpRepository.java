package ar.com.teco.repository;

import ar.com.teco.domain.MasterTipoEmp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MasterTipoEmp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MasterTipoEmpRepository extends JpaRepository<MasterTipoEmp, Long> {}
