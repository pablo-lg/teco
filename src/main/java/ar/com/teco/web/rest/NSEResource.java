package ar.com.teco.web.rest;

import ar.com.teco.domain.NSE;
import ar.com.teco.repository.NSERepository;
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
 * REST controller for managing {@link ar.com.teco.domain.NSE}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NSEResource {

    private final Logger log = LoggerFactory.getLogger(NSEResource.class);

    private static final String ENTITY_NAME = "nSE";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NSERepository nSERepository;

    public NSEResource(NSERepository nSERepository) {
        this.nSERepository = nSERepository;
    }

    /**
     * {@code POST  /nses} : Create a new nSE.
     *
     * @param nSE the nSE to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nSE, or with status {@code 400 (Bad Request)} if the nSE has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nses")
    public ResponseEntity<NSE> createNSE(@Valid @RequestBody NSE nSE) throws URISyntaxException {
        log.debug("REST request to save NSE : {}", nSE);
        if (nSE.getId() != null) {
            throw new BadRequestAlertException("A new nSE cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NSE result = nSERepository.save(nSE);
        return ResponseEntity
            .created(new URI("/api/nses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nses/:id} : Updates an existing nSE.
     *
     * @param id the id of the nSE to save.
     * @param nSE the nSE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nSE,
     * or with status {@code 400 (Bad Request)} if the nSE is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nSE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nses/{id}")
    public ResponseEntity<NSE> updateNSE(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody NSE nSE)
        throws URISyntaxException {
        log.debug("REST request to update NSE : {}, {}", id, nSE);
        if (nSE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nSE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nSERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NSE result = nSERepository.save(nSE);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nSE.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nses/:id} : Partial updates given fields of an existing nSE, field will ignore if it is null
     *
     * @param id the id of the nSE to save.
     * @param nSE the nSE to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nSE,
     * or with status {@code 400 (Bad Request)} if the nSE is not valid,
     * or with status {@code 404 (Not Found)} if the nSE is not found,
     * or with status {@code 500 (Internal Server Error)} if the nSE couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NSE> partialUpdateNSE(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody NSE nSE)
        throws URISyntaxException {
        log.debug("REST request to partial update NSE partially : {}, {}", id, nSE);
        if (nSE.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nSE.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nSERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NSE> result = nSERepository
            .findById(nSE.getId())
            .map(
                existingNSE -> {
                    if (nSE.getDescripcion() != null) {
                        existingNSE.setDescripcion(nSE.getDescripcion());
                    }
                    if (nSE.getActivo() != null) {
                        existingNSE.setActivo(nSE.getActivo());
                    }

                    return existingNSE;
                }
            )
            .map(nSERepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nSE.getId().toString())
        );
    }

    /**
     * {@code GET  /nses} : get all the nSES.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nSES in body.
     */
    @GetMapping("/nses")
    public List<NSE> getAllNSES() {
        log.debug("REST request to get all NSES");
        return nSERepository.findAll();
    }

    /**
     * {@code GET  /nses/:id} : get the "id" nSE.
     *
     * @param id the id of the nSE to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nSE, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nses/{id}")
    public ResponseEntity<NSE> getNSE(@PathVariable Long id) {
        log.debug("REST request to get NSE : {}", id);
        Optional<NSE> nSE = nSERepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nSE);
    }

    /**
     * {@code DELETE  /nses/:id} : delete the "id" nSE.
     *
     * @param id the id of the nSE to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nses/{id}")
    public ResponseEntity<Void> deleteNSE(@PathVariable Long id) {
        log.debug("REST request to delete NSE : {}", id);
        nSERepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
