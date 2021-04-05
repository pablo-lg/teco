package ar.com.teco.repository;

import ar.com.teco.domain.Competencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Competencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {}
