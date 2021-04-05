package ar.com.teco.repository;

import ar.com.teco.domain.TipoObra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoObra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoObraRepository extends JpaRepository<TipoObra, Long> {}
