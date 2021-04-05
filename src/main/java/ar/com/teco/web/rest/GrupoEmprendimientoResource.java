package ar.com.teco.web.rest;

import ar.com.teco.domain.GrupoEmprendimiento;
import ar.com.teco.repository.GrupoEmprendimientoRepository;
import ar.com.teco.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.com.teco.domain.GrupoEmprendimiento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrupoEmprendimientoResource {

    private final Logger log = LoggerFactory.getLogger(GrupoEmprendimientoResource.class);

    private static final String ENTITY_NAME = "grupoEmprendimiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoEmprendimientoRepository grupoEmprendimientoRepository;

    public GrupoEmprendimientoResource(GrupoEmprendimientoRepository grupoEmprendimientoRepository) {
        this.grupoEmprendimientoRepository = grupoEmprendimientoRepository;
    }

    /**
     * {@code POST  /grupo-emprendimientos} : Create a new grupoEmprendimiento.
     *
     * @param grupoEmprendimiento the grupoEmprendimiento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoEmprendimiento, or with status {@code 400 (Bad Request)} if the grupoEmprendimiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-emprendimientos")
    public ResponseEntity<GrupoEmprendimiento> createGrupoEmprendimiento(@Valid @RequestBody GrupoEmprendimiento grupoEmprendimiento)
        throws URISyntaxException {
        log.debug("REST request to save GrupoEmprendimiento : {}", grupoEmprendimiento);
        if (grupoEmprendimiento.getId() != null) {
            throw new BadRequestAlertException("A new grupoEmprendimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoEmprendimiento result = grupoEmprendimientoRepository.save(grupoEmprendimiento);
        return ResponseEntity
            .created(new URI("/api/grupo-emprendimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-emprendimientos/:id} : Updates an existing grupoEmprendimiento.
     *
     * @param id the id of the grupoEmprendimiento to save.
     * @param grupoEmprendimiento the grupoEmprendimiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoEmprendimiento,
     * or with status {@code 400 (Bad Request)} if the grupoEmprendimiento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoEmprendimiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-emprendimientos/{id}")
    public ResponseEntity<GrupoEmprendimiento> updateGrupoEmprendimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoEmprendimiento grupoEmprendimiento
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoEmprendimiento : {}, {}", id, grupoEmprendimiento);
        if (grupoEmprendimiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoEmprendimiento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoEmprendimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GrupoEmprendimiento result = grupoEmprendimientoRepository.save(grupoEmprendimiento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoEmprendimiento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /grupo-emprendimientos/:id} : Partial updates given fields of an existing grupoEmprendimiento, field will ignore if it is null
     *
     * @param id the id of the grupoEmprendimiento to save.
     * @param grupoEmprendimiento the grupoEmprendimiento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoEmprendimiento,
     * or with status {@code 400 (Bad Request)} if the grupoEmprendimiento is not valid,
     * or with status {@code 404 (Not Found)} if the grupoEmprendimiento is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoEmprendimiento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/grupo-emprendimientos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GrupoEmprendimiento> partialUpdateGrupoEmprendimiento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoEmprendimiento grupoEmprendimiento
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoEmprendimiento partially : {}, {}", id, grupoEmprendimiento);
        if (grupoEmprendimiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoEmprendimiento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoEmprendimientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoEmprendimiento> result = grupoEmprendimientoRepository
            .findById(grupoEmprendimiento.getId())
            .map(
                existingGrupoEmprendimiento -> {
                    if (grupoEmprendimiento.getDescripcion() != null) {
                        existingGrupoEmprendimiento.setDescripcion(grupoEmprendimiento.getDescripcion());
                    }
                    if (grupoEmprendimiento.getEsProtegido() != null) {
                        existingGrupoEmprendimiento.setEsProtegido(grupoEmprendimiento.getEsProtegido());
                    }

                    return existingGrupoEmprendimiento;
                }
            )
            .map(grupoEmprendimientoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grupoEmprendimiento.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-emprendimientos} : get all the grupoEmprendimientos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoEmprendimientos in body.
     */
    @GetMapping("/grupo-emprendimientos")
    public List<GrupoEmprendimiento> getAllGrupoEmprendimientos() {
        log.debug("REST request to get all GrupoEmprendimientos");
        return grupoEmprendimientoRepository.findAll();
    }

    /**
     * {@code GET  /grupo-emprendimientos/:id} : get the "id" grupoEmprendimiento.
     *
     * @param id the id of the grupoEmprendimiento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoEmprendimiento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-emprendimientos/{id}")
    public ResponseEntity<GrupoEmprendimiento> getGrupoEmprendimiento(@PathVariable Long id) {
        log.debug("REST request to get GrupoEmprendimiento : {}", id);
        Optional<GrupoEmprendimiento> grupoEmprendimiento = grupoEmprendimientoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grupoEmprendimiento);
    }

    /**
     * {@code DELETE  /grupo-emprendimientos/:id} : delete the "id" grupoEmprendimiento.
     *
     * @param id the id of the grupoEmprendimiento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-emprendimientos/{id}")
    public ResponseEntity<Void> deleteGrupoEmprendimiento(@PathVariable Long id) {
        log.debug("REST request to delete GrupoEmprendimiento : {}", id);
        grupoEmprendimientoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
