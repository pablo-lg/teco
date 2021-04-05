package ar.com.teco.repository;

import ar.com.teco.domain.Segmento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Segmento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentoRepository extends JpaRepository<Segmento, Long> {}
