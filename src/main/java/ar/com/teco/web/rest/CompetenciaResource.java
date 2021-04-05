package ar.com.teco.web.rest;

import ar.com.teco.domain.Competencia;
import ar.com.teco.repository.CompetenciaRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.Competencia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompetenciaResource {

    private final Logger log = LoggerFactory.getLogger(CompetenciaResource.class);

    private static final String ENTITY_NAME = "competencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetenciaRepository competenciaRepository;

    public CompetenciaResource(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository;
    }

    /**
     * {@code POST  /competencias} : Create a new competencia.
     *
     * @param competencia the competencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competencia, or with status {@code 400 (Bad Request)} if the competencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competencias")
    public ResponseEntity<Competencia> createCompetencia(@Valid @RequestBody Competencia competencia) throws URISyntaxException {
        log.debug("REST request to save Competencia : {}", competencia);
        if (competencia.getId() != null) {
            throw new BadRequestAlertException("A new competencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competencia result = competenciaRepository.save(competencia);
        return ResponseEntity
            .created(new URI("/api/competencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competencias/:id} : Updates an existing competencia.
     *
     * @param id the id of the competencia to save.
     * @param competencia the competencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencia,
     * or with status {@code 400 (Bad Request)} if the competencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competencias/{id}")
    public ResponseEntity<Competencia> updateCompetencia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Competencia competencia
    ) throws URISyntaxException {
        log.debug("REST request to update Competencia : {}, {}", id, competencia);
        if (competencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Competencia result = competenciaRepository.save(competencia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competencia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competencias/:id} : Partial updates given fields of an existing competencia, field will ignore if it is null
     *
     * @param id the id of the competencia to save.
     * @param competencia the competencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competencia,
     * or with status {@code 400 (Bad Request)} if the competencia is not valid,
     * or with status {@code 404 (Not Found)} if the competencia is not found,
     * or with status {@code 500 (Internal Server Error)} if the competencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competencias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Competencia> partialUpdateCompetencia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Competencia competencia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Competencia partially : {}, {}", id, competencia);
        if (competencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Competencia> result = competenciaRepository
            .findById(competencia.getId())
            .map(
                existingCompetencia -> {
                    if (competencia.getDescripcion() != null) {
                        existingCompetencia.setDescripcion(competencia.getDescripcion());
                    }

                    return existingCompetencia;
                }
            )
            .map(competenciaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competencia.getId().toString())
        );
    }

    /**
     * {@code GET  /competencias} : get all the competencias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competencias in body.
     */
    @GetMapping("/competencias")
    public List<Competencia> getAllCompetencias() {
        log.debug("REST request to get all Competencias");
        return competenciaRepository.findAll();
    }

    /**
     * {@code GET  /competencias/:id} : get the "id" competencia.
     *
     * @param id the id of the competencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competencias/{id}")
    public ResponseEntity<Competencia> getCompetencia(@PathVariable Long id) {
        log.debug("REST request to get Competencia : {}", id);
        Optional<Competencia> competencia = competenciaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competencia);
    }

    /**
     * {@code DELETE  /competencias/:id} : delete the "id" competencia.
     *
     * @param id the id of the competencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competencias/{id}")
    public ResponseEntity<Void> deleteCompetencia(@PathVariable Long id) {
        log.debug("REST request to delete Competencia : {}", id);
        competenciaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
