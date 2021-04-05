package ar.com.teco.repository;

import ar.com.teco.domain.Obra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Obra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {}
