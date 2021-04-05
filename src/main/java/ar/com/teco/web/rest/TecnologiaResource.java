package ar.com.teco.web.rest;

import ar.com.teco.domain.Tecnologia;
import ar.com.teco.repository.TecnologiaRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.Tecnologia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TecnologiaResource {

    private final Logger log = LoggerFactory.getLogger(TecnologiaResource.class);

    private static final String ENTITY_NAME = "tecnologia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TecnologiaRepository tecnologiaRepository;

    public TecnologiaResource(TecnologiaRepository tecnologiaRepository) {
        this.tecnologiaRepository = tecnologiaRepository;
    }

    /**
     * {@code POST  /tecnologias} : Create a new tecnologia.
     *
     * @param tecnologia the tecnologia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tecnologia, or with status {@code 400 (Bad Request)} if the tecnologia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tecnologias")
    public ResponseEntity<Tecnologia> createTecnologia(@Valid @RequestBody Tecnologia tecnologia) throws URISyntaxException {
        log.debug("REST request to save Tecnologia : {}", tecnologia);
        if (tecnologia.getId() != null) {
            throw new BadRequestAlertException("A new tecnologia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tecnologia result = tecnologiaRepository.save(tecnologia);
        return ResponseEntity
            .created(new URI("/api/tecnologias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tecnologias/:id} : Updates an existing tecnologia.
     *
     * @param id the id of the tecnologia to save.
     * @param tecnologia the tecnologia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tecnologia,
     * or with status {@code 400 (Bad Request)} if the tecnologia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tecnologia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tecnologias/{id}")
    public ResponseEntity<Tecnologia> updateTecnologia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Tecnologia tecnologia
    ) throws URISyntaxException {
        log.debug("REST request to update Tecnologia : {}, {}", id, tecnologia);
        if (tecnologia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tecnologia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tecnologiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tecnologia result = tecnologiaRepository.save(tecnologia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tecnologia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tecnologias/:id} : Partial updates given fields of an existing tecnologia, field will ignore if it is null
     *
     * @param id the id of the tecnologia to save.
     * @param tecnologia the tecnologia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tecnologia,
     * or with status {@code 400 (Bad Request)} if the tecnologia is not valid,
     * or with status {@code 404 (Not Found)} if the tecnologia is not found,
     * or with status {@code 500 (Internal Server Error)} if the tecnologia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tecnologias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Tecnologia> partialUpdateTecnologia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Tecnologia tecnologia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tecnologia partially : {}, {}", id, tecnologia);
        if (tecnologia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tecnologia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tecnologiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tecnologia> result = tecnologiaRepository
            .findById(tecnologia.getId())
            .map(
                existingTecnologia -> {
                    if (tecnologia.getDescripcion() != null) {
                        existingTecnologia.setDescripcion(tecnologia.getDescripcion());
                    }

                    return existingTecnologia;
                }
            )
            .map(tecnologiaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tecnologia.getId().toString())
        );
    }

    /**
     * {@code GET  /tecnologias} : get all the tecnologias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tecnologias in body.
     */
    @GetMapping("/tecnologias")
    public List<Tecnologia> getAllTecnologias() {
        log.debug("REST request to get all Tecnologias");
        return tecnologiaRepository.findAll();
    }

    /**
     * {@code GET  /tecnologias/:id} : get the "id" tecnologia.
     *
     * @param id the id of the tecnologia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tecnologia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tecnologias/{id}")
    public ResponseEntity<Tecnologia> getTecnologia(@PathVariable Long id) {
        log.debug("REST request to get Tecnologia : {}", id);
        Optional<Tecnologia> tecnologia = tecnologiaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tecnologia);
    }

    /**
     * {@code DELETE  /tecnologias/:id} : delete the "id" tecnologia.
     *
     * @param id the id of the tecnologia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tecnologias/{id}")
    public ResponseEntity<Void> deleteTecnologia(@PathVariable Long id) {
        log.debug("REST request to delete Tecnologia : {}", id);
        tecnologiaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
