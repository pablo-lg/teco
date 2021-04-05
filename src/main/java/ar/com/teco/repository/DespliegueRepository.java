package ar.com.teco.repository;

import ar.com.teco.domain.Despliegue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Despliegue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DespliegueRepository extends JpaRepository<Despliegue, Long> {}
