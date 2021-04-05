package ar.com.teco.web.rest;

import ar.com.teco.domain.Segmento;
import ar.com.teco.repository.SegmentoRepository;
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
 * REST controller for managing {@link ar.com.teco.domain.Segmento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SegmentoResource {

    private final Logger log = LoggerFactory.getLogger(SegmentoResource.class);

    private static final String ENTITY_NAME = "segmento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentoRepository segmentoRepository;

    public SegmentoResource(SegmentoRepository segmentoRepository) {
        this.segmentoRepository = segmentoRepository;
    }

    /**
     * {@code POST  /segmentos} : Create a new segmento.
     *
     * @param segmento the segmento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segmento, or with status {@code 400 (Bad Request)} if the segmento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/segmentos")
    public ResponseEntity<Segmento> createSegmento(@Valid @RequestBody Segmento segmento) throws URISyntaxException {
        log.debug("REST request to save Segmento : {}", segmento);
        if (segmento.getId() != null) {
            throw new BadRequestAlertException("A new segmento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Segmento result = segmentoRepository.save(segmento);
        return ResponseEntity
            .created(new URI("/api/segmentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /segmentos/:id} : Updates an existing segmento.
     *
     * @param id the id of the segmento to save.
     * @param segmento the segmento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmento,
     * or with status {@code 400 (Bad Request)} if the segmento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segmento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/segmentos/{id}")
    public ResponseEntity<Segmento> updateSegmento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Segmento segmento
    ) throws URISyntaxException {
        log.debug("REST request to update Segmento : {}, {}", id, segmento);
        if (segmento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Segmento result = segmentoRepository.save(segmento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, segmento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /segmentos/:id} : Partial updates given fields of an existing segmento, field will ignore if it is null
     *
     * @param id the id of the segmento to save.
     * @param segmento the segmento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segmento,
     * or with status {@code 400 (Bad Request)} if the segmento is not valid,
     * or with status {@code 404 (Not Found)} if the segmento is not found,
     * or with status {@code 500 (Internal Server Error)} if the segmento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/segmentos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Segmento> partialUpdateSegmento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Segmento segmento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Segmento partially : {}, {}", id, segmento);
        if (segmento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, segmento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!segmentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Segmento> result = segmentoRepository
            .findById(segmento.getId())
            .map(
                existingSegmento -> {
                    if (segmento.getDescripcion() != null) {
                        existingSegmento.setDescripcion(segmento.getDescripcion());
                    }

                    return existingSegmento;
                }
            )
            .map(segmentoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, segmento.getId().toString())
        );
    }

    /**
     * {@code GET  /segmentos} : get all the segmentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segmentos in body.
     */
    @GetMapping("/segmentos")
    public List<Segmento> getAllSegmentos() {
        log.debug("REST request to get all Segmentos");
        return segmentoRepository.findAll();
    }

    /**
     * {@code GET  /segmentos/:id} : get the "id" segmento.
     *
     * @param id the id of the segmento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segmento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/segmentos/{id}")
    public ResponseEntity<Segmento> getSegmento(@PathVariable Long id) {
        log.debug("REST request to get Segmento : {}", id);
        Optional<Segmento> segmento = segmentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(segmento);
    }

    /**
     * {@code DELETE  /segmentos/:id} : delete the "id" segmento.
     *
     * @param id the id of the segmento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/segmentos/{id}")
    public ResponseEntity<Void> deleteSegmento(@PathVariable Long id) {
        log.debug("REST request to delete Segmento : {}", id);
        segmentoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
