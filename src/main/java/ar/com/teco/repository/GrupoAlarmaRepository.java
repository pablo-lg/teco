package ar.com.teco.repository;

import ar.com.teco.domain.GrupoAlarma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GrupoAlarma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoAlarmaRepository extends JpaRepository<GrupoAlarma, Long> {}
