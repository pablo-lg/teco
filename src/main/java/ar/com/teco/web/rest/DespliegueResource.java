package ar.com.teco.web.rest;

import ar.com.teco.domain.Despliegue;
import ar.com.teco.repository.DespliegueRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.Despliegue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DespliegueResource {

    private final Logger log = LoggerFactory.getLogger(DespliegueResource.class);

    private static final String ENTITY_NAME = "despliegue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DespliegueRepository despliegueRepository;

    public DespliegueResource(DespliegueRepository despliegueRepository) {
        this.despliegueRepository = despliegueRepository;
    }

    /**
     * {@code POST  /despliegues} : Create a new despliegue.
     *
     * @param despliegue the despliegue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new despliegue, or with status {@code 400 (Bad Request)} if the despliegue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/despliegues")
    public ResponseEntity<Despliegue> createDespliegue(@Valid @RequestBody Despliegue despliegue) throws URISyntaxException {
        log.debug("REST request to save Despliegue : {}", despliegue);
        if (despliegue.getId() != null) {
            throw new BadRequestAlertException("A new despliegue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Despliegue result = despliegueRepository.save(despliegue);
        return ResponseEntity
            .created(new URI("/api/despliegues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /despliegues/:id} : Updates an existing despliegue.
     *
     * @param id the id of the despliegue to save.
     * @param despliegue the despliegue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated despliegue,
     * or with status {@code 400 (Bad Request)} if the despliegue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the despliegue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/despliegues/{id}")
    public ResponseEntity<Despliegue> updateDespliegue(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Despliegue despliegue
    ) throws URISyntaxException {
        log.debug("REST request to update Despliegue : {}, {}", id, despliegue);
        if (despliegue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, despliegue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!despliegueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Despliegue result = despliegueRepository.save(despliegue);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, despliegue.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /despliegues/:id} : Partial updates given fields of an existing despliegue, field will ignore if it is null
     *
     * @param id the id of the despliegue to save.
     * @param despliegue the despliegue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated despliegue,
     * or with status {@code 400 (Bad Request)} if the despliegue is not valid,
     * or with status {@code 404 (Not Found)} if the despliegue is not found,
     * or with status {@code 500 (Internal Server Error)} if the despliegue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/despliegues/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Despliegue> partialUpdateDespliegue(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Despliegue despliegue
    ) throws URISyntaxException {
        log.debug("REST request to partial update Despliegue partially : {}, {}", id, despliegue);
        if (despliegue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, despliegue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!despliegueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Despliegue> result = despliegueRepository
            .findById(despliegue.getId())
            .map(
                existingDespliegue -> {
                    if (despliegue.getDescripcion() != null) {
                        existingDespliegue.setDescripcion(despliegue.getDescripcion());
                    }
                    if (despliegue.getValor() != null) {
                        existingDespliegue.setValor(despliegue.getValor());
                    }

                    return existingDespliegue;
                }
            )
            .map(despliegueRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, despliegue.getId().toString())
        );
    }

    /**
     * {@code GET  /despliegues} : get all the despliegues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of despliegues in body.
     */
    @GetMapping("/despliegues")
    public List<Despliegue> getAllDespliegues() {
        log.debug("REST request to get all Despliegues");
        return despliegueRepository.findAll();
    }

    /**
     * {@code GET  /despliegues/:id} : get the "id" despliegue.
     *
     * @param id the id of the despliegue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the despliegue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/despliegues/{id}")
    public ResponseEntity<Despliegue> getDespliegue(@PathVariable Long id) {
        log.debug("REST request to get Despliegue : {}", id);
        Optional<Despliegue> despliegue = despliegueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(despliegue);
    }

    /**
     * {@code DELETE  /despliegues/:id} : delete the "id" despliegue.
     *
     * @param id the id of the despliegue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/despliegues/{id}")
    public ResponseEntity<Void> deleteDespliegue(@PathVariable Long id) {
        log.debug("REST request to delete Despliegue : {}", id);
        despliegueRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
