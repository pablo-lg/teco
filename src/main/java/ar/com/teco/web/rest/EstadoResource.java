package ar.com.teco.web.rest;

import ar.com.teco.domain.Estado;
import ar.com.teco.repository.EstadoRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.Estado}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoResource.class);

    private static final String ENTITY_NAME = "estado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoRepository estadoRepository;

    public EstadoResource(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    /**
     * {@code POST  /estados} : Create a new estado.
     *
     * @param estado the estado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estado, or with status {@code 400 (Bad Request)} if the estado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estados")
    public ResponseEntity<Estado> createEstado(@Valid @RequestBody Estado estado) throws URISyntaxException {
        log.debug("REST request to save Estado : {}", estado);
        if (estado.getId() != null) {
            throw new BadRequestAlertException("A new estado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estado result = estadoRepository.save(estado);
        return ResponseEntity
            .created(new URI("/api/estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estados/:id} : Updates an existing estado.
     *
     * @param id the id of the estado to save.
     * @param estado the estado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estado,
     * or with status {@code 400 (Bad Request)} if the estado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estados/{id}")
    public ResponseEntity<Estado> updateEstado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Estado estado
    ) throws URISyntaxException {
        log.debug("REST request to update Estado : {}, {}", id, estado);
        if (estado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Estado result = estadoRepository.save(estado);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estado.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estados/:id} : Partial updates given fields of an existing estado, field will ignore if it is null
     *
     * @param id the id of the estado to save.
     * @param estado the estado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estado,
     * or with status {@code 400 (Bad Request)} if the estado is not valid,
     * or with status {@code 404 (Not Found)} if the estado is not found,
     * or with status {@code 500 (Internal Server Error)} if the estado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estados/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Estado> partialUpdateEstado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Estado estado
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estado partially : {}, {}", id, estado);
        if (estado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Estado> result = estadoRepository
            .findById(estado.getId())
            .map(
                existingEstado -> {
                    if (estado.getDescripcion() != null) {
                        existingEstado.setDescripcion(estado.getDescripcion());
                    }

                    return existingEstado;
                }
            )
            .map(estadoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estado.getId().toString())
        );
    }

    /**
     * {@code GET  /estados} : get all the estados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estados in body.
     */
    @GetMapping("/estados")
    public List<Estado> getAllEstados() {
        log.debug("REST request to get all Estados");
        return estadoRepository.findAll();
    }

    /**
     * {@code GET  /estados/:id} : get the "id" estado.
     *
     * @param id the id of the estado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estados/{id}")
    public ResponseEntity<Estado> getEstado(@PathVariable Long id) {
        log.debug("REST request to get Estado : {}", id);
        Optional<Estado> estado = estadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estado);
    }

    /**
     * {@code DELETE  /estados/:id} : delete the "id" estado.
     *
     * @param id the id of the estado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estados/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        log.debug("REST request to delete Estado : {}", id);
        estadoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
