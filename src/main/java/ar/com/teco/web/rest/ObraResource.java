package ar.com.teco.web.rest;

import ar.com.teco.domain.Obra;
import ar.com.teco.repository.ObraRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.Obra}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ObraResource {

    private final Logger log = LoggerFactory.getLogger(ObraResource.class);

    private static final String ENTITY_NAME = "obra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObraRepository obraRepository;

    public ObraResource(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    /**
     * {@code POST  /obras} : Create a new obra.
     *
     * @param obra the obra to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new obra, or with status {@code 400 (Bad Request)} if the obra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/obras")
    public ResponseEntity<Obra> createObra(@Valid @RequestBody Obra obra) throws URISyntaxException {
        log.debug("REST request to save Obra : {}", obra);
        if (obra.getId() != null) {
            throw new BadRequestAlertException("A new obra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Obra result = obraRepository.save(obra);
        return ResponseEntity
            .created(new URI("/api/obras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /obras/:id} : Updates an existing obra.
     *
     * @param id the id of the obra to save.
     * @param obra the obra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obra,
     * or with status {@code 400 (Bad Request)} if the obra is not valid,
     * or with status {@code 500 (Internal Server Error)} if the obra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/obras/{id}")
    public ResponseEntity<Obra> updateObra(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Obra obra)
        throws URISyntaxException {
        log.debug("REST request to update Obra : {}, {}", id, obra);
        if (obra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, obra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!obraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Obra result = obraRepository.save(obra);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, obra.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /obras/:id} : Partial updates given fields of an existing obra, field will ignore if it is null
     *
     * @param id the id of the obra to save.
     * @param obra the obra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated obra,
     * or with status {@code 400 (Bad Request)} if the obra is not valid,
     * or with status {@code 404 (Not Found)} if the obra is not found,
     * or with status {@code 500 (Internal Server Error)} if the obra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/obras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Obra> partialUpdateObra(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Obra obra
    ) throws URISyntaxException {
        log.debug("REST request to partial update Obra partially : {}, {}", id, obra);
        if (obra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, obra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!obraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Obra> result = obraRepository
            .findById(obra.getId())
            .map(
                existingObra -> {
                    if (obra.getDescripcion() != null) {
                        existingObra.setDescripcion(obra.getDescripcion());
                    }
                    if (obra.getHabilitada() != null) {
                        existingObra.setHabilitada(obra.getHabilitada());
                    }
                    if (obra.getFechaFinObra() != null) {
                        existingObra.setFechaFinObra(obra.getFechaFinObra());
                    }

                    return existingObra;
                }
            )
            .map(obraRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, obra.getId().toString())
        );
    }

    /**
     * {@code GET  /obras} : get all the obras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of obras in body.
     */
    @GetMapping("/obras")
    public List<Obra> getAllObras() {
        log.debug("REST request to get all Obras");
        return obraRepository.findAll();
    }

    /**
     * {@code GET  /obras/:id} : get the "id" obra.
     *
     * @param id the id of the obra to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the obra, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/obras/{id}")
    public ResponseEntity<Obra> getObra(@PathVariable Long id) {
        log.debug("REST request to get Obra : {}", id);
        Optional<Obra> obra = obraRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(obra);
    }

    /**
     * {@code DELETE  /obras/:id} : delete the "id" obra.
     *
     * @param id the id of the obra to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/obras/{id}")
    public ResponseEntity<Void> deleteObra(@PathVariable Long id) {
        log.debug("REST request to delete Obra : {}", id);
        obraRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
