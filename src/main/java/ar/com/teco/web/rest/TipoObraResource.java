package ar.com.teco.web.rest;

import ar.com.teco.domain.TipoObra;
import ar.com.teco.repository.TipoObraRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.TipoObra}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoObraResource {

    private final Logger log = LoggerFactory.getLogger(TipoObraResource.class);

    private static final String ENTITY_NAME = "tipoObra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoObraRepository tipoObraRepository;

    public TipoObraResource(TipoObraRepository tipoObraRepository) {
        this.tipoObraRepository = tipoObraRepository;
    }

    /**
     * {@code POST  /tipo-obras} : Create a new tipoObra.
     *
     * @param tipoObra the tipoObra to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoObra, or with status {@code 400 (Bad Request)} if the tipoObra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-obras")
    public ResponseEntity<TipoObra> createTipoObra(@Valid @RequestBody TipoObra tipoObra) throws URISyntaxException {
        log.debug("REST request to save TipoObra : {}", tipoObra);
        if (tipoObra.getId() != null) {
            throw new BadRequestAlertException("A new tipoObra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoObra result = tipoObraRepository.save(tipoObra);
        return ResponseEntity
            .created(new URI("/api/tipo-obras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-obras/:id} : Updates an existing tipoObra.
     *
     * @param id the id of the tipoObra to save.
     * @param tipoObra the tipoObra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoObra,
     * or with status {@code 400 (Bad Request)} if the tipoObra is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoObra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-obras/{id}")
    public ResponseEntity<TipoObra> updateTipoObra(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoObra tipoObra
    ) throws URISyntaxException {
        log.debug("REST request to update TipoObra : {}, {}", id, tipoObra);
        if (tipoObra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoObra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoObraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoObra result = tipoObraRepository.save(tipoObra);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoObra.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-obras/:id} : Partial updates given fields of an existing tipoObra, field will ignore if it is null
     *
     * @param id the id of the tipoObra to save.
     * @param tipoObra the tipoObra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoObra,
     * or with status {@code 400 (Bad Request)} if the tipoObra is not valid,
     * or with status {@code 404 (Not Found)} if the tipoObra is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoObra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-obras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TipoObra> partialUpdateTipoObra(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoObra tipoObra
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoObra partially : {}, {}", id, tipoObra);
        if (tipoObra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoObra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoObraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoObra> result = tipoObraRepository
            .findById(tipoObra.getId())
            .map(
                existingTipoObra -> {
                    if (tipoObra.getDescripcion() != null) {
                        existingTipoObra.setDescripcion(tipoObra.getDescripcion());
                    }

                    return existingTipoObra;
                }
            )
            .map(tipoObraRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoObra.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-obras} : get all the tipoObras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoObras in body.
     */
    @GetMapping("/tipo-obras")
    public List<TipoObra> getAllTipoObras() {
        log.debug("REST request to get all TipoObras");
        return tipoObraRepository.findAll();
    }

    /**
     * {@code GET  /tipo-obras/:id} : get the "id" tipoObra.
     *
     * @param id the id of the tipoObra to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoObra, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-obras/{id}")
    public ResponseEntity<TipoObra> getTipoObra(@PathVariable Long id) {
        log.debug("REST request to get TipoObra : {}", id);
        Optional<TipoObra> tipoObra = tipoObraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoObra);
    }

    /**
     * {@code DELETE  /tipo-obras/:id} : delete the "id" tipoObra.
     *
     * @param id the id of the tipoObra to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-obras/{id}")
    public ResponseEntity<Void> deleteTipoObra(@PathVariable Long id) {
        log.debug("REST request to delete TipoObra : {}", id);
        tipoObraRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
