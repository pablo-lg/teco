package ar.com.teco.repository;

import ar.com.teco.domain.GrupoEmprendimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GrupoEmprendimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoEmprendimientoRepository extends JpaRepository<GrupoEmprendimiento, Long> {}
