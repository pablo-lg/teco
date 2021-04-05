package ar.com.teco.repository;

import ar.com.teco.domain.Pauta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pauta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {}
